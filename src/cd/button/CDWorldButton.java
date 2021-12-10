package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

abstract class CDWorldButton extends CDButton {
    
    protected Point2D.Double mWorldPosition = null;
    public Point2D.Double getPosition() {
        return this.mWorldPosition;
    }
    public void setPoition(Point2D.Double pt) {
        this.mWorldPosition = pt;
    }
    
    public CDWorldButton(String name, Point2D.Double pos) {
        super(name);
        this.mWorldPosition = pos;
    }
    
}
