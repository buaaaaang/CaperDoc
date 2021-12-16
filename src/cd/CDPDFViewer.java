package cd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CDPDFViewer extends JPanel {
    // constants
    public static final int PAGE_WIDTH = 1680;
    private static final int PAGE_HEIGHT = 2376;
    private static final int PAGE_COL_GAP = 24;
    public static final int PAGE_COL_INTERVAL = 
        CDPDFViewer.PAGE_HEIGHT + CDPDFViewer.PAGE_COL_GAP;
    private static final int PAGE_ROW_GAP = 120;
    public static final int PAGE_ROW_INTERVAL = 
        CDPDFViewer.PAGE_WIDTH + CDPDFViewer.PAGE_ROW_GAP;
    
    
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
    
    private ArrayList<Integer> mYPoses = null;

    private final int worldXPos;
    public double getWorldXPos() {
        return this.worldXPos;
    }
    
    private int mNPages;
    
    public CDPDFViewer(CD cd, String path) throws IOException {
        this.mCD = cd;
        double initialScale = (double) CDPDFViewer.PAGE_HEIGHT / 
            cd.getInitialHeight();
        this.worldXPos = (int) ((CD.HIERARCHY_WIDTH + 50) * initialScale);
        try {
            System.out.println("Opening " + path + "...");
            System.out.println("\ndon't worry about red error message\n"
                + "-----------------------------");
            this.mDoc = PDDocument.load(new File(path));
            System.out.println("-----------------------------\n");
            System.out.println("Opening " + path + "...");
        } catch (IOException e) {
            System.out.println("Error: No such file in the path");
            return;
        }
        System.out.println("PDF Opened");
        this.mRenderer = new PDFRenderer(this.mDoc); 
        this.mYPoses = cd.getBranchYPoses();
        this.mNPages = this.mDoc.getNumberOfPages();
    }
    
    public Point getPDFOrigin(int i) {
        return new Point(this.worldXPos + 
            i * CDPDFViewer.PAGE_ROW_INTERVAL, this.mYPoses.get(i));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        this.setSize(this.mCD.getFrame().getWidth(), 
            this.mCD.getFrame().getHeight());
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Render PDF pages
        for (int i = 0; i < this.mYPoses.size(); i++) {
            Point p = this.getPDFOrigin(i);
            renderPDF(g2, p.x, p.y);
        }
        
    }    
    
    private void renderPDF(Graphics2D g2, int xPos, int yPos) {
        double topY = this.mCD.getXform().
            calcPtFromScreenToWorld(new Point(0,0)).y - yPos;
        double bottomY = this.mCD.getXform().
            calcPtFromScreenToWorld(new Point(0,this.getHeight())).y - yPos;
        
        int topPage = (int) (topY / CDPDFViewer.PAGE_COL_INTERVAL);
        int bottomPage = (int) (bottomY / CDPDFViewer.PAGE_COL_INTERVAL) + 1;
        topPage = Math.max(topPage, 0);
        bottomPage = Math.min(bottomPage, this.mDoc.getNumberOfPages());
        float ratio = this.getHeight() / ((float)(bottomY - topY));
        
        for (int p = topPage; p <= bottomPage; p++) {  
            PDRectangle pageFrame = this.mDoc.getPage(p).getCropBox();
            float xs = CDPDFViewer.PAGE_WIDTH * ratio / pageFrame.getWidth();
            float ys = CDPDFViewer.PAGE_HEIGHT * ratio / pageFrame.getHeight();
            float scale = Math.min(xs, ys);
            Point pos = this.mCD.getXform().calcPtFromWorldToScreen(new 
                Point2D.Double(xPos, p * CDPDFViewer.PAGE_COL_INTERVAL + yPos));  
            try {
                BufferedImage pageImage = this.mRenderer.renderImage(p, scale);
                g2.drawImage(pageImage, pos.x, pos.y, null);
            } catch (IOException e) {
                System.out.println("Error: cannot load page");
            }
        }     
    }
    
    public int onWhatBranch(Point pt) {
        Point2D.Double p = this.mCD.getXform().calcPtFromScreenToWorld(pt);
        double wx = p.x - this.worldXPos;
        for (int i = 0; i < this.mYPoses.size(); i++) {
            if (wx > i * CDPDFViewer.PAGE_ROW_INTERVAL && wx < i * 
                CDPDFViewer.PAGE_ROW_INTERVAL + CDPDFViewer.PAGE_WIDTH) {
                return i;
            }
        }
        return -1;
    }
    
    public int onWhatPage(Point pt) {
        CDXform xform = this.mCD.getXform();
        int branch = this.onWhatBranch(pt);
        Point2D.Double worldPt = xform.calcPtFromScreenToWorld(pt);;
        double pdfY = worldPt.y - this.mCD.getBranchYPoses().get(branch);
        return (int) Math.floor(pdfY / CDPDFViewer.PAGE_COL_INTERVAL);
    }
    
    public double[] getPageLocationFromPts(Point pt) {
        CDXform xform = this.mCD.getXform();
        int branch = this.onWhatBranch(pt);
        Point2D.Double worldPt = xform.calcPtFromScreenToWorld(pt);
        if (branch < 0) {
            return null;
        }
        double pdfX = worldPt.x - branch * CDPDFViewer.PAGE_ROW_INTERVAL;
        double pdfY = worldPt.y - this.mCD.getBranchYPoses().get(branch);
        if (pdfY < 0 || pdfY > this.mNPages * CDPDFViewer.PAGE_COL_INTERVAL) {
            return null;
        }
        int page = (int) Math.floor(pdfY / CDPDFViewer.PAGE_COL_INTERVAL);
        double xPos = (pdfX - this.worldXPos);
        double yPos = (pdfY - page * CDPDFViewer.PAGE_COL_INTERVAL);
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
