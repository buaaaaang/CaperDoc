package cd.button;

import cd.CD;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class CDNeedButton extends CDWorldButton{
    
    public static final Color COLOR = new Color(255,0,0,128);
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    public static final int HEIGHT = 20;
    public static final int GAP_SIDE = 10;
    public static final int GAP_UP = 10;
    
    private CDContentButton mContentButton = null;
    public CDContentButton getContentButton() {
        return this.mContentButton;
    }
    private int mWidth;
    public int getWidth() {
        return this.mWidth;
    }
    public void setWidth(int width) {
        this.mWidth = width;
    }
    
    CD mCD = null;
    
    public CDNeedButton(String name, Point pt, CD cd, CDContentButton b) {
        super(name, pt);
        this.mKind = CDButton.Button.IMPLY;
        this.mCD = cd;
        this.mWidth = 0;
        this.mContentButton = b;
    }
    
    @Override
    public boolean contains(Point pt) {
        Rectangle box = new Rectangle(pt.x, pt.y, this.mWidth, HEIGHT);
        return box.contains(this.mCD.getXform().calcPtFromScreenToWorld(pt));
    }
    
}
