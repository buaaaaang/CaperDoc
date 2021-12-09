package cd.button;

import cd.CD;
import cd.CDButtonMgr;
import java.awt.Color;
import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    public static final int HEIGHT = 50;
    public static final int GAP = 2;
    public static final int SIDE_GAP = 5;
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    
    public CDImplyButton(String name, double y, CD cd) {
        super(name, y, cd);
        this.mKind = CDButton.Button.USED;
    }
    
    @Override
    public boolean contains(Point pt) {
        int n = this.mCD.getSideViewer().getImplyContent().getImplyButtons().
            indexOf(this);
        int shift = this.mCD.getSideViewer().getShiftAmount();
        boolean b1 = pt.y > (CDImplyButton.HEIGHT * n - shift) &&
            pt.y < (CDImplyButton.HEIGHT * (n + 1) - shift);
        boolean b2 = pt.x < CD.INITIAL_HIERARCHY_WIDTH;
        return b1 && b2;
    }
    
}
