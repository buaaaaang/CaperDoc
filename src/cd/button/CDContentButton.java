package cd.button;

import java.awt.Point;

public class CDContentButton extends CDWorldButton {
    
    public CDContentButton(String name, Point pos) {
        super(name, pos);
    }

    @Override
    public boolean contains(Point pt) {
        return true;
    }
    
}