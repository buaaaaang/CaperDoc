package cd.scenario;

import cd.CDScene;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XScenario;

public class CDExampleScenario extends XScenario {
    // singleton pattern
    private static CDExampleScenario mSingleton = null;
    public static CDExampleScenario createSingleton(XApp app) {
        assert (CDExampleScenario.mSingleton == null);
        CDExampleScenario.mSingleton = new CDExampleScenario(app);
        return CDExampleScenario.mSingleton;
    }
    public static CDExampleScenario getSingleton() {
        assert (CDExampleScenario.mSingleton != null);
        return CDExampleScenario.mSingleton;
    }
    
    // private constructor 
    private CDExampleScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDExampleScenario.ReadyScene.createSingleton(this));
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
}
