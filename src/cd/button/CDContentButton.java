package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class CDContentButton extends CDButton {
    private String name = null;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    // constructor
    public CDContentButton(String name, Point pos) {
        super(pos);
        this.name = name;
    }
    
    @Override
    public boolean contains(Point pt) {
        return true;
    }

    @Override
    public void draw(Graphics2D g2) {
    }
    
}
