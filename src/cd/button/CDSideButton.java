package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

abstract class CDSideButton extends CDButton {
    protected String mName = null;
    public String getName() {
        return this.mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
    
    protected double mContentPosition;
    public double getContentPosition() {
        return this.mContentPosition;
    }
    
    protected Point mPosition = null;
    public Point getPosition() {
        return this.mPosition;
    }
    public void setPoition(Point pt) {
        this.mPosition = pt;
    }
    
    // constructor
    public CDSideButton(String name, double y) {
        this.mName = name;
        this.mContentPosition = y;
    }
    
}
