package cd;

import cd.button.CDColorButton;
import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CDButtonViewer extends JPanel {
    // fields and constants
    private CD mCD = null;
    
    public CDButtonViewer(CD cd) {
        this.mCD = cd;
        
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        this.setSize(this.mCD.getFrame().getWidth(), 
            this.mCD.getFrame().getHeight());
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.transform(this.mCD.getXform().getCurXformFromWorldToScreen());
        // render world objects      
        
        // render the current scene's world objects
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.renderWorldObjects(g2);        
        
        g2.transform(this.mCD.getXform().getCurXformFromScreenToWorld());   
        //render screen objects
        this.drawColorButtons(g2); 

        
        //render the current scene's screen objects
        curScene.renderScreenObjects(g2);
    }
    
    private void drawColorButtons(Graphics2D g2) {
        if (this.mCD.getButtonMgr().getColorButtons() == null) {
            return;
        }
        this.mCD.getButtonMgr().getColorButtons().forEach(colorButton -> {
            this.drawColorButton(g2, colorButton);
        });
    }
    
    private void drawColorButton(Graphics2D g2, CDColorButton colorButton) {
        if (colorButton.isHighlighted()) {
            g2.setColor(CDColorButton.HIGHLIGHT_COLOR);
            g2.fillOval(this.getWidth() + (int) ((colorButton.getRadius() -
                colorButton.getHighLightRadius()) * 0.5) -
                colorButton.getScreenPositionFromRight(),
                colorButton.getScreenPositionFromTop() +
                (int) ((colorButton.getRadius() -
                colorButton.getHighLightRadius()) * 0.5), 
                colorButton.getHighLightRadius(), 
                colorButton.getHighLightRadius());
        }
        g2.setColor(colorButton.getColor());
        g2.fillOval(this.getWidth() - colorButton.getScreenPositionFromRight(),
            colorButton.getScreenPositionFromTop(),
            colorButton.getRadius(), colorButton.getRadius());        
    }
    
}
