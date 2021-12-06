package cd.button;

import java.awt.Graphics2D;
import java.awt.Point;

public class ConceptButton extends CDButton {
    
    public enum Mode {}

    @Override
    boolean Contains(Point pt) {
        return true;
    }

    @Override
    void draw(Graphics2D g2) {
    }
    
}
