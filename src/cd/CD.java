package cd;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class CD extends XApp {
    // constants
    public static final int INITIAL_WIDTH = 1700;
    private static final int INITIAL_HEIGHT = 1000;
    public static final double INITIAL_DIALATION = 0.4;
    
    // fields
    private JFrame mFrame = null;
    
    private CDPDFViewer mViewer = null;
    public CDPDFViewer getViewer() {
        return this.mViewer;
    }
    
    private CDXform mXform = null;
    public CDXform getXform() {
        return this.mXform;
    }
    
    private CDEventListener mEventListener = null;
    
    private XScenarioMgr mScenarioMgr = null;
    @Override
    public XScenarioMgr getScenarioMgr() {
        return this.mScenarioMgr;
    }
    
    private XLogMgr mLogMgr = null;
    @Override
    public XLogMgr getXLogMgr() {
        return this.mLogMgr;
    }
    
    // constructor
    public CD() throws IOException {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(CD.INITIAL_WIDTH, CD.INITIAL_HEIGHT);
        this.mViewer = new CDPDFViewer(this);
        this.mXform = new CDXform();
        this.mXform.scale(CD.INITIAL_DIALATION);
        this.mEventListener = new CDEventListener(this);
        
        this.mScenarioMgr = new CDScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);
        
        // connect to event listners
        this.mViewer.addMouseListener(this.mEventListener);
        this.mViewer.addMouseMotionListener(this.mEventListener);
        this.mViewer.addMouseWheelListener(this.mEventListener);
        this.mViewer.addKeyListener(this.mEventListener);    
        this.mViewer.setFocusable(true);
        
        
        // build and show
        this.mFrame.add(mViewer);
        this.mViewer.setPreferredSize(
            new Dimension(CD.INITIAL_WIDTH,CD.INITIAL_HEIGHT));
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }
    
    // main
    public static void main(String[] args) throws IOException {
        CD myCaperDoc = new CD();
    }
    
}
