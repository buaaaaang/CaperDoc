package cd.button;

import java.awt.Point;

public class CDHierarchyButton extends CDSideButton {
    public static final int HEIGHT = 50;
    
    public CDHierarchyButton(String name, double y) {
        super(name, y);
        this.mKind = CDButton.Button.HIERARCHY;
    }

    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
