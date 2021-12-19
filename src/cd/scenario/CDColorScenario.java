package cd.scenario;

import cd.CD;
import cd.CDScene;
import cd.button.CDButton;
import cd.button.CDColorButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDColorScenario extends XScenario {
    // singleton pattern
    private static CDColorScenario mSingleton = null;
    public static CDColorScenario createSingleton(XApp app) {
        assert (CDColorScenario.mSingleton == null);
        CDColorScenario.mSingleton = new CDColorScenario(app);
        return CDColorScenario.mSingleton;
    }
    public static CDColorScenario getSingleton() {
        assert (CDColorScenario.mSingleton != null);
        return CDColorScenario.mSingleton;
    }
    
    // private constructor 
    private CDColorScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDColorScenario.ColorScene.createSingleton(this));
    }
    
    public static class ColorScene extends CDScene {
        private static ColorScene mSingleton = null;
        public static ColorScene createSingleton(XScenario scenario) {
            assert (ColorScene.mSingleton == null);
            ColorScene.mSingleton = new ColorScene(scenario);
            return ColorScene.mSingleton;
        }
        public static ColorScene getSingleton() {
            assert (ColorScene.mSingleton != null);
            return ColorScene.mSingleton;
        }
        
        public ColorScene(XScenario scenario) {
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
            CDColorScenario scenario = (CDColorScenario)this.mScenario;
            CD cd = (CD) scenario.getApp();
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case COLOR:
                    CDColorButton selectedColorButton = (CDColorButton)button;
                    Color originalColor = 
                        cd.getCanvas().getCurColorForPtCurve();
                    Color newColor = selectedColorButton.getColor();
                    if ((selectedColorButton == 
                        scenario.getCurHandlingButton()) &&
                        (originalColor != newColor)) {
                        cd.getCanvas().setCurColorForPtCurve(newColor);
                        Color highlightColor = new Color(255, 200, 0, 128);
                        if (((originalColor.getAlpha() != 
                            highlightColor.getAlpha()) && 
                            (newColor.getAlpha() == 
                            highlightColor.getAlpha())) ||
                            ((originalColor.getAlpha() == 
                            highlightColor.getAlpha()) && 
                            (newColor.getAlpha() != 
                            highlightColor.getAlpha()))) {
                            Stroke savedStroke = 
                                cd.getCanvas().getSavedStrokeForPtCurve();
                            Stroke curStroke = 
                                cd.getCanvas().getCurStrokeForPtCurve();
                            cd.getCanvas().setSavedStrokeForPtCurve(curStroke);
                            cd.getCanvas().setCurStrokeForPtCurve(savedStroke);
                        }
                        for (CDColorButton colorButton : 
                            cd.getButtonMgr().getColorButtons()) {
                            colorButton.setHighlight(false);
                        }
                        selectedColorButton.setHighlight(true);
                        
                    }
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), 
                        null);
                    scenario.setCurHandlingButton(null);
                case NONE:
                case SIDE:
                case CONTENT:
                case HIERARCHY:
                case LINK:
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), 
                        null);
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
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

    }
    
    private CDColorButton mCurHandlingColorButton = null;
    public void setCurHandlingButton(CDColorButton button) {
        this.mCurHandlingColorButton = button;
    }
    public CDColorButton getCurHandlingButton() {
        return this.mCurHandlingColorButton;
    }
}
