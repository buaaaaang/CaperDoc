package cd.button;

import cd.CD;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CDContentButton extends CDWorldButton {
    public static final Color COLOR = new Color(0,0,255,64);
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    
    private Shape mBox = null;
    public Shape getBox() {
        return this.mBox;
    }
    
    private CDHierarchyButton mHierarchyButton = null;
    public void setHierarchyButton(CDHierarchyButton b) {
        this.mHierarchyButton = b;
    }
    public CDHierarchyButton getNeedButton() {
        return this.mHierarchyButton;
    }
    
    private ArrayList<CDNeedButton> mNeedButtons = null;
    public ArrayList<CDNeedButton> getNeedButtons() {
        return this.mNeedButtons;
    }
    public void addNeedButton(CDNeedButton button) {
        this.mNeedButtons.add(button);
    }
    
    private ArrayList<CDImplyButton> mImplyButtons = null;
    public ArrayList<CDImplyButton> getImplyButtons() {
        return this.mImplyButtons;
    }
    public void addImplyButton(CDImplyButton button) {
        this.mImplyButtons.add(button);
    }
    
    private CD mCD = null;
    
    public CDContentButton(String name, Shape rec, CD cd) {
        super(name, new Point2D.Double(rec.getBounds().x, rec.getBounds().y));
        this.mKind = CDButton.Button.CONTENT;
        this.mNeedButtons = new ArrayList<>();
        this.mImplyButtons = new ArrayList<>();
        this.mCD = cd;
        this.mBox = rec;
    }

    @Override
    public boolean contains(Point pt) {
        return this.mBox.contains(this.mCD.getXform().
            calcPtFromScreenToWorld(pt));
    }
    
}
