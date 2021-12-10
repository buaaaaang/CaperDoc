package cd.button;

import java.awt.Point;

public class CDSideRemainderButton extends CDButton {
    
    public CDSideRemainderButton() {
        super("");
        this.mKind = CDButton.Button.SIDE;
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
