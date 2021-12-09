package cd;

import cd.button.CDColorButton;
import java.awt.Color;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDButtonMgr {    
    private ArrayList<CDColorButton> mColorButtons = null;
    
    public ArrayList<CDColorButton> getColorButtons() {
        return this.mColorButtons;
    }
    public void addColorButton(CDColorButton button) {
        this.mColorButtons.add(button);
    }
    
    public CDButtonMgr(CD cd) {
        this.mColorButtons = new ArrayList<CDColorButton>();
        JPanel panel = cd.getButtonViewer();
        
        this.addColorButton(new CDColorButton(Color.black, 140, 15, 20, panel));
        this.addColorButton(new CDColorButton(Color.red, 110, 15, 20, panel));
        this.addColorButton(new CDColorButton(Color.blue, 80, 15, 20, panel));
        this.addColorButton(new CDColorButton(new Color(255, 200, 0, 128), 
            50, 15, 20, panel));
    }
}
