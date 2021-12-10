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
        this.addScene(CDButtonScenario.HierarchyPressedScene.
            createSingleton(this));
        this.addScene(CDButtonScenario.NeedPressedScene.createSingleton(this));
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
                        CDButtonScenario.getSingleton().getCurHandlingButton(), 
                        e.getPoint());
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
                        CDButtonScenario.getSingleton().
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
                    CDContentButton cont_used = CDButtonScenario.getSingleton().
                        getCurHandlingHierarchyButton().getContentButton();
                    // we may change site of need button
                    int i = -1;
                    boolean p = true;
                    while (p) {
                        i++;
                        p = false;       
                        for (CDContentButton b1: 
                            cd.getButtonMgr().getContentButtons()) {
                            if (b1.getBox().contains(new Point2D.Double(
                                cd.getPDFViewer().getWorldXPos() + 10,
                                box.y + i * CDNeedButton.HEIGHT * 1.2 + 10))) {
                                p = true;
                            }     
                        }
                        for (CDNeedButton b2: 
                            cd.getButtonMgr().getNeedButtons()) {
                            if (b2.getBox().contains(new Point2D.Double(
                                cd.getPDFViewer().getWorldXPos() + 15,
                                box.y + i * CDNeedButton.HEIGHT * 1.2 + 10))) {
                                p = true;
                            }
                        }
                    }
                    CDNeedButton need = new CDNeedButton(cont_used.getName(),
                        cont_used.getPosition().y, new Point2D.Double(
                        cd.getPDFViewer().getWorldXPos(), 
                        box.y + i * CDNeedButton.HEIGHT * 1.2), cd, cont_use);
                    CDImplyButton imply = new CDImplyButton(cont_use.getName(),
                        cont_use.getPosition().y, cd, cont_used);
                    if (cont_use == cont_used) {
                        break;
                    }
                    cd.getButtonMgr().addNeedButton(need);
                    cd.getButtonMgr().addImplyButton(imply);
                    cont_use.addNeedButton(need);
                    cont_used.addImplyButton(imply);
                    break;
            }
            CDButtonScenario.getSingleton().getCurHandlingButton().
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
    
    public static class NeedPressedScene extends CDScene {
        private static NeedPressedScene mSingleton = null;
        public static NeedPressedScene createSingleton(XScenario scenario) {
            assert (NeedPressedScene.mSingleton == null);
            NeedPressedScene.mSingleton = new NeedPressedScene(scenario);
            return NeedPressedScene.mSingleton;
        }
        public static NeedPressedScene getSingleton() {
            assert (NeedPressedScene.mSingleton != null);
            return NeedPressedScene.mSingleton;
        }
        
        public NeedPressedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CDNeedButton button = CDButtonScenario.getSingleton().
                getCurHandlingNeedButton();
            Point initialPt = button.getInitialPressedPoint();
            if (Math.pow(initialPt.x - e.getPoint().x, 2) + 
                Math.pow(initialPt.y - e.getPoint().y, 2) >
                Math.pow(CDButtonScenario.MAX_DRAG_DISTANCE_TO_CLICK, 2)) {
                button.moveScreenPosition(e.getPoint().x - initialPt.x,
                    e.getPoint().y - initialPt.y);
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDNeedButton button = CDButtonScenario.getSingleton().
                getCurHandlingNeedButton();
            button.setHighlight(false);
            if (!button.hasMoved()) {
                
                cd.getXform().goToYPos((int) button.getContentPosition());
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
    
    public static final double MAX_DRAG_DISTANCE_TO_CLICK = 10;
        
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
    private CDNeedButton mCurHandlingNeedButton = null;
    public void setCurHandlingNeedButton(CDNeedButton button) {
        this.mCurHandlingNeedButton = button;
    }
    public CDNeedButton getCurHandlingNeedButton() {
        return this.mCurHandlingNeedButton;
    }
    
}
