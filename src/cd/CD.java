package cd;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class CD extends XApp {
    // constants
    private static final int INITIAL_WIDTH = 800;
    private static final int INITIAL_HEIGHT = 600;
    
    // fields
    private JFrame mFrame = null;
    private CDPDFViewer mViewer = null;
    
    @Override
    public XScenarioMgr getScenarioMgr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public XLogMgr getXLogMgr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // constructor
    public CD() throws IOException {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.mViewer = new CDPDFViewer(this);
        
        
        // build and show
        this.mFrame.add(mViewer);
        this.mViewer.setPreferredSize(
            new Dimension(INITIAL_WIDTH,INITIAL_HEIGHT));
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }
    
    // main
    public static void main(String[] args) throws IOException {
        CD myCaperDoc = new CD();
    }
    
}
