package cd;

import cd.button.CDColorButton;
import cd.button.CDContentButton;
import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
        this.drawContentButtons(g2);
        
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
    
    private void drawColorButton(Graphics2D g2, CDColorButton button) {
        if (button.isHighlighted()) {
            g2.setColor(CDColorButton.HIGHLIGHT_COLOR);
            g2.fillOval(this.getWidth() + (int) ((button.getRadius() -
                button.getHighLightRadius()) * 0.5) -
                button.getScreenPositionFromRight(),
                button.getScreenPositionFromTop() +
                (int) ((button.getRadius() -
                button.getHighLightRadius()) * 0.5), 
                button.getHighLightRadius(), 
                button.getHighLightRadius());
        }
        g2.setColor(button.getColor());
        g2.fillOval(this.getWidth() - button.getScreenPositionFromRight(),
            button.getScreenPositionFromTop(),
            button.getRadius(), button.getRadius());        
    }
    
    private void drawContentButtons(Graphics2D g2) {
        if (this.mCD.getButtonMgr().getContentButtons() == null) {
            return;
        }
        this.mCD.getButtonMgr().getContentButtons().forEach(button -> {
            this.drawContentButton(g2, button);
        });
    }
    
    private void drawContentButton(Graphics2D g2, CDContentButton button) {
        Rectangle box = button.getBox().getBounds();
        int width = box.width;
        int height = box.height;
        int xPos = box.x;
        int yPos = box.y;
        if (button.isHighlighted()) {
            g2.setColor(CDContentButton.HIGHLIGHT_COLOR);
            g2.fillRect(xPos - (int) (0.15 * width), yPos - (int) (0.15 * 
                height), (int) (WIDTH * 1.3), (int) (HEIGHT * 1.3));
        }
        g2.setColor(CDContentButton.COLOR);
        g2.fillRect(xPos, yPos, width, height);
    }
    
}
