package cd.scenario;

import cd.CD;
import cd.CDScene;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDNavigateScenario extends XScenario {
    // singleton pattern
    private static CDNavigateScenario mSingleton = null;
    public static CDNavigateScenario createSingleton(XApp app) {
        assert (CDNavigateScenario.mSingleton == null);
        CDNavigateScenario.mSingleton = new CDNavigateScenario(app);
        return CDNavigateScenario.mSingleton;
    }
    public static CDNavigateScenario getSingleton() {
        assert (CDNavigateScenario.mSingleton != null);
        return CDNavigateScenario.mSingleton;
    }
      
    // private constructor 
    private CDNavigateScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDNavigateScenario.ZoomPanReadyScene.
            createSingleton(this));
        this.addScene(CDNavigateScenario.PanScene.createSingleton(this));
    }
    
    public static class ZoomPanReadyScene extends CDScene {
        private static ZoomPanReadyScene mSingleton = null;
        public static ZoomPanReadyScene createSingleton(XScenario scenario) {
            assert (ZoomPanReadyScene.mSingleton == null);
            ZoomPanReadyScene.mSingleton = new ZoomPanReadyScene(scenario);
            return ZoomPanReadyScene.mSingleton;
        }
        public static ZoomPanReadyScene getSingleton() {
            assert (ZoomPanReadyScene.mSingleton != null);
            return ZoomPanReadyScene.mSingleton;
        }
        
        private ZoomPanReadyScene(XScenario scenario) {
            super(scenario);
        }
        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            cd.getXform().setStartScreenPt(e.getPoint());
            XCmdToChangeScene.execute(cd, 
                CDNavigateScenario.PanScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
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
                case KeyEvent.VK_CONTROL:
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
    
    public static class PanScene extends CDScene {
        private static PanScene mSingleton = null;
        public static PanScene createSingleton(XScenario scenario) {
            assert (PanScene.mSingleton == null);
            PanScene.mSingleton = new PanScene(scenario);
            return PanScene.mSingleton;
        }
        public static PanScene getSingleton() {
            assert (PanScene.mSingleton != null);
            return PanScene.mSingleton;
        }
        
        public PanScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            cd.getXform().translateTo(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            cd.getXform().setStartScreenPt(null);
            XCmdToChangeScene.execute(cd, CDNavigateScenario.
                ZoomPanReadyScene.getSingleton(), this.mReturnScene);
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
                case KeyEvent.VK_CONTROL:
                    cd.getXform().setStartScreenPt(null);
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
    
}