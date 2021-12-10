package cd.button;

import cd.CD;
import cd.CDSideViewer;
import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    
    public CDImplyButton(String name, double y, CD cd) {
        super(name, y, cd);
        this.mKind = CDButton.Button.IMPLY;
    }
    
    @Override
    public boolean contains(Point pt) {
        if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.HIERARCHY) {
            return false;
        }
        int n = this.mCD.getSideViewer().getImplyContent().getImplyButtons().
            indexOf(this);
        int shift = this.mCD.getSideViewer().getShiftAmount();
        boolean b1 = pt.y > (CDSideButton.HEIGHT * n - shift) &&
            pt.y < (CDSideButton.HEIGHT * (n + 1) - shift);
        boolean b2 = pt.x < CD.HIERARCHY_WIDTH - GAP_SIDE;
        System.out.println("" + (b1 && b2));
        return b1 && b2;
    }
    
}
