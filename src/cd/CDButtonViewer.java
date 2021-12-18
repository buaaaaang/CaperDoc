package cd;

import cd.button.CDButton;
import cd.button.CDColorButton;
import cd.button.CDContentButton;
import cd.button.CDLinkButton;
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
        this.drawLinkButtons(g2);  
        
        // render the current scene's world objects
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.renderWorldObjects(g2);  
        
        g2.transform(this.mCD.getXform().getCurXformFromScreenToWorld());   
        //render screen objects
        this.drawColorButtons(g2);    

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
    
    private void drawLinkButtons(Graphics2D g2) {
        if (this.mCD.getButtonMgr().getLinkButtons() == null) {
            return;
        }
        for (int i=0; i < this.mCD.getBranchYPoses().size(); i++) {
            Point p = this.mCD.getPDFViewer().getPDFOrigin(i);
            this.mCD.getButtonMgr().getLinkButtons().forEach(button -> {
                this.drawLinkButton(g2, button, p.x, p.y);
            });
        }
    }
    
    private void drawLinkButton(Graphics2D g2, CDLinkButton button, 
        int xPos, int yPos) {
        Point pos = new Point((int) (button.getBox().x + xPos), 
            button.getBox().y + yPos);
        g2.setFont(CDLinkButton.FONT);
        if (button.getBox().width == 0) {
            button.setBox(2 * CDLinkButton.GAP_SIDE + g2.getFontMetrics().
                stringWidth(button.getName()));
        }
        if (button.isHighlighted()) {
            g2.setColor(CDLinkButton.HIGHLIGHT_COLOR);
            g2.fillRect(pos.x, pos.y, button.getBox().width, 
                CDLinkButton.HEIGHT);
        }
        g2.setColor(CDLinkButton.COLOR);
        g2.fillRect(pos.x, pos.y, button.getBox().width, CDLinkButton.HEIGHT);
        g2.setColor(Color.black);
        g2.drawString(button.getName(), CDLinkButton.GAP_SIDE + pos.x, 
            pos.y + CDLinkButton.GAP_UP_TEXT);
    }
    
}
