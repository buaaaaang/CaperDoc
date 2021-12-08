package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;

public class CDConceptButton extends CDButton {
    
    public enum Mode {}
    
    public CDConceptButton(Point pos) {
        super(pos);
    }
    @Override
    public boolean contains(Point pt) {
        return true;
    }

    @Override
    public void draw(Graphics2D g2) {
    }
    
}
