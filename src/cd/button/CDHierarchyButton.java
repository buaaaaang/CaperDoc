package cd.button;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDSideViewer;
import java.awt.Color;
import java.awt.Point;

public class CDHierarchyButton extends CDSideButton {
    public static final int HEIGHT = 50;
    public static final int GAP = 2;
    public static final int SIDE_GAP = 5;
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    
    public CDHierarchyButton(String name, double y, CD cd) {
        super(name, y, cd);
        this.mKind = CDButton.Button.HIERARCHY;
    }

    @Override
    public boolean contains(Point pt) {
        if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.IMPLY) {
            return false;
        }
        int n = this.mCD.getButtonMgr().getHierarchyButtons().indexOf(this);
        int shift = this.mCD.getSideViewer().getShiftAmount();
        boolean b1 = pt.y > (CDHierarchyButton.HEIGHT * n - shift) &&
            pt.y < (CDHierarchyButton.HEIGHT * (n + 1) - shift);
        boolean b2 = pt.x < CD.HIERARCHY_WIDTH;
        return b1 && b2;
    }
    
}
