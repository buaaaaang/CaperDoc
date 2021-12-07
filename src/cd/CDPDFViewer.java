package cd;

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
    private static final int PAGE_WIDTH = 1680;
    private static final int PAGE_HEIGHT = 2376;
    private static final int PAGE_GAP = 24;
    public static final int PAGE_INTERVAL = 
        CDPDFViewer.PAGE_HEIGHT + CDPDFViewer.PAGE_GAP;
    private static final double WORLD_X_POS = 0.5 / CDXform.INITIAL_DIALATION *
        (CD.INITIAL_PANEL_WIDTH - CDPDFViewer.PAGE_WIDTH * CDXform.INITIAL_DIALATION);
    
    
    // fields
    private CD mCD = null;
    private PDDocument mDoc = null;
    private PDFRenderer mRenderer = null;
    
    
    public CDPDFViewer(CD cd, String path) throws IOException {
        this.mCD = cd;
        try {
            System.out.println("Opening " + path + "...");
            System.out.println("\ndon't worry about red error message\n"
                + "-----------------------------");
            this.mDoc = PDDocument.load(new File(path));
            // 조교님한테 이부분 물어보기
            System.out.println("-----------------------------\n");
            System.out.println("Opening " + path + "...");
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
    }    
}
