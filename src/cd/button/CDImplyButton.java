package cd.button;

import cd.CD;
import cd.CDSideViewer;
import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    
    private double mContentXPos;
    public double getContentXPos() {
        return this.mContentXPos;
    }
    
    public CDImplyButton(String name, double x, double y) {
        super(name, y);
        this.mContentXPos = x;
        this.mKind = CDButton.Button.IMPLY;
    }

}
