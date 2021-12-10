package cd.button;

import java.awt.Font;
import java.awt.Point;

public abstract class CDButton {
    
    public enum Button {
        COLOR, CONTENT, IMPLY, NEED, HIERARCHY, SIDE, NONE
    }
    
    // fields
    protected Button mKind;
    public Button getKind() {
        return this.mKind;
    }
    
    protected String mName = null;
    public String getName() {
        return this.mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
    
    private boolean mHighlighted;
    public boolean isHighlighted() {
        return this.mHighlighted;
    }
    public void setHighlight(boolean b) {
        this.mHighlighted = b;
    }
    
    // constructor
    public CDButton(String name) {
        this.mHighlighted = false;
        this.mName = name;
    }
    
    // abstract methods
    public abstract boolean contains(Point pt);
//    public abstract void draw(Graphics2D g2);
}
