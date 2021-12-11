package cd.scenario;

import cd.CD;
import cd.CDCanvas2D;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.button.CDNeedButton;
import cd.cmd.CDCmdToCreateCurPtCurve;
import cd.cmd.CDCmdToIncreaseStrokeWidthForCurPtCurve;
import cd.cmd.CDCmdToSaveFile;
import cd.cmd.CDCmdToScroll;
import cd.cmd.CDCmdToMoveBackToRecentXform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDDefaultScenario extends XScenario {
    // singleton pattern
    private static CDDefaultScenario mSingleton = null;
    public static CDDefaultScenario createSingleton(XApp app) {
        assert (CDDefaultScenario.mSingleton == null);
        CDDefaultScenario.mSingleton = new CDDefaultScenario(app);
        return CDDefaultScenario.mSingleton;
    }
    public static CDDefaultScenario getSingleton() {
        assert (CDDefaultScenario.mSingleton != null);
        return CDDefaultScenario.mSingleton;
    }
    
    // private constructor 
    private CDDefaultScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDDefaultScenario.ReadyScene.createSingleton(this));
    }
    
    public static class ReadyScene extends CDScene {
        private static ReadyScene mSingleton = null;
        public static ReadyScene createSingleton(XScenario scenario) {
            assert (ReadyScene.mSingleton == null);
            ReadyScene.mSingleton = new ReadyScene(scenario);
            return ReadyScene.mSingleton;
        }
        public static ReadyScene getSingleton() {
            assert (ReadyScene.mSingleton != null);
            return ReadyScene.mSingleton;
        }
        
        public ReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case CONTENT:
                    cd.getSideViewer().setImplyMode(
                        cd.getButtonMgr().getCurWorkingContentButton());
                    CDButtonScenario.getSingleton().
                        setCurHandlingButton(button);
                    button.setHighlight(true);
                    XCmdToChangeScene.execute(cd, 
                        CDButtonScenario.ContentChoosedScene.getSingleton(), 
                        null);
                    break;
                case HIERARCHY:
                    CDButtonScenario.getSingleton().
                        setCurHandlingButton(button);
                    CDButtonScenario.getSingleton().
                        setCurHandlingHierarchyButton(cd.getButtonMgr().
                        getCurWorkingHierarchyButton());
                    button.setHighlight(true);
                    XCmdToChangeScene.execute(cd, 
                        CDButtonScenario.HierarchyPressedScene.getSingleton(), 
                        this);
                    break;
                case COLOR:
                    CDColorButton cb = (CDColorButton)button;
                    CDColorScenario.getSingleton().setCurHandlingButton(cb);
                    XCmdToChangeScene.execute(cd, 
                        CDColorScenario.ColorScene.getSingleton(), 
                        this);
                    break;
                case NEED:
                    CDNeedButton needButton = 
                        cd.getButtonMgr().getCurWorkingNeedButton();
                    needButton.setInitialPressedPoint(e.getPoint());
                    needButton.setMoved(false);
                    needButton.setInitialPosition();
                    needButton.setHighlight(true);
                    CDButtonScenario.getSingleton().
                        setCurHandlingNeedButton(needButton);
                    XCmdToChangeScene.execute(cd, 
                        CDButtonScenario.NeedPressedScene.getSingleton(), 
                        this);
                    break;
                case NONE:
                    CDCmdToCreateCurPtCurve.execute(cd, e.getPoint());
                    XCmdToChangeScene.execute(cd, 
                        CDDrawScenario.DrawScene.getSingleton(), 
                        CDDefaultScenario.ReadyScene.getSingleton());
                    break;
            }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
//            CD cd = (CD)this.mScenario.getApp();
//            Graphics g = cd.getCanvas().getGraphics();
//            Graphics2D g2 = (Graphics2D) g;
//            cd.getCanvas().drawPenTip(g2, e);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case NONE:
                case COLOR:
                case CONTENT:
                case NEED:
                    CDCmdToScroll.execute(cd, 
                        (e.getWheelRotation() > 0) ? -1 : 1);
                    break;
                case SIDE:
                case HIERARCHY:
                    int amount = 10;
                    cd.getSideViewer().shift(e.getWheelRotation() * amount);
            }
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_UP:
                    CDCmdToScroll.execute(cd, 1);
                    break;
                case KeyEvent.VK_DOWN:
                    CDCmdToScroll.execute(cd, -1);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(cd, CDNavigateScenario.
                        ZoomPanReadyScene.getSingleton(), this);
                    break;
                case KeyEvent.VK_CLOSE_BRACKET:
                    CDCmdToIncreaseStrokeWidthForCurPtCurve.execute(cd, 
                            CDCanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
                case KeyEvent.VK_OPEN_BRACKET:
                    CDCmdToIncreaseStrokeWidthForCurPtCurve.execute(cd, 
                            -CDCanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(cd, 
                        CDSelectScenario.SelectReadyScene.getSingleton(), 
                        this);
                    break;
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(cd, 
                        CDCropScenario.CropReadyScene.getSingleton(), 
                        this);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_S:
                    CDCmdToSaveFile.execute(cd);
                    break;
                case KeyEvent.VK_LEFT:
                    CDCmdToMoveBackToRecentXform.execute(cd, -1);
                    System.out.println("back");
                    int len = cd.getXform().getXformHistory().size();
                    System.out.println("length"+len);
                    System.out.println("position"+cd.getXform().getCurPosOnHistory());
                    break;
                case KeyEvent.VK_RIGHT:
                    CDCmdToMoveBackToRecentXform.execute(cd, 1);
                    System.out.println("front");
                    int len2 = cd.getXform().getXformHistory().size();
                    System.out.println("length"+len2);
                    System.out.println("position"+cd.getXform().getCurPosOnHistory());
                    break;
            }
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }
    }
}
