package cd.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;

public class CDColorButton extends CDButton {
    
    private int mScreenPositionFromRight;
    private int mScreenPositionFromTop;
    private int mRadius;
    private int mHighLightRadius;
    private Color mColor = null;
    private JPanel mPanel = null;
    
    public CDColorButton(Color color, int right, int top, int r, Point pos,
        JPanel panel) {
        super(pos);
        this.mColor = color;
        this.mScreenPositionFromRight = right;
        this.mScreenPositionFromTop = top;
        this.mRadius = r; 
        this.mHighLightRadius = (int) (this.mRadius * 1.2);
        this.mPanel = panel;
    }

    @Override
    public boolean contains(Point pt) {
        int dx = this.mPanel.getWidth() - this.mScreenPositionFromRight - pt.x;
        int dy = this.mScreenPositionFromTop - pt.y;
        return ((dx*dx + dy*dy) < this.mRadius * this.mRadius);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (this.isHighlighted()) {
            g2.setColor(CDButton.HIGHLIGHT_COLOR);
            g2.fillOval(this.mPanel.getWidth() - this.mScreenPositionFromRight,
                this.mScreenPositionFromTop, this.mHighLightRadius, 
                this.mHighLightRadius);
        }
        g2.setColor(this.mColor);
        g2.fillOval(this.mPanel.getWidth() - this.mScreenPositionFromRight,
            this.mScreenPositionFromTop, this.mRadius, this.mRadius);
    }
}
