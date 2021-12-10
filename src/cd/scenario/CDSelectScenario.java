package cd.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import cd.CD;
import cd.CDCanvas2D;
import cd.CDPtCurve;
import cd.CDScene;
import cd.CDBox;
import cd.button.CDButton;
import cd.cmd.CDCmdToCreateCurPtCurve;
import cd.cmd.CDCmdToCreateSelectionBox;
import cd.cmd.CDCmdToDeleteSelectedPtCurves;
import cd.cmd.CDCmdToDeselectSelectedPtCurves;
import cd.cmd.CDCmdToIncreaseStrokeWidthForCurPtCurve;
import cd.cmd.CDCmdToIncreaseStrokeWidthForSelectedPtCurves;
import cd.cmd.CDCmdToScroll;
import cd.cmd.CDCmdToUpdateSelectionBox;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDSelectScenario extends XScenario {
    // singleton pattern
    private static CDSelectScenario mSingleton = null;
    public static CDSelectScenario createSingleton(XApp app) {
        assert (CDSelectScenario.mSingleton == null);
        CDSelectScenario.mSingleton = new CDSelectScenario(app);
        return CDSelectScenario.mSingleton;
    }
    public static CDSelectScenario getSingleton() {
        assert (CDSelectScenario.mSingleton != null);
        return CDSelectScenario.mSingleton;
    }
    // privae constructor 
    private CDSelectScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(CDSelectScenario.SelectReadyScene.createSingleton(this));
        this.addScene(CDSelectScenario.SelectScene.createSingleton(this));
        this.addScene(CDSelectScenario.SelectedScene.createSingleton(this));
    }

    public static class SelectReadyScene extends CDScene{
        private static SelectReadyScene mSingleton = null;
        public static SelectReadyScene createSingleton(XScenario scenario) {
            assert (SelectReadyScene.mSingleton == null);
            SelectReadyScene.mSingleton = new SelectReadyScene(scenario);
            return SelectReadyScene.mSingleton;
        }
        public static SelectReadyScene getSingleton() {
            assert (SelectReadyScene.mSingleton != null);
            return SelectReadyScene.mSingleton;
        }
        
        public SelectReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDButton button = cd.getButtonMgr().checkButton(e.getPoint());
            CDButton.Button kind = button.getKind();
            switch (kind) {
                case NONE:
                    CDCmdToCreateSelectionBox.execute(cd, e.getPoint());    
                    XCmdToChangeScene.execute(cd, 
                        CDSelectScenario.SelectScene.getSingleton(), 
                        this.getReturnScene());
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
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
            case KeyEvent.VK_CLOSE_BRACKET:
                CDCmdToIncreaseStrokeWidthForCurPtCurve.execute(cd, 
                        CDCanvas2D.STROKE_WIDTH_INCREMENT);
                break;
            case KeyEvent.VK_OPEN_BRACKET:
                CDCmdToIncreaseStrokeWidthForCurPtCurve.execute(cd, 
                        CDCanvas2D.STROKE_WIDTH_INCREMENT);
                break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_SHIFT:
                    if(!cd.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                        XCmdToChangeScene.execute(cd, 
                            CDSelectScenario.SelectedScene.getSingleton(), 
                            null);
                    } else {
                        XCmdToChangeScene.execute(cd, 
                            CDDefaultScenario.ReadyScene.getSingleton(), 
                            null);
                    }
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
    
    public static class SelectScene extends CDScene {
        private static SelectScene mSingleton = null;
        public static SelectScene createSingleton(XScenario scenario) {
            assert (SelectScene.mSingleton == null);
            SelectScene.mSingleton = new SelectScene(scenario);
            return SelectScene.mSingleton;
        }
        public static SelectScene getSingleton() {
            assert (SelectScene.mSingleton != null);
            return SelectScene.mSingleton;
        }
        
        public SelectScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDCmdToUpdateSelectionBox.execute(cd, e);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            ((CDSelectScenario) this.mScenario).setCurSelectionBox(null);
            cd.getPtCurveMgr().setPastSelectedPtCurves(
                cd.getPtCurveMgr().getSelectedPtCurves());
            XCmdToChangeScene.execute(cd, 
                CDSelectScenario.SelectReadyScene.getSingleton(), 
                this.getReturnScene());
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
                case KeyEvent.VK_SHIFT:
                    if(cd.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                        XCmdToChangeScene.execute(cd, 
                            CDSelectScenario.SelectedScene.getSingleton(), 
                            null);
                    } else {
                        XCmdToChangeScene.execute(cd, 
                            CDDefaultScenario.ReadyScene.getSingleton(), null);
                    }
                    break;
            }
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            if (((CDSelectScenario) this.mScenario).getSelectionBox() != null){
//                g2.setColor(CDCanvas2D.COLOR_SELECTION_BOX);
//                g2.setStroke(CDCanvas2D.STROKE_SELECTION_BOX);
//                g2.draw(((CDSelectScenario) this.mScenario).getSelectionBox());
                ((CDSelectScenario) this.mScenario).drawSelectionBox(g2);
            }
        }
        
    }

    public static class SelectedScene extends CDScene {
        private static SelectedScene mSingleton = null;
        public static SelectedScene createSingleton(XScenario scenario) {
            assert (SelectedScene.mSingleton == null);
            SelectedScene.mSingleton = new SelectedScene(scenario);
            return SelectedScene.mSingleton;
        }
        public static SelectedScene getSingleton() {
            assert (SelectedScene.mSingleton != null);
            return SelectedScene.mSingleton;
        }
        
        public SelectedScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            Point pt = e.getPoint();
            CDCmdToCreateCurPtCurve.execute(cd, pt);        
            XCmdToChangeScene.execute(cd, 
                CDDrawScenario.DrawScene.getSingleton(), this);

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
                case KeyEvent.VK_CLOSE_BRACKET:
                    CDCmdToIncreaseStrokeWidthForSelectedPtCurves.execute(cd, 
                            CDCanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
                case KeyEvent.VK_OPEN_BRACKET:
                    CDCmdToIncreaseStrokeWidthForSelectedPtCurves.execute(cd, 
                            -CDCanvas2D.STROKE_WIDTH_INCREMENT);
                    break;
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(cd, 
                        CDSelectScenario.SelectReadyScene.getSingleton(), 
                        this);
                    break;
            }          
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_ESCAPE:
                    CDCmdToDeselectSelectedPtCurves.execute(cd);
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_DELETE:
                    CDCmdToDeleteSelectedPtCurves.execute(cd);
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
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
    
    private CDBox mCurSelectionBox = null;
    
    public CDBox getSelectionBox(){
        return this.mCurSelectionBox;
    }
    
    public void setCurSelectionBox(CDBox selectionBox) {
        this.mCurSelectionBox = selectionBox;
    }
    
    public void drawSelectionBox(Graphics2D g2) {
        g2.setColor(CDCanvas2D.COLOR_SELECTION_BOX);
        g2.setStroke(CDCanvas2D.STROKE_SELECTION_BOX);
        g2.draw(this.getSelectionBox());
    }
    
    public void updateSelectedPtCurves() {
        CD cd = (CD) this.mXApp;
        AffineTransform at = cd.getXform().getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(
            this.mCurSelectionBox);
        
        ArrayList<CDPtCurve> newlySelectedPtCurves = new ArrayList<>();
        ArrayList<CDPtCurve> candidatePtCurves = new ArrayList<>();
        candidatePtCurves.addAll(cd.getPtCurveMgr().getPtCurves());
        candidatePtCurves.addAll(cd.getPtCurveMgr().getSelectedPtCurves());
        candidatePtCurves.removeAll(
            cd.getPtCurveMgr().getPastSelectedPtCurves());
        
        for (CDPtCurve ptCurve: candidatePtCurves) {
            if (worldSelectionBoxShape.intersects(ptCurve.getBoundingBox())
                || ptCurve.getBoundingBox().isEmpty()){
                for (Point2D.Double pt: ptCurve.getPts()) {
                    if (worldSelectionBoxShape.contains(pt)) {
                        newlySelectedPtCurves.add(ptCurve);
                        break;
                    }
                }
            }
        }
        
        candidatePtCurves.removeAll(newlySelectedPtCurves);
        cd.getPtCurveMgr().setPtCurves(candidatePtCurves);
        newlySelectedPtCurves.addAll(
            cd.getPtCurveMgr().getPastSelectedPtCurves());
        cd.getPtCurveMgr().setSelectedPtCurves(newlySelectedPtCurves);
    }
}
