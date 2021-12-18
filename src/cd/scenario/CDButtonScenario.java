package cd.scenario;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDButton.Button;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import cd.button.CDLinkButton;
import cd.cmd.CDCmdToScroll;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDButtonScenario extends XScenario {
    // singleton pattern
    private static CDButtonScenario mSingleton = null;
    public static CDButtonScenario createSingleton(XApp app) {
        assert (CDButtonScenario.mSingleton == null);
        CDButtonScenario.mSingleton = new CDButtonScenario(app);
        return CDButtonScenario.mSingleton;
    }
    public static CDButtonScenario getSingleton() {
        assert (CDButtonScenario.mSingleton != null);
        return CDButtonScenario.mSingleton;
    }
    
    // private constructor 
    private CDButtonScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDButtonScenario.ContentChoosedScene.
            createSingleton(this));
        this.addScene(CDButtonScenario.LinkPressedScene.createSingleton(this));
    }
    
    public static class ContentChoosedScene extends CDScene {
        private static ContentChoosedScene mSingleton = null;
        public static ContentChoosedScene createSingleton(XScenario scenario) {
            assert (ContentChoosedScene.mSingleton == null);
            ContentChoosedScene.mSingleton = new ContentChoosedScene(scenario);
            return ContentChoosedScene.mSingleton;
        }
        public static ContentChoosedScene getSingleton() {
            assert (ContentChoosedScene.mSingleton != null);
            return ContentChoosedScene.mSingleton;
        }
        
        public ContentChoosedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButtonMgr mgr = cd.getButtonMgr();
            CDButton button = mgr.checkButton(e.getPoint());
            Button kind = button.getKind();
            switch (kind) {
                case CONTENT:
                    CDButtonScenario.getSingleton().getCurHandlingButton().
                        setHighlight(false);
                    button.setHighlight(true);
                    CDButtonScenario.getSingleton().setCurHandlingButton(
                        button);
                    cd.getSideViewer().setImplyMode(
                        mgr.getCurWorkingContentButton());
                    break;
                case IMPLY:
                    cd.getXform().goToYPos((int) 
                        mgr.getCurWorkingImplyButton().getContentPosition());
                    CDButtonScenario.getSingleton().getCurHandlingButton().
                        setHighlight(false);
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case COLOR:
                case SIDE:
                case NONE:
                    CDButtonScenario.getSingleton().getCurHandlingButton().
                        setHighlight(false);
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
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
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(cd, CDNavigateScenario.
                        ZoomPanReadyScene.getSingleton(), this);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

    }
    
    public static class LinkPressedScene extends CDScene {
        private static LinkPressedScene mSingleton = null;
        public static LinkPressedScene createSingleton(XScenario scenario) {
            assert (LinkPressedScene.mSingleton == null);
            LinkPressedScene.mSingleton = new LinkPressedScene(scenario);
            return LinkPressedScene.mSingleton;
        }
        public static LinkPressedScene getSingleton() {
            assert (LinkPressedScene.mSingleton != null);
            return LinkPressedScene.mSingleton;
        }
        
        public LinkPressedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDLinkButton button = CDButtonScenario.getSingleton().
                getCurHandlingNeedButton();
            Point initialPt = button.getInitialPressedPoint();
            if (Math.pow(initialPt.x - e.getPoint().x, 2) + 
                Math.pow(initialPt.y - e.getPoint().y, 2) >
                Math.pow(CDButtonScenario.MAX_DRAG_DISTANCE_TO_CLICK, 2)) {
                Point2D.Double wp1 = cd.getXform().calcPtFromScreenToWorld(
                    e.getPoint());
                Point2D.Double wp2 = cd.getXform().calcPtFromScreenToWorld(
                    initialPt);
                Rectangle box = button.getInitialBox();
                button.setBox(new Rectangle(box.x + (int) (wp1.x - wp2.x), 
                    box.y + (int) (wp1.y - wp2.y), box.width, box.height));
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDLinkButton button = CDButtonScenario.getSingleton().
                getCurHandlingNeedButton();
            button.setHighlight(false);
            if (button.getInitialBox() == button.getBox()) {
                int curBranch = cd.getPDFViewer().onWhatBranch(e.getPoint());
                cd.getPDFViewer().addPage(curBranch, 
                    (int) button.getContentPosition());
            }
            XCmdToChangeScene.execute(cd, this.mReturnScene, null);
        }
        
        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

    }
    
    public static final double MAX_DRAG_DISTANCE_TO_CLICK = 20;
        
    private CDButton mCurHandlingButton = null;
    public void setCurHandlingButton(CDButton button) {
        this.mCurHandlingButton = button;
    }
    public CDButton getCurHandlingButton() {
        return this.mCurHandlingButton;
    }
    private CDLinkButton mCurHandlingNeedButton = null;
    public void setCurHandlingNeedButton(CDLinkButton button) {
        this.mCurHandlingNeedButton = button;
    }
    public CDLinkButton getCurHandlingNeedButton() {
        return this.mCurHandlingNeedButton;
    }
    
}
