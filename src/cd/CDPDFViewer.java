package cd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    
    
    // fields
    private CD mCD = null;
    private PDDocument mDoc = null;
    public PDDocument getDocument() {
        return this.mDoc;
    }
  
    private PDFRenderer mRenderer = null;
    public PDFRenderer getRenderer() {
        return this.mRenderer;
    }

    final double worldXPos;
    
    public CDPDFViewer(CD cd, String path) throws IOException {
        this.mCD = cd;
        System.out.println("" + cd.getInitialHeight());
        double initialScale = (double) CDPDFViewer.PAGE_HEIGHT / 
            cd.getInitialHeight();
        this.worldXPos = (CD.INITIAL_HIERARCHY_WIDTH + 50) * initialScale;
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
        this.setSize(this.mCD.getFrame().getWidth(), 
            this.mCD.getFrame().getHeight());
        
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
                new Point2D.Double(this.worldXPos, 
                p * CDPDFViewer.PAGE_INTERVAL)));  
            try {
                BufferedImage pageImage = this.mRenderer.renderImage(p, scale);
//                File outputfile = new File("image.jpg");
//                ImageIO.write(pageImage, "jpg", outputfile);
                g2.drawImage(pageImage, pos.x, pos.y, null);
            } catch (IOException e) {
                System.out.println("Error: cannot load page");
            }
        }     
    }    
    
    public double[] getPageLocationFromPts(Point pt) {
        CDXform xform = this.mCD.getXform();
        Point2D.Double worldPt = xform.calcPtFromScreenToWorld(pt);
        int page = (int) Math.floor(worldPt.y / CDPDFViewer.PAGE_INTERVAL);
        double xPos = (worldPt.x - this.worldXPos);
        double yPos = (worldPt.y - page * 2400);
        PDRectangle pageFrame = this.mDoc.getPage(page).getCropBox();
        float xs = CDPDFViewer.PAGE_WIDTH / pageFrame.getWidth();
        float ys = CDPDFViewer.PAGE_HEIGHT / pageFrame.getHeight();
        if (xs < ys) {
            double[] pos = { page, xPos / xs, yPos / xs };
            return pos;
        } else {
            double[] pos = { page, xPos / ys, yPos / ys };
            return pos;
        }
    }
}
