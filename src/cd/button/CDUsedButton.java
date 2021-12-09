package cd.button;

import java.awt.Point;

public class CDUsedButton extends CDScreenButton {
    
    public CDUsedButton(String name, double y) {
        super(name, y);
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
