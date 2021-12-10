package cd.button;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDSideViewer;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

abstract class CDSideButton extends CDButton {
    
    protected double mContentPosition;
    public double getContentPosition() {
        return this.mContentPosition;
    }
    
    protected CD mCD;
    
    // constructor
    public CDSideButton(String name, double y, CD cd) {
        super(name);
        this.mContentPosition = y;
        this.mCD = cd;
    }
    
}
