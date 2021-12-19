package cd.scenario;

import cd.CD;
import cd.CDPDFViewer;
import cd.CDPtCurve;
import cd.CDScene;
import cd.cmd.CDCmdToDrawPtCurve;
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
            CDCmdToDrawPtCurve.execute(cd, e);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {     
            CD cd = (CD) this.mScenario.getApp();
            CDPtCurve curPtCurve = cd.getPtCurveMgr().getCurPtCurve(); 
            if (curPtCurve.getPts().size() >= 2) {
                cd.getPtCurveMgr().getPtCurves().add(cd.getPtCurveMgr().
                    getCurPtCurve());
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
