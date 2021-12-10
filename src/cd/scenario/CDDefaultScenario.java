package cd.scenario;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDCanvas2D;
import cd.CDScene;
import cd.button.CDButton;
import cd.cmd.CDCmdToCreateCurPtCurve;
import cd.cmd.CDCmdToIncreaseStrokeWidthForCurPtCurve;
import cd.cmd.CDCmdToSaveFile;
import cd.cmd.CDCmdToScroll;
import java.awt.Graphics2D;
import java.awt.Point;
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
                    XCmdToChangeScene.execute(cd, 
                        CDButtonScenario.HierarchyPressedScene.getSingleton(), 
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
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            if (e.getPoint().x > CD.HIERARCHY_WIDTH) {
                CDCmdToScroll.execute(cd, (e.getWheelRotation() > 0) ? -1 : 1);
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
