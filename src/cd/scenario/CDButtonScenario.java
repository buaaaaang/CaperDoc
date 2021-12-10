package cd.scenario;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDButton.Button;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import cd.button.CDNeedButton;
import cd.cmd.CDCmdToScroll;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
        this.addScene(CDButtonScenario.HierarchyPressedScene.
            createSingleton(this));
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
                    ((CDButtonScenario) this.mScenario).getCurHandlingButton().
                        setHighlight(false);
                    button.setHighlight(true);
                    ((CDButtonScenario) this.mScenario).setCurHandlingButton(
                        button);
                    cd.getSideViewer().setImplyMode(
                        mgr.getCurWorkingContentButton());
                    break;
                case IMPLY:
                    cd.getXform().goToYPos((int) 
                        mgr.getCurWorkingImplyButton().getContentPosition());
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                case SIDE:
                case NONE:
                    ((CDButtonScenario) this.mScenario).getCurHandlingButton().
                        setHighlight(false);
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, this.mReturnScene, null);
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
    
    public static class HierarchyPressedScene extends CDScene {
        private static HierarchyPressedScene mSingleton = null;
        public static HierarchyPressedScene createSingleton(XScenario scenario) {
            assert (HierarchyPressedScene.mSingleton == null);
            HierarchyPressedScene.mSingleton = new HierarchyPressedScene(scenario);
            return HierarchyPressedScene.mSingleton;
        }
        public static HierarchyPressedScene getSingleton() {
            assert (HierarchyPressedScene.mSingleton != null);
            return HierarchyPressedScene.mSingleton;
        }
        
        public HierarchyPressedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButtonMgr mgr = cd.getButtonMgr();
            CDButton button = mgr.checkButton(e.getPoint());
            Button kind = button.getKind();
            switch (kind) {
                case NONE:
                case COLOR:
                case CONTENT:
                case NEED:
                    cd.getButtonViewer().setDummyButton(
                        ((CDButtonScenario) this.mScenario).
                        getCurHandlingButton(), e.getPoint());
                    break;
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButtonMgr mgr = cd.getButtonMgr();
            CDButton button = mgr.checkButton(e.getPoint());
            Button kind = button.getKind();
            switch (kind) {
                case HIERARCHY:
                    if (cd.getButtonMgr().getCurWorkingHierarchyButton() ==
                        ((CDButtonScenario) this.mScenario).
                        getCurHandlingButton()) {
                        cd.getXform().goToYPos((int) cd.getButtonMgr().
                            getCurWorkingHierarchyButton().
                            getContentPosition());
                    }
                    break;
                case CONTENT:
                    CDContentButton cont_use = cd.getButtonMgr().
                        getCurWorkingContentButton();
                    Rectangle box = cont_use.getBox().getBounds();
                    // we may change site of need button
                    CDNeedButton need = new CDNeedButton(button.getName(),
                        new Point(box.x - 200, box.y), cd, cont_use);
                    cd.getButtonMgr().addNeedButton(need);
                    cont_use.addNeedButton(need);
                    CDContentButton cont_used = 
                        ((CDButtonScenario) this.mScenario).
                        getCurHandlingHierarchyButton().getContentButton();
                    CDImplyButton imply = new CDImplyButton(button.getName(),
                        cd, cont_used);
                    ((CDButtonScenario) this.mScenario).
                        getCurHandlingHierarchyButton().getContentButton().
                        addImplyButton(imply);
                    
                    break;






            }
            ((CDButtonScenario) this.mScenario).getCurHandlingButton().
                setHighlight(false);
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
        
    private CDButton mCurHandlingButton = null;
    public void setCurHandlingButton(CDButton button) {
        this.mCurHandlingButton = button;
    }
    public CDButton getCurHandlingButton() {
        return this.mCurHandlingButton;
    }
    private CDHierarchyButton mCurHandlingHierarchyButton = null;
    public void setCurHandlingHierarchyButton(CDHierarchyButton button) {
        this.mCurHandlingHierarchyButton = button;
    }
    public CDHierarchyButton getCurHandlingHierarchyButton() {
        return this.mCurHandlingHierarchyButton;
    }
    
}
