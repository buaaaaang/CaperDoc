package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

abstract class CDWorldButton extends CDButton {
    protected String mName = null;
    public String getName() {
        return this.mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
    
    protected Point mPosition = null;
    public Point getPosition() {
        return this.mPosition;
    }
    public void setPoition(Point pt) {
        this.mPosition = pt;
    }
    
    public CDWorldButton(String name, Point pos) {
        super();
        this.mName = name;
        this.mPosition = pos;
    }
    
}
