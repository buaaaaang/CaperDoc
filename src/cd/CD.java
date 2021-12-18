package cd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public static final int HIERARCHY_WIDTH = 150;
    private static final Color SIDE_COLOR = new Color(200, 200, 200, 255);
    
    // fields
    private JFrame mFrame = null;
    public JFrame getFrame() {
        return this.mFrame;
    }
    
    private final int initialWidth;
    public int getInitialWidth() {
        return this.initialWidth;
    }
    private final int initialHeight;
    public int getInitialHeight() {
        return this.initialHeight;
    }
    
    private CDPtCurveMgr mPtCurveMgr = null;
    public CDPtCurveMgr getPtCurveMgr() {
        return this.mPtCurveMgr;
    }
    
    private CDButtonMgr mButtonMgr = null;
    public CDButtonMgr getButtonMgr() {
        return this.mButtonMgr;
    }
    
    private JPanel mPanel = null;
    public JPanel getPanel() {
        return this.mPanel;
    }
    
    private CDPDFViewer mPDFViewer = null;
    public CDPDFViewer getPDFViewer() {
        return this.mPDFViewer;
    }
    
    private CDCanvas2D mCanvas = null;
    public CDCanvas2D getCanvas() {
        return this.mCanvas;
    }
    
    private CDButtonViewer mButtonViewer = null;
    public CDButtonViewer getButtonViewer() {
        return this.mButtonViewer;
    }
    
    private CDSideViewer mSideViewer = null;
    public CDSideViewer getSideViewer() {
        return this.mSideViewer;
    }
    
    private CDXform mXform = null;
    public CDXform getXform() {
        return this.mXform;
    }
    
    private ArrayList<Integer> mBranchYPoses = null;
    public ArrayList<Integer> getBranchYPoses() {
        return this.mBranchYPoses;
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
        Dimension size  =Toolkit.getDefaultToolkit().getScreenSize();
        this.initialWidth = (int) size.getWidth();
        this.initialHeight = (int) size.getHeight() - 40;
        
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(this.initialWidth, this.initialHeight);
        //this.mFrame.setVisible(true);
        
        this.mXform = new CDXform(this);
        this.mBranchYPoses = new ArrayList<>();
        this.mBranchYPoses.add(0);

        this.mPanel = new JPanel();
        this.mPDFViewer = new CDPDFViewer(this, path);
        this.mCanvas = new CDCanvas2D(this);
        this.mButtonViewer = new CDButtonViewer(this);
        this.mSideViewer = new CDSideViewer(this);
        
        this.mEventListener = new CDEventListener(this);
                
        this.mScenarioMgr = new CDScenarioMgr(this);
        this.mPtCurveMgr = new CDPtCurveMgr();
        this.mButtonMgr = new CDButtonMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);
        
        this.mPanel.addMouseListener(this.mEventListener);
        this.mPanel.addMouseMotionListener(this.mEventListener);
        this.mPanel.addMouseWheelListener(this.mEventListener);
        this.mPanel.addKeyListener(this.mEventListener);    
        this.mPanel.setFocusable(true);
        
        // build and show
        this.mPanel.setLayout(null);
        
        this.mButtonViewer.setOpaque(false);
        this.mCanvas.setOpaque(false);
        
        this.mPanel.add(this.mSideViewer);
        this.mPanel.add(this.mButtonViewer);
        this.mPanel.add(this.mCanvas);
        this.mPanel.add(this.mPDFViewer);
        this.mPanel.setOpaque(false);
        this.mSideViewer.setBackground(CD.SIDE_COLOR);
        
        this.mPDFViewer.setBounds(0,0, this.initialWidth, this.initialHeight);
        this.mCanvas.setBounds(0,0, this.initialWidth, this.initialHeight);
        this.mSideViewer.setBounds(0, 0, CD.HIERARCHY_WIDTH, 
            this.initialHeight);
        this.mButtonViewer.setBounds(0,0, this.initialWidth, 
            this.initialHeight);
        
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
