package cd.button;

import cd.CD;
import cd.CDSideViewer;
import java.awt.Point;

public class CDImplyButton extends CDSideButton {
    
    public CDImplyButton(String name, double y) {
        super(name, y);
        this.mKind = CDButton.Button.IMPLY;
    }

}
