package cd.button;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CDContentButton extends CDPDFButton {
    public static final Color COLOR = new Color(0,0,255,64);
    public static final Color HIGHLIGHT_COLOR = new Color(0,0,0,64);
    
    private CDHierarchyButton mHierarchyButton = null;
    public void setHierarchyButton(CDHierarchyButton b) {
        this.mHierarchyButton = b;
    }
    public CDHierarchyButton getHierarchyButton() {
        return this.mHierarchyButton;
    }
    
    private ArrayList<CDImplyButton> mImplyButtons = null;
    public ArrayList<CDImplyButton> getImplyButtons() {
        return this.mImplyButtons;
    }
    public void addImplyButton(CDImplyButton button) {
        for (int i=0; i < this.mImplyButtons.size(); i++) {
            if (this.mImplyButtons.get(i).getContentPosition() > 
                button.getContentPosition()) {
                this.mImplyButtons.add(i, button);
                return;
            }
        }
        this.mImplyButtons.add(button);
    }
    
    public CDContentButton(String name, Rectangle box) {
        super(name, box);
        this.mKind = CDButton.Button.CONTENT;
        this.mImplyButtons = new ArrayList<>();
    }
    
}
