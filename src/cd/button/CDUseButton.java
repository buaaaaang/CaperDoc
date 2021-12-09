package cd.button;

import java.awt.Point;

public class CDUseButton extends CDWorldButton{
    
    public CDUseButton(String name, Point pt) {
        super(name, pt);
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
