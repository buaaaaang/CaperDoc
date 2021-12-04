package cd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CDPDFViewer extends JPanel {
    // constants
    private static final float RATIO_PDF_WIDTH = 0.98f;
    private static final float RATIO_PDF_LOCATION = 0.01f;
    private static final float RATIO_PAGE_GAP = 0.01f;
    
    // fields
    private CD mCD = null;
    private PDDocument mDoc = null;
    private PDFRenderer mRenderer = null;
    
    private int mPage;
    private float mPageLocation;
    
    
    public CDPDFViewer(CD cd) throws IOException {
        this.mCD = cd;
        try {
            this.mDoc = PDDocument.load(new File("real analysis.pdf"));
        } catch (IOException e) { }
        this.mRenderer = new PDFRenderer(this.mDoc); 
        
        this.mPage = 30;
        this.mPageLocation = 0.7f;
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Render PDF pages
        PDPage page = this.mDoc.getPage(this.mPage);
        PDRectangle singlePage = page.getCropBox();
        float pageWidth = singlePage.getWidth();
        float pageHeight = singlePage.getHeight();
        float scale = this.getWidth() * CDPDFViewer.RATIO_PDF_WIDTH / pageWidth;
        int nPages = (int) (this.getHeight() / (pageHeight * scale)) + 2;
        
        int xPos = (int) ((this.getWidth() * CDPDFViewer.RATIO_PDF_LOCATION));
        int yPos = -1 * ((int) (pageHeight * scale * this.mPageLocation));
    
        for (int i = 0; i < nPages; i++) {
            try {
                BufferedImage pageImage = 
                    this.mRenderer.renderImage(this.mPage + i, scale);
                g2.drawImage(pageImage, xPos, yPos, null);
                yPos += pageHeight * scale * (1 + CDPDFViewer.RATIO_PAGE_GAP);
            } catch (IOException e) { }
        }
    }
    
    public void Move(float f) {
        this.mPageLocation += f;
        if (mPageLocation < 0 || mPageLocation >= 1) {
            mPage += Math.floor(mPageLocation);
            this.mPageLocation = this.mPageLocation - 
                (float) Math.floor(this.mPageLocation);
        }
    }
    
}
