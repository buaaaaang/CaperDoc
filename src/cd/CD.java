package cd;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    private CDPtCurveMgr mPtCurveMgr = null;
    public CDPtCurveMgr getPtCurveMgr() {
        return this.mPtCurveMgr;
    }
    
    private CDPDFViewer mViewer = null;
    public CDPDFViewer getViewer() {
        return this.mViewer;
    }
    
    private CDCanvas2D mCanvas = null;
    public CDCanvas2D getCanvas() {
        return this.mCanvas;
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
    
    private JPanel mPanel = null;
    public JPanel getPanel() {
        return this.mPanel;
    }
    
    // constructor
    public CD() throws IOException {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(CD.INITIAL_WIDTH, CD.INITIAL_HEIGHT);
        this.mPanel = new JPanel();
        this.mViewer = new CDPDFViewer(this);
        this.mCanvas = new CDCanvas2D(this);
        
        this.mXform = new CDXform();
        this.mXform.scale(CD.INITIAL_DIALATION);
        this.mEventListener = new CDEventListener(this);
        
        this.mScenarioMgr = new CDScenarioMgr(this);
        this.mPtCurveMgr = new CDPtCurveMgr();
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);
        
        // connect to event listners
//        this.mViewer.addMouseListener(this.mEventListener);
//        this.mViewer.addMouseMotionListener(this.mEventListener);
//        this.mViewer.addMouseWheelListener(this.mEventListener);
//        this.mViewer.addKeyListener(this.mEventListener);    
//        this.mViewer.setFocusable(true);
//        
//        this.mCanvas.addMouseListener(this.mEventListener);
//        this.mCanvas.addMouseMotionListener(this.mEventListener);
//        this.mCanvas.addMouseWheelListener(this.mEventListener);
//        this.mCanvas.addKeyListener(this.mEventListener);    
//        this.mCanvas.setFocusable(true);
        
        this.mPanel.addMouseListener(this.mEventListener);
        this.mPanel.addMouseMotionListener(this.mEventListener);
        this.mPanel.addMouseWheelListener(this.mEventListener);
        this.mPanel.addKeyListener(this.mEventListener);    
        this.mPanel.setFocusable(true);
        
        
        // build and show
        this.mCanvas.setOpaque(false);
        this.mPanel.setLayout(null);
        this.mPanel.add(this.mCanvas);
//        this.mCanvas.setBackground(Color.blue);
        this.mPanel.add(this.mViewer);
        
        this.mFrame.add(this.mPanel);
        this.mViewer.setBounds(
            0,0,CD.INITIAL_WIDTH,CD.INITIAL_HEIGHT);
        this.mCanvas.setBounds(
            0,0,CD.INITIAL_WIDTH,CD.INITIAL_HEIGHT);
//        this.mViewer.setPreferredSize(
//            new Dimension(CD.INITIAL_WIDTH,CD.INITIAL_HEIGHT));
//        this.mCanvas.setPreferredSize(
//            new Dimension(CD.INITIAL_WIDTH/2,CD.INITIAL_HEIGHT/2));
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }
    
    // main
    public static void main(String[] args) throws IOException {
        CD myCaperDoc = new CD();
    }
}
