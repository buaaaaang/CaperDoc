package cd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CDPDFViewer extends JPanel {
    // fields
    private CD mCD = null;
    private PDDocument mDoc = null;
    private PDFRenderer mRenderer = null;
    
    public CDPDFViewer(CD cd) throws IOException {
        this.mCD = cd;
        try {
            this.mDoc = PDDocument.load(new File("real analysis.pdf"));
        } catch (IOException e) {
            Logger.getLogger(CDPDFViewer.class.getName()).
                log(Level.SEVERE, null, e);
            return;
        }
        this.mRenderer = new PDFRenderer(this.mDoc);        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        PDPage page = this.mDoc.getPage(0);
        PDRectangle cropBox = page.getCropBox();
        float imgWidth = cropBox.getWidth();
        float imgHeight = cropBox.getHeight();
        float widthRatio = this.getWidth() / imgWidth;
        float heightRatio = this.getHeight() / imgHeight;
        float scale;
        if (widthRatio > heightRatio) {
            g2.translate(
                (int) ((this.getWidth() - imgWidth * heightRatio) / 2), 0);
            scale = heightRatio;
        } else {
            g2.translate(
                0, (int) ((this.getHeight() - imgHeight * widthRatio) / 2));
            scale = widthRatio;
        }
        try {
            this.mRenderer.renderPageToGraphics(0, g2, scale);
        } catch (IOException e) {
            Logger.getLogger(CDPDFViewer.class.getName()).
                log(Level.SEVERE, null, e);
        }
    }
    
    
}
