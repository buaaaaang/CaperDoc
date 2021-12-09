package cd;

import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDSideViewer extends JPanel {
    // constants
    private static final Font FONT = new Font("Monospaced", Font.PLAIN, 10);
    private static final int GAP_LEFT = 5;
    private static final int GAP_UP = 5;
    
    public enum Mode {
        HIERARCHY, IMPLY
    }
    
    // fields
    private CD mCD = null;
    
    private Mode mMode;
    public void setHierarchyMode() {
        this.mMode = Mode.HIERARCHY;
        this.mShiftAmount = this.mLastHiearchyShift;
    }
    public void setImplyMode() {
        this.mMode = Mode.IMPLY;
        this.mLastHiearchyShift = this.mShiftAmount;
        this.mShiftAmount = 0;
    }
    
    private int mShiftAmount;
    public int getShiftAmount() {
        return this.mShiftAmount;
    }
    private int mLastHiearchyShift;
    
    private CDContentButton mImplyContent = null;
    public CDContentButton getImplyContent() {
        return this.mImplyContent;
    }
    public void setImplyContent(CDContentButton button) {
        this.mImplyContent = button;
    }
    
    public CDSideViewer(CD cd) {
        this.mCD = cd;
        this.mMode = Mode.HIERARCHY;
        this.mShiftAmount = 0;
        this.mLastHiearchyShift = 0;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if (this.mMode == Mode.HIERARCHY) {
            this.drawHierarchies(g2);
        } else {
            this.drawImplies(g2);
        }
        
    }
    
    public void drawHierarchies(Graphics2D g2) {
        ArrayList<CDHierarchyButton> buttons = 
            this.mCD.getButtonMgr().getHierarchyButtons();
        for (int i=0; i < buttons.size(); i++) {
            this.drawHierarchy(g2, buttons.get(i), i);
        }
    }
    private void drawHierarchy(Graphics2D g2, CDHierarchyButton button, 
        int index) {
        if (button.isHighlighted()) {
            g2.setColor(CDHierarchyButton.HIGHLIGHT_COLOR);
            g2.fillRect(0, CDHierarchyButton.HEIGHT * index - 
                this.mShiftAmount - CDHierarchyButton.GAP, 
                CD.INITIAL_HIERARCHY_WIDTH - CDHierarchyButton.SIDE_GAP, 
                CDHierarchyButton.HEIGHT - 2 * CDHierarchyButton.GAP);
        }
        g2.setColor(Color.black);
        g2.setFont(CDSideViewer.FONT);
        g2.drawString(button.getName(), CDSideViewer.GAP_LEFT, 
            CDHierarchyButton.HEIGHT * index - this.mShiftAmount + 
            CDSideViewer.GAP_UP);
    }
    
    public void drawImplies(Graphics2D g2) {
        ArrayList<CDImplyButton> buttons = this.mImplyContent.getImplyButtons();
        for (int i=0; i < buttons.size(); i++) {
            this.drawImply(g2, buttons.get(i), i);
        }
    }
    private void drawImply(Graphics2D g2, CDImplyButton button, 
        int index) {
        if (button.isHighlighted()) {
            g2.setColor(CDImplyButton.HIGHLIGHT_COLOR);
            g2.fillRect(0, CDImplyButton.HEIGHT * index - this.mShiftAmount - 
                CDImplyButton.GAP, CD.INITIAL_HIERARCHY_WIDTH - 
                CDImplyButton.SIDE_GAP, 
                CDImplyButton.HEIGHT - 2 * CDImplyButton.GAP);
        }
        g2.setColor(Color.black);
        g2.setFont(CDSideViewer.FONT);
        g2.drawString(button.getName(), CDSideViewer.GAP_LEFT, 
            CDHierarchyButton.HEIGHT * index - this.mShiftAmount + 
            CDSideViewer.GAP_UP);
    }
    
    public void shift(int amount) {
        int minShift = 0;
        int maxShift;
        if (this.mMode == Mode.HIERARCHY) {
            maxShift = Math.max(this.mCD.getButtonMgr().getHierarchyButtons().
                size() * CDHierarchyButton.HEIGHT, 0);
        } else {
            maxShift = Math.max(this.mImplyContent.getImplyButtons().size() * 
                CDImplyButton.HEIGHT, 0);
        }
        int afterShift = this.mShiftAmount + amount;
        if (afterShift >= minShift && afterShift <= maxShift) {
            this.mShiftAmount = afterShift;
        }
    }
}
