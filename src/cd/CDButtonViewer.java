package cd;

import java.awt.BasicStroke;
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
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.transform(this.mCD.getXform().getCurXformFromWorldToScreen());
        
        // render commom world objects
        
        g2.transform(this.mCD.getXform().getCurXformFromScreenToWorld());
        
        // render the current scene's world objects
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.renderWorldObjects(g2);
        
        //render common screen objects

        //render the current scene's screen objects
        curScene.renderScreenObjects(g2);
    }
    
}
