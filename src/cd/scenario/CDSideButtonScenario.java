package cd.scenario;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDPDFViewer;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import cd.button.CDLinkButton;
import java.awt.Color;
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

public class CDSideButtonScenario extends XScenario {
    // singleton pattern
    private static CDSideButtonScenario mSingleton = null;
    public static CDSideButtonScenario createSingleton(XApp app) {
        assert (CDSideButtonScenario.mSingleton == null);
        CDSideButtonScenario.mSingleton = new CDSideButtonScenario(app);
        return CDSideButtonScenario.mSingleton;
    }
    public static CDSideButtonScenario getSingleton() {
        assert (CDSideButtonScenario.mSingleton != null);
        return CDSideButtonScenario.mSingleton;
    }
    
    // private constructor 
    private CDSideButtonScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDSideButtonScenario.HierarchyPressedScene.
            createSingleton(this));
        this.addScene(CDSideButtonScenario.GenerateLinkScene.
            createSingleton(this));
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
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case NONE:
                case COLOR:
                case CONTENT:
                case LINK:
                    CDHierarchyButton hb = CDSideButtonScenario.
                        getSingleton().getCurHandlingHierarchyButton();
                    CDSideButtonScenario.getSingleton().setDummyName(
                        hb.getName());
                    CDSideButtonScenario.getSingleton().setDummyContentPosition(
                        hb.getContentPosition());
                    XCmdToChangeScene.execute(cd, 
                        CDSideButtonScenario.GenerateLinkScene.
                        getSingleton(), this);
                    break;
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButtonMgr mgr = cd.getButtonMgr();
            CDButton button = mgr.checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case HIERARCHY:
                    if (cd.getButtonMgr().getCurWorkingHierarchyButton() ==
                        CDSideButtonScenario.getSingleton().
                        getCurHandlingHierarchyButton()) {
                        cd.getXform().goToYPos((int) cd.getButtonMgr().
                            getCurWorkingHierarchyButton().
                            getContentPosition());
                    }
                    break;
            }
            CDSideButtonScenario.getSingleton().getCurHandlingHierarchyButton().
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
    
    public static class GenerateLinkScene extends CDScene {
        private static GenerateLinkScene mSingleton = null;
        public static GenerateLinkScene createSingleton(XScenario scenario) {
            assert (GenerateLinkScene.mSingleton == null);
            GenerateLinkScene.mSingleton = new GenerateLinkScene(scenario);
            return GenerateLinkScene.mSingleton;
        }
        public static GenerateLinkScene getSingleton() {
            assert (GenerateLinkScene.mSingleton != null);
            return GenerateLinkScene.mSingleton;
        }
        
        public GenerateLinkScene(XScenario scenario) {
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
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case SIDE:
                case HIERARCHY:
                    CDSideButtonScenario.getSingleton().initializeDummy();
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                case NONE:
                case LINK:
                case COLOR:
                case CONTENT:
                    Point2D.Double wPt = cd.getXform().
                        calcPtFromScreenToWorld(e.getPoint());
                    CDSideButtonScenario.getSingleton().setDummyPt(
                        new Point((int) wPt.x, (int) wPt.y));
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButtonMgr mgr = cd.getButtonMgr();
            CDButton button = mgr.checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            int branch = cd.getPDFViewer().onWhatBranch(e.getPoint());
            if (branch < 0) {
                CDSideButtonScenario.getSingleton().initializeDummy();
                XCmdToChangeScene.execute(cd, 
                    CDDefaultScenario.ReadyScene.getSingleton(), null);
            } else {
                CDSideButtonScenario s = 
                    CDSideButtonScenario.getSingleton();
                Rectangle box = new Rectangle(s.getDummyPt().x - branch * 
                    CDPDFViewer.PAGE_ROW_INTERVAL - 
                    (int) cd.getPDFViewer().getWorldXPos() - (int) 
                    (0.5 * s.getDummyWidth()), s.getDummyPt().y - 
                    (int) (0.5 * CDLinkButton.HEIGHT) - 
                    cd.getBranchYPoses().get(branch), s.getDummyWidth(),
                    CDLinkButton.HEIGHT);
                CDLinkButton b = new CDLinkButton(s.getDummyName(), 
                    s.getDummyContentPosition(), box);
                cd.getButtonMgr().addLinkButton(b);
                s.initializeDummy();
                XCmdToChangeScene.execute(cd, 
                    CDDefaultScenario.ReadyScene.getSingleton(), null);
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
            CDSideButtonScenario s = CDSideButtonScenario.getSingleton();
            g2.setFont(CDLinkButton.FONT);
            if (s.getDummyWidth() == 0) {
                s.setDummyWidth(2 * CDLinkButton.GAP_SIDE + g2.getFontMetrics().
                stringWidth(s.getDummyName()));
            }
            int xPos = s.getDummyPt().x - (int) (0.5 * s.getDummyWidth());
            int yPos = s.getDummyPt().y - (int) (0.5 * CDLinkButton.HEIGHT);
            g2.setColor(CDLinkButton.HIGHLIGHT_COLOR);
            g2.fillRect(xPos, yPos, s.getDummyWidth(), CDLinkButton.HEIGHT);
            g2.setColor(CDLinkButton.COLOR);
            g2.fillRect(xPos, yPos, s.getDummyWidth(), CDLinkButton.HEIGHT);
            g2.setColor(Color.black);
            g2.drawString(s.getDummyName(), CDLinkButton.GAP_SIDE + xPos, 
                yPos + CDLinkButton.GAP_UP_TEXT);
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

    }
    
    private CDHierarchyButton mCurHandlingHierarchyButton = null;
    public void setCurHandlingHierarchyButton(CDHierarchyButton button) {
        this.mCurHandlingHierarchyButton = button;
    }
    public CDHierarchyButton getCurHandlingHierarchyButton() {
        return this.mCurHandlingHierarchyButton;
    }
    
    private Point mDummyPt = null;
    public void setDummyPt(Point pt) {
        this.mDummyPt = pt;
    }
    public Point getDummyPt() {
        return this.mDummyPt;
    }    
    private int mDummyWidth;
    public void setDummyWidth(int w) {
        this.mDummyWidth = w;
    }
    public int getDummyWidth() {
        return this.mDummyWidth;
    }    
    private String mDummyName = null;
    public void setDummyName(String name) {
        this.mDummyName = name;
    }
    public String getDummyName() {
        return this.mDummyName;
    }
    private double mDummyContentPosition;
    public void setDummyContentPosition(double y) {
        this.mDummyContentPosition = y;
    }
    public double getDummyContentPosition() {
        return this.mDummyContentPosition;
    }    
    public void initializeDummy() {
        this.mDummyPt = null;
        this.mDummyWidth = 0;
        this.mDummyName = null;
        this.mDummyContentPosition = 0;
        this.mCurHandlingHierarchyButton.setHighlight(false);
    }
    
}
