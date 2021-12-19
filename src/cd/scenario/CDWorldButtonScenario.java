package cd.scenario;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDButtonViewer;
import cd.CDPDFViewer;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDButton.Button;
import cd.button.CDContentButton;
import cd.button.CDImplyButton;
import cd.button.CDLinkButton;
import cd.cmd.CDCmdToChooseAllContentBox;
import cd.cmd.CDCmdToDeleteChoosedContentBox;
import cd.cmd.CDCmdToDeleteLinkButton;
import cd.cmd.CDCmdToMakeImplyButton;
import cd.cmd.CDCmdToMakeNewBranch;
import cd.cmd.CDCmdToMoveLink;
import cd.cmd.CDCmdToScrollSide;
import cd.cmd.CDCmdToScrollWorld;
import cd.cmd.CDCmdToSetLinkPressed;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDWorldButtonScenario extends XScenario {
    // singleton pattern
    private static CDWorldButtonScenario mSingleton = null;
    public static CDWorldButtonScenario createSingleton(XApp app) {
        assert (CDWorldButtonScenario.mSingleton == null);
        CDWorldButtonScenario.mSingleton = new CDWorldButtonScenario(app);
        return CDWorldButtonScenario.mSingleton;
    }
    public static CDWorldButtonScenario getSingleton() {
        assert (CDWorldButtonScenario.mSingleton != null);
        return CDWorldButtonScenario.mSingleton;
    }
    
    // private constructor 
    private CDWorldButtonScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDWorldButtonScenario.ContentPressedScene.
            createSingleton(this));
        this.addScene(CDWorldButtonScenario.ContentChoosedScene.
            createSingleton(this));
        this.addScene(CDWorldButtonScenario.LinkPressedScene.createSingleton(this));
    }
    
    
    public static class ContentPressedScene extends CDScene {
        private static ContentPressedScene mSingleton = null;
        public static ContentPressedScene createSingleton(XScenario scenario) {
            assert (ContentPressedScene.mSingleton == null);
            ContentPressedScene.mSingleton = new ContentPressedScene(scenario);
            return ContentPressedScene.mSingleton;
        }
        public static ContentPressedScene getSingleton() {
            assert (ContentPressedScene.mSingleton != null);
            return ContentPressedScene.mSingleton;
        }
        
        public ContentPressedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDWorldButtonScenario.getSingleton().setCurPoint(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            CDWorldButtonScenario s = CDWorldButtonScenario.getSingleton();
            switch (kind) {
                case CONTENT:
                    CDContentButton b = 
                        cd.getButtonMgr().getCurMouseContentButton();
                    if (b == s.getCurHandlingContentButton()) {
                        s.addChoosedButton(b);
                        if (s.choosedButtonExist()) {
                            cd.getSideViewer().setImplyMode(b);
                            XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                                ContentChoosedScene.getSingleton(), null);
                        } else {
                            b.setHighlight(false);
                            XCmdToChangeScene.execute(cd, CDDefaultScenario.
                                ReadyScene.getSingleton(), null);
                        }
                    } else {
                        CDCmdToMakeImplyButton.execute(cd);
                        XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                            ContentChoosedScene.getSingleton(), null);
                    }
                    break;
                case COLOR:
                case NONE:
                case LINK:
                case HIERARCHY:
                case SIDE:
                case IMPLY:
                    s.getCurHandlingContentButton().setHighlight(
                        s.isChoosed(s.getCurHandlingContentButton()));
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, this.mReturnScene, null);
            }
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
            CD cd = (CD) this.mScenario.getApp();
            CDContentButton b = CDWorldButtonScenario.getSingleton().
                getCurHandlingContentButton();
            Point pt = CDWorldButtonScenario.getSingleton().getCurPoint();
            if (cd.getButtonMgr().contains(b, pt)) {
                return;
            }
            g2.setColor(CDButtonViewer.COLOR_DRAWING_ARROW);
            g2.setStroke(CDButtonViewer.STROKE_ARROW);
            Point2D.Double curPt = cd.getXform().calcPtFromScreenToWorld(pt);
            int branch = cd.getPDFViewer().onWhatBranch(CDWorldButtonScenario.
                getSingleton().getStartPoint());
            double x1 = cd.getPDFViewer().getWorldXPos() + b.getBox().x +
                branch * CDPDFViewer.PAGE_ROW_INTERVAL;
            double y1 = b.getBox().y + b.getBox().height / 2 + 
                cd.getBranchYPoses().get(branch);
            g2.drawLine((int) x1, (int) y1, (int) curPt.x, (int) curPt.y);
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

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
                    CDWorldButtonScenario.getSingleton().
                        setCurHandlingContentButton(cd.getButtonMgr().
                        getCurMouseContentButton());
                    CDWorldButtonScenario.getSingleton().setStartPoint(e.getPoint());
                    XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                        ContentPressedScene.getSingleton(), this);
                    break;
                case IMPLY:
                    CDSideButtonScenario.getSingleton().setCurHandlingSideButton(
                        cd.getButtonMgr().getCurMouseImplyButton());
                    button.setHighlight(true);
                    XCmdToChangeScene.execute(cd, 
                        CDSideButtonScenario.SideButtonPressedScene.
                        getSingleton(), this);
                    break;
                case LINK:
                    CDCmdToSetLinkPressed.execute(cd, e.getPoint());
                    XCmdToChangeScene.execute(cd, CDWorldButtonScenario.
                        LinkPressedScene.getSingleton(), this);
                    break;
                case COLOR:
                case SIDE:
                case NONE:
                    CDWorldButtonScenario.getSingleton().initializeChoosedButton();
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
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
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_A:
                    CDCmdToChooseAllContentBox.execute(cd);
                    break;
                case KeyEvent.VK_DELETE:
                    CDCmdToDeleteChoosedContentBox.execute(cd);
                    cd.getSideViewer().setHierarchyMode();
                    XCmdToChangeScene.execute(cd, CDDefaultScenario.
                        ReadyScene.getSingleton(), null);
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
            CDCmdToMoveLink.execute(cd, e);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDLinkButton button = CDWorldButtonScenario.getSingleton().getCurHandlingLinkButton();
            button.setHighlight(false);
            if (button.getInitialBox() == button.getBox()) {
                CDCmdToMakeNewBranch.execute(cd, e);
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
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_DELETE:
                    CDCmdToDeleteLinkButton.execute(cd);
                    XCmdToChangeScene.execute(cd, this.mReturnScene, null);
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
    
    public static final double MAX_DRAG_DISTANCE_TO_CLICK = 20;
        
    private CDContentButton mCurHandlingContentButton = null;
    public void setCurHandlingContentButton(CDContentButton button) {
        this.mCurHandlingContentButton = button;
    }
    public CDContentButton getCurHandlingContentButton() {
        return this.mCurHandlingContentButton;
    }
    private CDLinkButton mCurHandlingNeedButton = null;
    public void setCurHandlingNeedButton(CDLinkButton button) {
        this.mCurHandlingNeedButton = button;
    }
    public CDLinkButton getCurHandlingLinkButton() {
        return this.mCurHandlingNeedButton;
    }
    
    private ArrayList<CDContentButton> mChoosedButtons = null;
    public ArrayList<CDContentButton> getChoosedButton() {
        return this.mChoosedButtons;
    }
    public void addChoosedButton(CDContentButton b) {
        if (this.mChoosedButtons == null) {
            this.mChoosedButtons = new ArrayList<>();
        }
        if (this.mChoosedButtons.contains(b)) {
            b.setHighlight(false);
            this.mChoosedButtons.remove(b);
        } else {
            b.setHighlight(true);
            this.mChoosedButtons.add(b); 
        }
    }
    public void initializeChoosedButton() {
        if (this.mChoosedButtons == null) {
            return;
        }
        this.mChoosedButtons.forEach(b -> {
            b.setHighlight(false);
        });
        this.mChoosedButtons = null;
    }
    public boolean choosedButtonExist() {
        if (this.mChoosedButtons == null) {
            return false;
        } else if (this.mChoosedButtons.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public boolean isChoosed(CDContentButton b) {
        if (this.mChoosedButtons == null) {
            return false;
        }
        return this.mChoosedButtons.contains(b);
    }
    public CDContentButton recentlyChoosedButton() {
        return this.mChoosedButtons.get(this.mChoosedButtons.size() - 1);
    }
    
    private Point mCurPoint = null;
    public void setCurPoint(Point pt) {
        this.mCurPoint = pt;
    }
    public Point getCurPoint() {
        return this.mCurPoint;
    }
    
    private Point mStartPoint = null;
    public void setStartPoint(Point pt) {
        this.mStartPoint = pt;
        this.mCurPoint = pt;
    }
    public Point getStartPoint() {
        return this.mStartPoint;
    }
    
    
}
