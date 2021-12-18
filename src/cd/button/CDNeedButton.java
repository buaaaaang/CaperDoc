package cd.button;

import cd.CDPDFViewer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class CDNeedButton extends CDPDFButton{
    
    public static final Color COLOR = new Color(255,0,0,128);
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    public static final int HEIGHT = 60;
    public static final int GAP_SIDE = 30;
    public static final int GAP_UP_TEXT = 30;
    public static final Font FONT = new Font("Monospaced", Font.PLAIN, 30);
    
    protected double mContentPosition;
    public double getContentPosition() {
        return this.mContentPosition;
    }

    public void setBox(int width) {
        this.mBox.add(new Point(this.mBox.x + width, 
            (int) (this.mBox.y + CDNeedButton.HEIGHT)));
    }
    public void setBox(Rectangle box) {
        System.out.println(box.width + " ");
        if (box.x < -1 * (int) (box.width / 2.0) || 
            box.x > CDPDFViewer.PAGE_WIDTH - (int) (box.width / 2.0)) {
            return;
        }
        this.mBox = box;
    }
    
    private Rectangle mInitialBox = null;
    public void setInitialBox() {
        this.mInitialBox = this.mBox;
    }
    public Rectangle getInitialBox() {
        return this.mInitialBox;
    }
    
    private Point mInitialPressedPoint = null;
    public Point getInitialPressedPoint() {
        return this.mInitialPressedPoint;
    }
    public void setInitialPressedPoint(Point pt) {
        this.mInitialPressedPoint = pt;
    }
    
    public CDNeedButton(String name, double y, Rectangle box) {
        super(name, box);
        this.mKind = CDButton.Button.NEED;
        this.mContentPosition = y;
    }
    
    
}
