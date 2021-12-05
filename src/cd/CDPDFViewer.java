package cd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CDPDFViewer extends JPanel {
    // constants
    // about pdf rendering
    private static final int PAGE_WIDTH = 1680;
    private static final int PAGE_HEIGHT = 2376;
    private static final int PAGE_GAP = 24;
    private static final int PAGE_INTERVAL = 
        CDPDFViewer.PAGE_HEIGHT + CDPDFViewer.PAGE_GAP;
    private static final double WORLD_X_POS = 0.5 / CD.INITIAL_DIALATION *
        (CD.INITIAL_WIDTH - CDPDFViewer.PAGE_WIDTH * CD.INITIAL_DIALATION);
    
    // about topleft info
    private static final Color COLOR_INFO = new Color(255,0,0,128);
    private static final Font FONT_INFO = 
        new Font("Monospaced", Font.PLAIN, 24);
    private static final int INFO_TOP_ALIGNMENT_X = 100;
    private static final int INFO_TOP_ALIGNMENT_Y = 30;
    
    
    // fields
    private CD mCD = null;
    private PDDocument mDoc = null;
    private PDFRenderer mRenderer = null;
    
    
    public CDPDFViewer(CD cd, String path) throws IOException {
        this.mCD = cd;
        try {
            System.out.println("Opening " + path + "...");
            this.mDoc = PDDocument.load(new File(path));
        } catch (IOException e) {
            System.out.println("Error: No such file in the path");
            return;
        }
        System.out.println("PDF Opened");
        this.mRenderer = new PDFRenderer(this.mDoc); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Render PDF pages
        Point2D.Double topPoint = this.mCD.getXform().
            calcPtFromScreenToWorld(new Point(0,0));
        Point2D.Double bottomPoint = this.mCD.getXform().
            calcPtFromScreenToWorld(new Point(0,this.getHeight()));
        
        int topPage = (int) (topPoint.y / CDPDFViewer.PAGE_INTERVAL);
        int bottomPage = (int) (bottomPoint.y / CDPDFViewer.PAGE_INTERVAL) + 1;
        topPage = Math.max(topPage, 0);
        bottomPage = Math.min(bottomPage, this.mDoc.getNumberOfPages());
        float ratio = this.getHeight() / ((float)(bottomPoint.y - topPoint.y));
        
        for (int p = topPage; p <= bottomPage; p++) {  
            PDRectangle pageFrame = this.mDoc.getPage(p).getCropBox();
            float xs = CDPDFViewer.PAGE_WIDTH * ratio / pageFrame.getWidth();
            float ys = CDPDFViewer.PAGE_HEIGHT * ratio / pageFrame.getHeight();
            float scale = Math.min(xs, ys);
            Point pos = (this.mCD.getXform().calcPtFromWorldToScreen(
                new Point2D.Double(CDPDFViewer.WORLD_X_POS, 
                p * CDPDFViewer.PAGE_INTERVAL)));  
            try {
                BufferedImage pageImage = this.mRenderer.renderImage(p, scale);
                g2.drawImage(pageImage, pos.x, pos.y, null);
            } catch (IOException e) {
                System.out.println("Error: cannot load page");
            }
        }     
        
        // render common screen objects
//        this.drawInfo(g2);
    }    
    
//    private void drawInfo(Graphics2D g2) {
//        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
//        String str = curScene.getClass().getSimpleName();
//        g2.setColor(CDPDFViewer.COLOR_INFO);
//        g2.setFont(CDPDFViewer.FONT_INFO);
//        g2.drawString(str, CDPDFViewer.INFO_TOP_ALIGNMENT_X, 
//            CDPDFViewer.INFO_TOP_ALIGNMENT_Y);   
//    }
}
