package cd;

import cd.button.CDButton;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import cd.button.CDSideButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDSideViewer extends JPanel {
    
    public enum Mode {
        HIERARCHY, IMPLY
    }
    
    // fields
    private CD mCD = null;
    
    private Mode mMode;
    public Mode getMode() {
        return this.mMode;
    }
    public void setHierarchyMode() {
        this.mMode = Mode.HIERARCHY;
        this.mShiftAmount = this.mLastHiearchyShift;
    }
    public void setImplyMode(CDContentButton button) {
        this.mMode = Mode.IMPLY;
        this.mLastHiearchyShift = this.mShiftAmount;
        this.mShiftAmount = 0;
        this.mImplyContent = button;
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
            g2.fillRect(CDSideButton.GAP_SIDE, CDSideButton.HEIGHT * index - 
                this.mShiftAmount, CD.HIERARCHY_WIDTH - 2 * 
                CDSideButton.GAP_SIDE, CDSideButton.HEIGHT);
        }
        g2.setColor(Color.black);
        g2.setFont(CDSideButton.FONT);
        g2.drawString(button.getName(), CDSideButton .GAP_SIDE_TEXT, 
            CDSideButton.HEIGHT * index - this.mShiftAmount+ 
            CDSideButton.GAP_UP_TEXT);
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
            g2.fillRect(CDSideButton.GAP_SIDE, CDImplyButton.HEIGHT * index - 
                this.mShiftAmount, CD.HIERARCHY_WIDTH - 2 *
                CDSideButton.GAP_SIDE, CDSideButton.HEIGHT);
        }
        g2.setColor(Color.black);
        g2.setFont(CDSideButton.FONT);
        g2.drawString(button.getName(), CDSideButton.GAP_SIDE_TEXT, 
            CDSideButton.HEIGHT * index - this.mShiftAmount + 
            CDSideButton.GAP_UP_TEXT);
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
