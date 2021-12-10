package cd.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;

public class CDColorButton extends CDButton {
    //constants
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    
    private int mScreenPositionFromRight;
    public int getScreenPositionFromRight() {
        return this.mScreenPositionFromRight;
    }
    private int mScreenPositionFromTop;
    public int getScreenPositionFromTop() {
        return this.mScreenPositionFromTop;
    }
    private int mRadius;
    public int getRadius() {
        return this.mRadius;
    }
    private int mHighLightRadius;
    public int getHighLightRadius() {
        return this.mHighLightRadius;
    }
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    private JPanel mPanel = null;
    
    public CDColorButton(Color color, int right, int top, int radius, 
        JPanel panel) {
        super("");
        this.mKind = CDButton.Button.COLOR;
        this.mColor = color;
        this.mScreenPositionFromRight = right;
        this.mScreenPositionFromTop = top;
        this.mRadius = radius; 
        this.mHighLightRadius = (int) (this.mRadius * 1.3);
        this.mPanel = panel;
    }

    @Override
    public boolean contains(Point pt) {
        int dx = this.mPanel.getWidth() - this.mScreenPositionFromRight - pt.x;
        int dy = this.mScreenPositionFromTop - pt.y;
        return ((dx*dx + dy*dy) < this.mRadius * this.mRadius);
    }
}
