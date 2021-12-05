package cd;

import java.awt.Dimension;
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
    public CD(String path) throws IOException {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(CD.INITIAL_WIDTH, CD.INITIAL_HEIGHT);
        this.mViewer = new CDPDFViewer(this, path);
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
