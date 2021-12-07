package cd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDCanvas2D extends JPanel {
    // fields and constants
    private static final Color COLOR_PT_CURVE_DEFAULT = new Color(0, 0, 0, 255);
    public static final Color COLOR_SELECTION_BOX = new Color(255, 0, 0, 64);
    public static final Color COLOR_CROP_BOX = Color.CYAN;
    private static final Color COLOR_SELECTED_PT_CURVE = Color.ORANGE;
    private static final Color COLOR_INFO = new Color(255,0,0,128);
    
    private static final Stroke STROKE_PT_CURVE_DEFAULT = new BasicStroke(5f,
        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public static final Stroke STROKE_SELECTION_BOX = new BasicStroke(2f);
    public static final Stroke STROKE_CROP_BOX = new BasicStroke(2f);
    
    private static final Font FONT_INFO = 
        new Font("Monospaced", Font.PLAIN, 24);
    private static final int INFO_TOP_ALIGNMENT_X = 1000;
    private static final int INFO_TOP_ALIGNMENT_Y = 30;
    
    private static final double PEN_TIP_OFFSET = 30.0;
    public static final float STROKE_WIDTH_INCREMENT = 1f;
    public static final float STROKE_MIN_WIDTH = 1f;
    
    private CD mCD = null;
    private Color mCurColorForPtCurve = null;
    public Color getCurColorForPtCurve() {
        return this.mCurColorForPtCurve;
    }
    public void setCurColorForPtCurve(Color c) {
        this.mCurColorForPtCurve = c;
    }
    private Stroke mCurStrokeForPtCurve = null;
    public Stroke getCurStrokeForPtCurve() {
        return this.mCurStrokeForPtCurve;
    }
    
    public CDCanvas2D(CD cd) {
        this.mCD = cd;
        this.mCurColorForPtCurve = CDCanvas2D.COLOR_PT_CURVE_DEFAULT;
        this.mCurStrokeForPtCurve = CDCanvas2D.STROKE_PT_CURVE_DEFAULT;
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.transform(this.mCD.getXform().getCurXformFromWorldToScreen());
        
        // render commom world objects
        this.drawPtCurves(g2);
        this.drawSelectedPtCurves(g2);
        this.drawCurPtCurve(g2);
        
        g2.transform(this.mCD.getXform().getCurXformFromScreenToWorld());
        
        // render the current scene's world objects
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.renderWorldObjects(g2);
        
        //render common screen objects
        this.drawInfo(g2);        
        this.drawPenTip(g2);
        
        //render the current scene's screen objects
        curScene.renderScreenObjects(g2);
    }
    
    private void drawPtCurve(Graphics2D g2, CDPtCurve ptCurve, Color c, 
        Stroke s){
        Path2D.Double path = new Path2D.Double();
        ArrayList<Point2D.Double> pts = ptCurve.getPts();
        if (pts.size() < 2) {
            return;
        }
        Point2D.Double pt = pts.get(0);
        path.moveTo(pt.x, pt.y);
        if (pts.size() < 3) {
            pt = pts.get(1);
            path.lineTo(pt.x, pt.y);
        } else {
            Point2D.Double pt0 = null;
            Point2D.Double pt1 = pts.get(0);
            Point2D.Double pt2 = pts.get(1);
            Point2D.Double pt3 = pts.get(2);
            Point2D.Double ctrlPt1 = new Point2D.Double(
                pt1.x*(2.0/3.0)+pt2.x*(1.0/3.0),
                pt1.y*(2.0/3.0)+pt2.y*(1.0/3.0));
            Point2D.Double ctrlPt2 = new Point2D.Double(
                (1.0 / 6.0)*(pt1.x - pt3.x) + pt2.x,
                (1.0 / 6.0)*(pt1.y - pt3.y) + pt2.y);
            path.curveTo(ctrlPt1.x, ctrlPt1.y, ctrlPt2.x, ctrlPt2.y, 
                pt2.x, pt2.y);
            
            for (int i = 1; i < (pts.size() - 2); i++) {
                pt0 = pts.get(i - 1);
                pt1 = pts.get(i);
                pt2 = pts.get(i + 1);
                pt3 = pts.get(i + 2);
                ctrlPt1 = new Point2D.Double(
                    (1.0 / 6.0) * (pt2.x - pt0.x) + pt1.x,
                    (1.0 / 6.0) * (pt2.y - pt0.y) + pt1.y);
                ctrlPt2 = new Point2D.Double(
                    (1.0 / 6.0) * (pt1.x - pt3.x) + pt2.x,
                    (1.0 / 6.0) * (pt1.y - pt3.y) + pt2.y);
                path.curveTo(ctrlPt1.x, ctrlPt1.y, ctrlPt2.x, ctrlPt2.y, 
                    pt2.x, pt2.y);                   
            }
            ctrlPt1 = new Point2D.Double(
                (1.0 / 6.0) * (pt3.x - pt1.x) + pt2.x,
                (1.0 / 6.0) * (pt3.y - pt1.y) + pt2.y);
            ctrlPt2 = new Point2D.Double(
                pt3.x * (2.0 / 3.0) + pt2.x * (1.0 / 3.0),
                pt3.y * (2.0 / 3.0) + pt2.y * (1.0 / 3.0));
            path.curveTo(ctrlPt1.x, ctrlPt1.y, ctrlPt2.x, ctrlPt2.y, 
                pt3.x, pt3.y);          
        }
        
        g2.setColor(c);
        g2.setStroke(s);
        g2.draw(path);
    }

    private void drawPtCurves(Graphics2D g2) {
        this.mCD.getPtCurveMgr().getPtCurves().forEach(ptCurve -> {
            this.drawPtCurve(g2, ptCurve, ptCurve.getColor(), 
                ptCurve.getStroke());
        });
    }

    private void drawCurPtCurve(Graphics2D g2) {
        CDPtCurve ptCurve = this.mCD.getPtCurveMgr().getCurPtCurve();
        if(ptCurve != null){
            this.drawPtCurve(g2, ptCurve, ptCurve.getColor(), 
                ptCurve.getStroke());
        }
    }

    private void drawSelectedPtCurves(Graphics2D g2) {
        this.mCD.getPtCurveMgr().getSelectedPtCurves().forEach(selectedPtCurve -> {this.drawPtCurve(g2, selectedPtCurve, 
                CDCanvas2D.COLOR_SELECTED_PT_CURVE, 
                selectedPtCurve.getStroke());
        });
    }

    private void drawInfo(Graphics2D g2) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        String str = curScene.getClass().getSimpleName();
        g2.setColor(CDCanvas2D.COLOR_INFO);
        g2.setFont(CDCanvas2D.FONT_INFO);
        g2.drawString(str, CDCanvas2D.INFO_TOP_ALIGNMENT_X, 
                CDCanvas2D.INFO_TOP_ALIGNMENT_Y);
    } 

    public void increaseStrokeWidthForCurPtCurve(float f) {
        BasicStroke bs = (BasicStroke) this.mCurStrokeForPtCurve;
        float w = bs.getLineWidth();
        w += f;
        if (w < CDCanvas2D.STROKE_MIN_WIDTH) {
            w = CDCanvas2D.STROKE_MIN_WIDTH;
        }
        this.mCurStrokeForPtCurve = new BasicStroke(w, bs.getEndCap(),
            bs.getLineJoin());
    }
    
    private void drawPenTip(Graphics2D g2) {
        BasicStroke bs = (BasicStroke) this.mCurStrokeForPtCurve;
        Point2D.Double worldPt0 = new Point2D.Double(0.0, 0.0);
        Point2D.Double worldPt1 = new Point2D.Double(bs.getLineWidth(), 0.0);
        Point screenPt0 = this.mCD.getXform().calcPtFromWorldToScreen(
            worldPt0);
        Point screenPt1 = this.mCD.getXform().calcPtFromWorldToScreen(
            worldPt1);
        double d = screenPt0.distance(screenPt1);
        double r = d / 2.0;
        Point2D.Double ctr = new Point2D.Double(
            this.getWidth() - CDCanvas2D.PEN_TIP_OFFSET,
            CDCanvas2D.PEN_TIP_OFFSET);
        Ellipse2D.Double e = new Ellipse2D.Double(ctr.x - r, ctr.y - r, d, d);
        g2.setColor(this.mCurColorForPtCurve);
        g2.fill(e);
    }
}
