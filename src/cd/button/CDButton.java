package cd.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

abstract class CDButton {
    // constans
    protected static final Color HIGHLIGHT_COLOR = Color.ORANGE;
    
    // fields
    private boolean mHighlighted;
    public boolean isHighlighted() {
        return this.mHighlighted;
    }
    public void setHighlight(boolean b) {
        this.mHighlighted = b;
    }
    
    private Point mPos = null;
    public Point getPos() {
        return this.mPos;
    }
    public void setPos(Point pos) {
        this.mPos = pos;
    }
    
    // constructor
    public CDButton(Point pos) {
        this.mHighlighted = false;
        this.mPos = pos;
    }
    
    // abstract methods
    public abstract boolean contains(Point pt);
    public abstract void draw(Graphics2D g2);
}
