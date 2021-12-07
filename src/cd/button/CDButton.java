package cd.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

abstract class CDButton {
    // constans
    protected static final Color HIGHLIGHT_COLOR = Color.ORANGE;
    
    // fields
    private boolean mHighlighted;
    public boolean isHighlighted() {
        return this.mHighlighted;
    }
    public void setHighlightOff(boolean b) {
        this.mHighlighted = b;
    }
    
    // constructor
    public CDButton() {
        this.mHighlighted = false;
    }
    
    // abstract methods
    abstract boolean Contains(Point pt);
    abstract void draw(Graphics2D g2);
}
