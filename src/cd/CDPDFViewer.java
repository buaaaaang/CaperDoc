package cd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.RenderDestination;

public class CDPDFViewer extends JPanel {
    // constants
    private static final float WIDTH_PDF_RATIO = 0.95f;
    private static final float PDF_LOCATION_RATIO = 0.025f;
    private static final int PDF_PAGE_GAP = 10;
    
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
        float scale = this.getWidth() * CDPDFViewer.WIDTH_PDF_RATIO / pageWidth;
        int nPages = (int) (this.getHeight() / (pageHeight * scale)) + 2;
        
        int xPos = (int) ((this.getWidth() * CDPDFViewer.PDF_LOCATION_RATIO));
        int yPos = -1 * ((int) (pageHeight * scale * this.mPageLocation));
    
        for (int i = 0; i < nPages; i++) {
            try {
                BufferedImage pageImage = 
                    this.mRenderer.renderImage(this.mPage + i, scale);
                g2.drawImage(pageImage, xPos, yPos, null);
                yPos += pageHeight * scale + CDPDFViewer.PDF_PAGE_GAP;
            } catch (IOException e) { }
        }
    }
    
    
}
