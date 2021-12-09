package cd.button;

import java.awt.Point;

public class CDRemainderButton extends CDButton {
    
    public CDRemainderButton() {
        this.mKind = CDButton.Button.NONE;
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
