package cd.button;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDSideViewer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class CDSideButton extends CDButton {
    
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    public static final int HEIGHT = 20;
    public static final int GAP_SIDE = 10;
    public static final int GAP_SIDE_TEXT = 20;
    public static final int GAP_UP_TEXT = 12;
    
    
    protected double mContentPosition;
    public double getContentPosition() {
        return this.mContentPosition;
    }
    
    protected CDContentButton mContentButton = null;
    public CDContentButton getContentButton() {
        return this.mContentButton;
    }
    
    protected CD mCD;
    
    // constructor
    public CDSideButton(String name, double y, CD cd, CDContentButton b) {
        super(name);
        this.mContentPosition = y;
        this.mCD = cd;
        this.mContentButton = b;
    }
    
}
