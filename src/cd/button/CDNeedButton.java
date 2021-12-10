package cd.button;

import java.awt.Point;

public class CDNeedButton extends CDWorldButton{
    
    public CDNeedButton(String name, Point pt) {
        super(name, pt);
        this.mKind = CDButton.Button.IMPLY;
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}
