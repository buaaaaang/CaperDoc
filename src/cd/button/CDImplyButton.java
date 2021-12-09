package cd.button;

import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    public static final int HEIGHT = 50;
    
    public CDImplyButton(String name, double y) {
        super(name, y);
        this.mKind = CDButton.Button.USED;
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
