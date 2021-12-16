package cd;

import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.button.CDContentButton;
import cd.button.CDNeedButton;
import cd.button.CDSideButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
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
        this.drawNeedButtons(g2);  
        
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
        for (int i=0; i < this.mCD.getBranchYPoses().size(); i++) {
            Point p = this.mCD.getPDFViewer().getPDFOrigin(i);
            this.mCD.getButtonMgr().getContentButtons().forEach(button -> {
                this.drawContentButton(g2, button, p.x, p.y);
            });
        }
    }
    
    private void drawContentButton(Graphics2D g2, CDContentButton button,
        int xPos, int yPos) {
        Rectangle box = button.getBox();
        int width = box.width;
        int height = box.height;
        Point p = new Point(box.x + xPos, box.y + yPos);
        if (button.isHighlighted()) {
            g2.setColor(CDContentButton.HIGHLIGHT_COLOR);
            g2.fillRect(p.x, p.y, width, height);
        }
        g2.setColor(CDContentButton.COLOR);
        g2.fillRect(p.x, p.y, width, height);
    }
    
    private void drawNeedButtons(Graphics2D g2) {
        if (this.mCD.getButtonMgr().getNeedButtons() == null) {
            return;
        }
        for (int i=0; i < this.mCD.getBranchYPoses().size(); i++) {
            Point p = this.mCD.getPDFViewer().getPDFOrigin(i);
            this.mCD.getButtonMgr().getNeedButtons().forEach(button -> {
                this.drawNeedButton(g2, button, p.x, p.y);
            });
        }
    }
    
    private void drawNeedButton(Graphics2D g2, CDNeedButton button, 
        int xPos, int yPos) {
        Point pos = new Point((int) (button.getBox().x + xPos), 
            button.getBox().y + yPos);
        g2.setFont(CDNeedButton.FONT);
        if (button.getBox().width == 0) {
            button.setBox(2 * CDNeedButton.GAP_SIDE + g2.getFontMetrics().
                stringWidth(button.getName()));
        }
        if (button.isHighlighted()) {
            g2.setColor(CDNeedButton.HIGHLIGHT_COLOR);
            g2.fillRect(pos.x, pos.y, button.getBox().width, 
                CDNeedButton.HEIGHT);
        }
        g2.setColor(CDNeedButton.COLOR);
        g2.fillRect(pos.x, pos.y, button.getBox().width, CDNeedButton.HEIGHT);
        g2.setColor(Color.black);
        g2.drawString(button.getName(), CDNeedButton.GAP_SIDE + pos.x, 
            pos.y + CDNeedButton.GAP_UP_TEXT);
    }
    
    private void drawDummyHierarchy(Graphics2D g2) {
        if (this.mDummyHierarchyPos == null) {
            return;
        }
        g2.setFont(CDSideButton.FONT);
        int width = 2 * CDSideButton.GAP_SIDE + g2.getFontMetrics().
            stringWidth(this.mDummyHierarchy.getName());
        int xPos = this.mDummyHierarchyPos.x - (int) (width * 0.5);
        int yPos = this.mDummyHierarchyPos.y - 
            (int) (CDSideButton.HEIGHT * 0.5);
        g2.setColor(CDSideButton.HIGHLIGHT_COLOR);
        g2.fillRect(xPos, yPos, width, CDSideButton.HEIGHT);
        g2.setColor(Color.black);
        g2.drawString(this.mDummyHierarchy.getName(), CDSideButton.GAP_SIDE +
            xPos, yPos + CDSideButton.GAP_UP_TEXT);
    }
    
}
