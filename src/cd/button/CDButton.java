package cd.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

abstract class CDButton {
    
    public enum Button {
        COLOR, CONTENT, USED, USE, NONE
    }
    
    // fields
    private boolean mHighlighted;
    public boolean isHighlighted() {
        return this.mHighlighted;
    }
    public void setHighlight(boolean b) {
        this.mHighlighted = b;
    }
    
    // constructor
    public CDButton() {
        this.mHighlighted = false;
    }
    
    // abstract methods
    public abstract boolean contains(Point pt);
//    public abstract void draw(Graphics2D g2);
}
