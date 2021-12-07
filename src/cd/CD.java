package cd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JFrame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.JPanel;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

@SuppressWarnings("unchecked")
public class CD extends XApp {
    // constants
    public static final int INITIAL_PANEL_WIDTH = 1300;
    private static final int INITIAL_PANEL_HEIGHT = 1000;
    public static final int INITIAL_HIERARCHY_WIDTH = 300;
    
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
    
    private CDButtonViewer mButtonViewer = null;
    public CDButtonViewer getButtonViewer() {
        return this.mButtonViewer;
    }
    
    private JPanel mPanel = null;
    public JPanel getPanel() {
        return this.mPanel;
    }
    
    private JPanel mHierarchy = null;
    public JPanel getHierarchy() {
        return this.mHierarchy;
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
    public CD(String path) throws IOException {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(CD.INITIAL_PANEL_WIDTH, CD.INITIAL_PANEL_HEIGHT);

        this.mPanel = new JPanel();
        this.mHierarchy = new JPanel();
        this.mViewer = new CDPDFViewer(this, path);
        this.mCanvas = new CDCanvas2D(this);
        this.mButtonViewer = new CDButtonViewer(this);
        
        this.mXform = new CDXform();
        this.mEventListener = new CDEventListener(this);
        
        this.mScenarioMgr = new CDScenarioMgr(this);
        this.mPtCurveMgr = new CDPtCurveMgr();
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);
        
        this.mPanel.addMouseListener(this.mEventListener);
        this.mPanel.addMouseMotionListener(this.mEventListener);
        this.mPanel.addMouseWheelListener(this.mEventListener);
        this.mPanel.addKeyListener(this.mEventListener);    
        this.mPanel.setFocusable(true);
        
        this.mHierarchy.addMouseListener(this.mEventListener);
        this.mHierarchy.addMouseMotionListener(this.mEventListener);
        this.mHierarchy.addMouseWheelListener(this.mEventListener);
        this.mHierarchy.addKeyListener(this.mEventListener);    
        this.mHierarchy.setFocusable(true);
        
        // build and show
        
        this.mPanel.setLayout(null);
//        this.mButtonViewer.setOpaque(false);
        
        
        this.mButtonViewer.setOpaque(false);
//        this.mHierarchy.setOpaque(false);
        this.mCanvas.setOpaque(false);
        
        this.mPanel.add(this.mButtonViewer);
        this.mPanel.add(this.mHierarchy);
        this.mPanel.add(this.mCanvas);
        this.mPanel.add(this.mViewer);
//        this.mPanel.setBackground(Color.blue);
        this.mPanel.setOpaque(false);
        this.mHierarchy.setBackground(Color.orange);
        
        this.mViewer.setBounds(0,0,CD.INITIAL_PANEL_WIDTH,CD.INITIAL_PANEL_HEIGHT);
        this.mCanvas.setBounds(0,0,CD.INITIAL_PANEL_WIDTH,CD.INITIAL_PANEL_HEIGHT);
        this.mHierarchy.setBounds(0, 0, INITIAL_HIERARCHY_WIDTH, INITIAL_PANEL_HEIGHT);
        this.mButtonViewer.setBounds(0,0,CD.INITIAL_PANEL_WIDTH,CD.INITIAL_PANEL_HEIGHT);
        
        this.mFrame.add(this.mHierarchy);
        
        this.mFrame.add(this.mPanel);
        
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }
    
    public void save() {
        JSONArray info = new JSONArray();
        
        // write amount of pt curve, name box, link box in first line
        
        // write pt curves
        
        // write name box
        
        // write link box
        
    }
    
    public void load(String savePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser(); 
        try {
            System.out.println("Loading savefile...");
            Object arr = parser.parse(new FileReader(savePath));
            JSONArray loaded = (JSONArray) arr;
            Iterator<JSONObject> iterator = loaded.iterator();
            
            // read amount of pt curve, name box, link box in first line

            // read pt curves

            // read name box

            // read link box
                 
        } catch (FileNotFoundException e) {
            System.out.println("No save file : You may press 's' to save");
            return;
        }
        System.out.println("Save file loaded");
    }
    
    // main
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ParseException {
        // get file path to open from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter path to the file you want to open");
        String path = sc.nextLine();
        if (path.equals("")) { path = "real analysis.pdf"; }
        System.out.println();
        
        // open progrem with given path
        CD myCaperDoc = new CD(path);
        
        // load or make filename_CaporDoc.json file
        String parentPath;
        String fileName;
        int lastIndex = path.lastIndexOf('/');
        if (lastIndex < 0) {
            parentPath = "";
            fileName = (path.split("\\."))[0];
        } else {
            parentPath = path.substring(0,lastIndex + 1);
            fileName = (path.substring(lastIndex + 1).split("\\."))[0];
        }
        String savePath = parentPath + fileName + "_CaperDoc.json";
        myCaperDoc.load(savePath);
    }
}
