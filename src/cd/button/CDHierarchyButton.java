package cd.button;

import java.awt.Point;

public class CDHierarchyButton extends CDWorldButton {
    
    public CDHierarchyButton(String name, Point pos) {
        super(name, pos);
    }

    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
