package cd;

import cd.button.CDColorButton;
import cd.button.CDContentButton;
import java.awt.Color;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDButtonMgr {
    private ArrayList<CDContentButton> mContents = null;
    public ArrayList<CDContentButton> getContents() {
        return this.mContents;
    }
    public void addContent(CDContentButton button) {
        this.mContents.add(button);
    }
    
    private ArrayList<CDColorButton> mColorButtons = null;
    
    public ArrayList<CDColorButton> getColorButtons() {
        return this.mColorButtons;
    }
    public void addColorButton(CDColorButton button) {
        this.mColorButtons.add(button);
    }
    
    public CDButtonMgr(CD cd) {
        this.mContents = new ArrayList<CDContentButton>();
        this.mColorButtons = new ArrayList<CDColorButton>();
        Point dummyPos = new Point(0, 0);
        JPanel panel = cd.getButtonViewer();
        CDColorButton bt1 = new CDColorButton(Color.red, 1, 1, 100, dummyPos, panel);
        bt1.setHighlight(true);
        this.addColorButton(bt1);
    }
}
