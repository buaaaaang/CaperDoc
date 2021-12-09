package cd;

import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class CDSideViewer extends JPanel {
    // constants
    private static final Font FONT = new Font("Monospaced", Font.PLAIN, 10);
    
    public enum Mode {
        HIERARCHY, IMPLY
    }
    
    // fields
    private CD mCD = null;
    
    private Mode mMode;
    public void setHierarchyMode() {
        this.mMode = Mode.HIERARCHY;
        this.mShiftedAmount = this.mLastHiearchyShift;
    }
    public void setImplyMode() {
        this.mMode = Mode.IMPLY;
        this.mLastHiearchyShift = this.mShiftedAmount;
        this.mShiftedAmount = 0;
    }
    
    private int mShiftedAmount;
    private int mLastHiearchyShift;
    
    private CDContentButton mImplyContent = null;
    public void setImplyContent(CDContentButton button) {
        this.mImplyContent = button;
    }
    
    public CDSideViewer(CD cd) {
        this.mCD = cd;
        this.mMode = Mode.HIERARCHY;
        this.mShiftedAmount = 0;
        this.mLastHiearchyShift = 0;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        this.setSize(this.mCD.getFrame().getWidth(), 
            this.mCD.getFrame().getHeight());
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
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
        int afterShift = this.mShiftedAmount + amount;
        if (afterShift >= minShift && afterShift <= maxShift) {
            this.mShiftedAmount = afterShift;
        }
    }
}
