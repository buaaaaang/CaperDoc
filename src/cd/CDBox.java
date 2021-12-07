package cd;



import java.awt.Point;
import java.awt.Rectangle;

public class CDBox extends Rectangle {
    
    private Point mAnchorPt = null;

    public CDBox(Point pt) {
        super(pt);
        this.mAnchorPt = pt;
    }
    
    public void update(Point pt) {
        this.setRect(this.mAnchorPt.x, this.mAnchorPt.y, 0, 0);
        this.add(pt);
    }
}