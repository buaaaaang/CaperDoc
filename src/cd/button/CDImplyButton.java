package cd.button;

import cd.CD;
import cd.CDButtonMgr;
import cd.CDSideViewer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    
    public CDImplyButton(String name, CD cd, CDContentButton b) {
        super(name, b.getBox().getBounds().y, cd, b);
        this.mKind = CDButton.Button.NEED;
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
        boolean b2 = pt.x < CD.HIERARCHY_WIDTH;
        return b1 && b2;
    }
    
}
