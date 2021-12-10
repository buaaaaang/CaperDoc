package cd;

import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class CDButtonViewer extends JPanel {
    // fields and constants
    private CD mCD = null;

    private CDButton mDummyHierarchy = null;
    private Point mDummyHierarchyPos = null;
    public void setDummyButton(CDButton button, Point pos) {
        this.mDummyHierarchy = button;
        this.mDummyHierarchyPos = pos;
    }
    
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
        this.drawDummyHierarchy(g2);
        this.mDummyHierarchyPos = null;

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
            System.out.println("dfdf");
            g2.setColor(CDContentButton.HIGHLIGHT_COLOR);
            g2.fillRect(xPos, yPos, width, height);
        }
        g2.setColor(CDContentButton.COLOR);
        g2.fillRect(xPos, yPos, width, height);
    }
    
    private void drawDummyHierarchy(Graphics2D g2) {
        if (this.mDummyHierarchyPos == null) {
            return;
        }
        int xPos = this.mDummyHierarchyPos.x - (int) (CD.HIERARCHY_WIDTH * 0.5);
        int yPos = this.mDummyHierarchyPos.y - 
            (int) (CDHierarchyButton.HEIGHT * 0.5);
        g2.setColor(CDHierarchyButton.HIGHLIGHT_COLOR);
        g2.fillRect(xPos, yPos, CD.HIERARCHY_WIDTH, CDHierarchyButton.HEIGHT);
        g2.setColor(Color.black);
        g2.setFont(CDSideViewer.FONT);
        g2.drawString(this.mDummyHierarchy.getName(), CDSideViewer.GAP_LEFT +
            xPos, yPos + CDSideViewer.GAP_UP);
    }
    
}
