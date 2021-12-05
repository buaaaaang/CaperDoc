package cd.scenario;

import cd.CD;
import cd.CDScene;
import cd.cmd.CDCmdToScroll;
import java.awt.Graphics2D;
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
            CDCmdToScroll.execute(cd, (e.getWheelRotation() > 0) ? -1 : 1);
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
}
