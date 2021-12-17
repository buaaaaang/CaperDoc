package cd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class CDPtCurve {
    //constants
    public static final double MIN_DIST_BTWN_PTS = 5.0f;
    
    //fields
    private ArrayList<Point2D.Double> mPts = null;
    public ArrayList<Point2D.Double> getPts() {
        return this.mPts;
    }
    
    private Rectangle2D.Double mBoundingBox = null;
    public Rectangle2D.Double getBoundingBox() {
        return this.mBoundingBox;
    }
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    public void setColor(Color c) {
        this.mColor = c;
    }
    
    private Stroke mStroke = null;
    public Stroke getStroke() {
        return this.mStroke;
    }
    
    private int mCreatedBranch;
    public int getCreatedBranch() {
        return this.mCreatedBranch;
    }
    
    public CDPtCurve(Point2D.Double pt, Color c, Stroke s,int branch) {
        this.mPts = new ArrayList<>();
        this.mPts.add(pt);
        this.mBoundingBox = new Rectangle2D.Double(pt.x, pt.y, 0, 0);
        
        this.mColor = c;
        
        BasicStroke bs = (BasicStroke) s;
        this.mStroke = new BasicStroke(bs.getLineWidth(), bs.getEndCap(),
            bs.getLineJoin());
        
        this.mCreatedBranch = branch;
    }
    
    public void addPt(Point2D.Double pt) {
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }

    public void increaseStokeWidth(float f) {
        BasicStroke bs = (BasicStroke) this.mStroke;
        float w = bs.getLineWidth();
        w += f;
        if (w < CDPtCurveMgr.STROKE_MIN_WIDTH) {
            w = CDPtCurveMgr.STROKE_MIN_WIDTH;
        }
        BasicStroke stroke = new BasicStroke(w, bs.getEndCap(),
                bs.getLineJoin());
        this.mStroke = stroke;

    }
    
}
