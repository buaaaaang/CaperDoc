package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

abstract class CDWorldButton extends CDButton {
    
    protected Point mPosition = null;
    public Point getPosition() {
        return this.mPosition;
    }
    public void setPoition(Point pt) {
        this.mPosition = pt;
    }
    
    public CDWorldButton(String name, Point pos) {
        super(name);
        this.mPosition = pos;
    }
    
}
