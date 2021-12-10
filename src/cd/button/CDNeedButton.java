package cd.button;

import cd.CD;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class CDNeedButton extends CDWorldButton{
    
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
    
    private int mWidth;
    public int getWidth() {
        return this.mWidth;
    }
    public void setWidth(int width) {
        this.mWidth = width;
    }
    
    CD mCD = null;
    
    private Point2D.Double mInitialPosition = null;
    public void setInitialPosition() {
        this.mInitialPosition = this.mWorldPosition;
    }
    
    private Point mInitialPressedPoint = null;
    public Point getInitialPressedPoint() {
        return this.mInitialPressedPoint;
    }
    public void setInitialPressedPoint(Point pt) {
        this.mInitialPressedPoint = pt;
    }
    
    private boolean Moved;
    public boolean hasMoved() {
        return this.Moved;
    }
    public void setMoved(boolean m) {
        this.Moved = m;
    }
    
    private Rectangle mBox= null;
    public Rectangle getBox() {
        return this.mBox;
    }
    
    public CDNeedButton(String name, double y, Point2D.Double pt, CD cd) {
        super(name, pt);
        this.mKind = CDButton.Button.NEED;
        this.mCD = cd;
        this.mWidth = 0;
        this.mContentPosition = y;
        this.mBox = new Rectangle((int) this.getPosition().x, 
            (int) this.getPosition().y, 30, HEIGHT);
    }
    
    public void moveScreenPosition(int dx, int dy) {
        Point2D.Double p0 = this.mInitialPosition;
        Point p0_screen = this.mCD.getXform().calcPtFromWorldToScreen(p0);
        Point p_screen = new Point(p0_screen.x + dx, p0_screen.y + dy);
        this.mWorldPosition = 
            this.mCD.getXform().calcPtFromScreenToWorld(p_screen);
        this.Moved = true;
        this.mBox = new Rectangle((int) this.getPosition().x, 
            (int) this.getPosition().y, this.mWidth, HEIGHT);
    }
    
    @Override
    public boolean contains(Point pt) {
        Rectangle box = new Rectangle((int) this.getPosition().x, 
            (int) this.getPosition().y, this.mWidth, HEIGHT);
        return box.contains(
            this.mCD.getXform().calcPtFromScreenToWorld(pt));
    }
    
}
