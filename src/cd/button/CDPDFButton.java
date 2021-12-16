package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class CDPDFButton extends CDButton {
    
    protected Rectangle mBox = null;
    public Rectangle getBox() {
        return this.mBox;
    }
    
    public CDPDFButton(String name, Rectangle box) {
        super(name);
        this.mBox = box;
    }
    
}
