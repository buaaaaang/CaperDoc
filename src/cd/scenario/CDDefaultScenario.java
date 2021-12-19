package cd.scenario;

import cd.CD;
import cd.CDCanvas2D;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.cmd.CDCmdToChooseAllContentBox;
import cd.cmd.CDCmdToCreateCurPtCurve;
import cd.cmd.CDCmdToIncreaseStrokeWidthForCurPtCurve;
import cd.cmd.CDCmdToSaveFile;
import cd.cmd.CDCmdToScrollWorld;
import cd.cmd.CDCmdToScrollSide;
import cd.cmd.CDCmdToSetLinkPressed;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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
                        cd.getButtonMgr().getCurMouseContentButton());
                    CDWorldButtonScenario.getSingleton().
                        setCurHandlingContentButton(
                        cd.getButtonMgr().getCurMouseContentButton());
                    button.setHighlight(true);
                    CDWorldButtonScenario.getSingleton().setStartPoint(e.getPoint());
                    XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                        ContentPressedScene.getSingleton(), this);
                    break;
                case HIERARCHY:
                    CDSideButtonScenario.getSingleton().setCurHandlingSideButton(
                        cd.getButtonMgr().getCurMouseHierarchyButton());
                    button.setHighlight(true);
                    XCmdToChangeScene.execute(cd, 
                        CDSideButtonScenario.SideButtonPressedScene.
                        getSingleton(), this);
                    break;
                case COLOR:
                    CDColorButton cb = (CDColorButton)button;
                    CDColorScenario.getSingleton().setCurHandlingButton(cb);
                    XCmdToChangeScene.execute(cd, 
                        CDColorScenario.ColorScene.getSingleton(), 
                        this);
                    break;
                case LINK:
                    CDCmdToSetLinkPressed.execute(cd, e.getPoint());
                    XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                        LinkPressedScene.getSingleton(), this);
                    break;
                case NONE:
                    if (cd.getPDFViewer().onWhatBranch(e.getPoint()) != -1) {
                        CDCmdToCreateCurPtCurve.execute(cd, e.getPoint());
                        XCmdToChangeScene.execute(cd, 
                            CDDrawScenario.DrawScene.getSingleton(), this);
                        break;
                    }
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
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case NONE:
                case COLOR:
                case CONTENT:
                case LINK:
                    CDCmdToScrollWorld.execute(cd, e);
                    break;
                case SIDE:
                case IMPLY:
                case HIERARCHY:
                    CDCmdToScrollSide.execute(cd, e);
                    break;
            }
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
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
                    cd.getXform().goToNextBranch(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    cd.getXform().goToNextBranch(1);
                    break;
                case KeyEvent.VK_CLOSE_BRACKET:
                    CDDefaultScenario.getSingleton().setDrawPenTip(false);
                    break;
                case KeyEvent.VK_OPEN_BRACKET:
                    CDDefaultScenario.getSingleton().setDrawPenTip(false);
                    break;
                case KeyEvent.VK_A:
                    CDCmdToChooseAllContentBox.execute(cd);
                    XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                        ContentChoosedScene.getSingleton(), null);
            }
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            CD cd = (CD) this.mScenario.getApp();
            if (cd.getEventListener().getCurPoint() == null ||
                !CDDefaultScenario.getSingleton().getDrawPenTip()) {
                return;
            }
            BasicStroke bs = (BasicStroke) cd.getCanvas().
                getCurStrokeForPtCurve();
            Point2D.Double worldPt0 = new Point2D.Double(0.0, 0.0);
            Point2D.Double worldPt1 = new Point2D.Double(bs.getLineWidth(), 0.0);
            Point screenPt0 = cd.getXform().calcPtFromWorldToScreen(
                worldPt0);
            Point screenPt1 = cd.getXform().calcPtFromWorldToScreen(
                worldPt1);
            double d = screenPt0.distance(screenPt1);
            double r = d / 2.0;
            Point ctr = cd.getEventListener().getCurPoint();
            Ellipse2D.Double ellipse = 
                new Ellipse2D.Double(ctr.x - r, ctr.y - r, d, d);
            g2.setColor(cd.getCanvas().getCurColorForPtCurve());
            g2.fill(ellipse);
        }
    }
    
    private boolean mDrawPenTip = false;
    private boolean getDrawPenTip() {
        return this.mDrawPenTip;
    }
    public void setDrawPenTip(boolean b) {
        this.mDrawPenTip = b;
    }
}
