package cd.scenario;

import cd.CD;
import cd.CDPtCurve;
import cd.CDScene;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDDrawScenario extends XScenario {
    // singleton pattern
    private static CDDrawScenario mSingleton = null;
    public static CDDrawScenario createSingleton(XApp app) {
        assert (CDDrawScenario.mSingleton == null);
        CDDrawScenario.mSingleton = new CDDrawScenario(app);
        return CDDrawScenario.mSingleton;
    }
    public static CDDrawScenario getSingleton() {
        assert (CDDrawScenario.mSingleton != null);
        return CDDrawScenario.mSingleton;
    }
    // privae constructor 
    private CDDrawScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(CDDrawScenario.DrawScene.createSingleton(this));
        
    }

    public static class DrawScene extends CDScene{
        private static DrawScene mSingleton = null;
        public static DrawScene createSingleton(XScenario scenario) {
            assert (DrawScene.mSingleton == null);
            DrawScene.mSingleton = new DrawScene(scenario);
            return DrawScene.mSingleton;
        }
        public static DrawScene getSingleton() {
            assert (DrawScene.mSingleton != null);
            return DrawScene.mSingleton;
        }        
        
        public DrawScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDPtCurve curPtCurve = cd.getPtCurveMgr().getCurPtCurve();
            Point pt = e.getPoint();

            int size = cd.getPtCurveMgr().getCurPtCurve().getPts().size();
            Point2D.Double lastWorldPt =
                curPtCurve.getPts().get(size - 1);
            Point lastScreenPt = cd.getXform().calcPtFromWorldToScreen(
                lastWorldPt);
            if (pt.distance(lastScreenPt) < 
                CDPtCurve.MIN_DIST_BTWN_PTS) {
                return;
            }

            Point2D.Double worldPt = 
                cd.getXform().calcPtFromScreenToWorld(pt);
            cd.getPtCurveMgr().getCurPtCurve().addPt(worldPt);
            cd.getCanvas().repaint();
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {     
            CD cd = (CD) this.mScenario.getApp();
            CDPtCurve curPtCurve = cd.getPtCurveMgr().getCurPtCurve(); 
            if (curPtCurve.getPts().size() >= 2) {
                cd.getPtCurveMgr().getPtCurves().add(cd.getPtCurveMgr().getCurPtCurve());
            }
            cd.getPtCurveMgr().setCurPtCurve(null);
            XCmdToChangeScene.execute(cd, this.mReturnScene, null);
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

        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
            
        }
    }
    
}