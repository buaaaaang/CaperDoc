package cd;

import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDRemainderButton;
import cd.button.CDLinkButton;
import cd.button.CDImplyButton;
import cd.button.CDPDFButton;
import cd.button.CDSideButton;
import cd.button.CDSideRemainderButton;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CDButtonMgr {    
    private CD mCD;
    
    private ArrayList<CDColorButton> mColorButtons = null;
    public ArrayList<CDColorButton> getColorButtons() {
        return this.mColorButtons;
    }
    private void addColorButton(CDColorButton button) {
        this.mColorButtons.add(button);
    }
    
    private ArrayList<CDContentButton> mContentButtons = null;
    public ArrayList<CDContentButton> getContentButtons() {
        return this.mContentButtons;
    }
    public void addContentButton(CDContentButton button) {
        this.mContentButtons.add(button);
    }
    
    private ArrayList<CDLinkButton> mLinkButtons = null;
    public ArrayList<CDLinkButton> getLinkButtons() {
        return this.mLinkButtons;
    }
    public void addLinkButton(CDLinkButton button) {
        this.mLinkButtons.add(button);
    }

    private ArrayList<CDHierarchyButton> mHierarchyButtons = null;
    public ArrayList<CDHierarchyButton> getHierarchyButtons() {
        return this.mHierarchyButtons;
    }
    public void addHierarchyButton(CDHierarchyButton button) {
        for (int i=0; i < this.mHierarchyButtons.size(); i++) {
            if (this.mHierarchyButtons.get(i).getContentPosition() > 
                button.getContentPosition()) {
                this.mHierarchyButtons.add(i, button);
                return;
            }
        }
        this.mHierarchyButtons.add(button);
    }
    
    private CDColorButton mCurMouseColorButton = null;
    public CDColorButton getCurMouseColorButton() {
        return this.mCurMouseColorButton;
    }
    
    private CDContentButton mCurMouseContentButton = null;
    public CDContentButton getCurMouseContentButton() {
        return this.mCurMouseContentButton;
    }
    
    private CDLinkButton mCurMouseLinkButton = null;
    public CDLinkButton getCurMouseLinkButton() {
        return this.mCurMouseLinkButton;
    }
    
    private CDHierarchyButton mCurMouseHierarchyButton = null;
    public CDHierarchyButton getCurMouseHierarchyButton() {
        return this.mCurMouseHierarchyButton;
    }
    
    private CDImplyButton mCurMouseImplyButton = null;
    public CDImplyButton getCurMouseImplyButton() {
        return this.mCurMouseImplyButton;
    }
    
    
    public CDButtonMgr(CD cd) {
        this.mCD = cd;
        
        this.mColorButtons = new ArrayList<>();
        this.addColorButton(new CDColorButton(Color.black, 140, 15, 20));
        this.addColorButton(new CDColorButton(Color.red, 110, 15, 20));
        this.addColorButton(new CDColorButton(Color.blue, 80, 15, 20));
        this.addColorButton(new CDColorButton(new Color(255, 200, 0, 128), 
            50, 15, 20));
        this.mColorButtons.get(0).setHighlight(true);
        
        this.mContentButtons = new ArrayList<>();
        this.mLinkButtons = new ArrayList<>();
        this.mHierarchyButtons = new ArrayList<>();
    }    
    
    private boolean contains(CDColorButton button, Point pt) {
        int dx = this.mCD.getPanel().getWidth() - 
            button.getScreenPositionFromRight() + button.getRadius() - pt.x;
        int dy = button.getScreenPositionFromTop() + button.getRadius() - pt.y;
        return ((dx*dx + dy*dy) < button.getRadius() * button.getRadius());
    }
    
    public boolean contains(CDPDFButton button, Point pt) {
        boolean b = false;
        Rectangle box = button.getBox();
        Point2D.Double p = this.mCD.getXform().calcPtFromScreenToWorld(pt);
        for (int i=0; i < this.mCD.getBranchYPoses().size(); i++) {
            Point o = this.mCD.getPDFViewer().getPDFOrigin(i);
            Rectangle newBox = new Rectangle(box.x + o.x, box.y + o.y, 
                box.width, box.height);
            b = b || newBox.contains(p);
        }
        return b;
    }
    
    private boolean contains(CDImplyButton button, Point pt) {
        if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.HIERARCHY) {
            return false;
        }
        int n = this.mCD.getSideViewer().getImplyContent().getImplyButtons().
            indexOf(button);
        int shift = this.mCD.getSideViewer().getShiftAmount();
        boolean b1 = pt.y > (CDSideButton.HEIGHT * n - shift) &&
            pt.y < (CDSideButton.HEIGHT * (n + 1) - shift);
        boolean b2 = pt.x < CD.HIERARCHY_WIDTH - CDSideButton.GAP_SIDE;
        return b1 && b2;
    }
    
    private boolean contains(CDHierarchyButton button, Point pt) {
        if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.IMPLY) {
            return false;
        }
        int n = this.mCD.getButtonMgr().getHierarchyButtons().indexOf(button);
        int shift = this.mCD.getSideViewer().getShiftAmount();
        boolean b1 = pt.y > (CDHierarchyButton.HEIGHT * n - shift) &&
            pt.y < (CDHierarchyButton.HEIGHT * (n + 1) - shift);
        boolean b2 = pt.x < CD.HIERARCHY_WIDTH - CDSideButton.GAP_SIDE;
        return b1 && b2;
    }
    
    public CDButton checkButton(Point pt) {
        for (CDColorButton button: this.mColorButtons) {
            if (this.contains(button, pt)) {
                this.mCurMouseColorButton = button;
                return button;
            }
        }
        if (pt.x < CD.HIERARCHY_WIDTH) { 
            if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.IMPLY) {
                for (CDImplyButton button: this.mCD.getSideViewer().
                    getImplyContent().getImplyButtons()) {
                    if (this.contains(button, pt)) {
                        this.mCurMouseImplyButton = button;
                        return button;
                    }
                }   
            } else {
                for (CDHierarchyButton button: this.mHierarchyButtons) {
                    if (this.contains(button, pt)) {
                        this.mCurMouseHierarchyButton = button;
                        return button;
                    }
                }   
            }
            return new CDSideRemainderButton();
        } else {
            for (CDLinkButton button: this.mLinkButtons) {
                if (this.contains(button, pt)) {
                    this.mCurMouseLinkButton = button;
                    return button;
                }
            }   
            for (CDContentButton button: this.mContentButtons) {
                if (this.contains(button, pt)) {
                    this.mCurMouseContentButton = button;
                    return button;
                }
            }  
            return new CDRemainderButton();  
        }
    }
}
