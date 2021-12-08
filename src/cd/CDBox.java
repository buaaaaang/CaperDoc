package cd;



import java.awt.Point;
import java.awt.Rectangle;

public class CDBox extends Rectangle {
    
    private Point mAnchorPt = null;
    public Point getAnchorPt() {
        return this.mAnchorPt;
    }

    public CDBox(Point pt) {
        super(pt);
        this.mAnchorPt = pt;
    }
    
    public Point getEndPt() {
        int endX = this.mAnchorPt.x + this.width;
        int endY = this.mAnchorPt.y + this.height;
        Point endPt = new Point(endX, endY);
        return endPt;
    }
    
    public void update(Point pt) {
        this.setRect(this.mAnchorPt.x, this.mAnchorPt.y, 0, 0);
        this.add(pt);
    }
    
    public CDBox getReformulatedBox() {
        x = (this.width > 0) ? this.x : x + this.width;
        y = (this.height > 0) ? this.y : y + this.height;
        Point newStartPt = new Point(x, y);
        CDBox reformedRect = new CDBox(newStartPt);
        reformedRect.setSize(Math.abs(this.width), Math.abs(this.height));
        return reformedRect;
    }
}