package cd;

import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.button.CDImplyButton;
import cd.button.CDLinkButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public CDEventListener getEventListener() {
        return this.mEventListener;
    }
            
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
    
    private String mSavePath = null;
    
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
        
        // loading
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
        this.mSavePath = parentPath + fileName + "_CaperDoc";
        try {
            this.load();
        } catch (ParseException ex) {
            Logger.getLogger(CD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save() {
        System.out.println("saving file...");
        File dir = new File(this.mSavePath);
        if (! dir.exists()) {
            dir.mkdir();
        }
        JSONArray ptCurveInfo = new JSONArray();
        for (CDPtCurve ptCurve: this.mPtCurveMgr.getPtCurves()) {
            JSONObject tmpPtCurve = new JSONObject();
            ArrayList<Point2D.Double> pts = ptCurve.getPts();
            
            for (int i = 0; i < pts.size(); i++) {
                tmpPtCurve.put("x" + i, "" + pts.get(i).x);
                tmpPtCurve.put("y" + i, "" + pts.get(i).y);
            }
            tmpPtCurve.put("Rcolor", ptCurve.getColor().getRed());
            tmpPtCurve.put("Bcolor", ptCurve.getColor().getBlue());
            tmpPtCurve.put("Gcolor", ptCurve.getColor().getGreen());
            tmpPtCurve.put("Acolor", ptCurve.getColor().getAlpha());
            tmpPtCurve.put("width", ((BasicStroke) (ptCurve.getStroke())).
                getLineWidth());
            ptCurveInfo.add(tmpPtCurve);
        }
        JSONArray buttonInfo = new JSONArray();
        // 1.content 2.Hierarchy 3.Imply 4.Link
        JSONObject tmpButton = new JSONObject();
        for (CDContentButton button1: this.mButtonMgr.getContentButtons()) {
            tmpButton = new JSONObject();
            tmpButton.put("key", "" + 1);
            tmpButton.put("name", button1.getName());
            tmpButton.put("x", "" + button1.getBox().x);
            tmpButton.put("y", "" + button1.getBox().y);
            tmpButton.put("width", "" + button1.getBox().width);
            tmpButton.put("height", "" + button1.getBox().height);
            buttonInfo.add(tmpButton);
            tmpButton = new JSONObject();
            CDHierarchyButton button2 = button1.getHierarchyButton();
            tmpButton.put("key", "" + 2);
            tmpButton.put("name", button2.getName());
            tmpButton.put("y", "" + button2.getContentPosition());
            buttonInfo.add(tmpButton);
            for (CDImplyButton button3: button1.getImplyButtons()) {
                tmpButton = new JSONObject();
                tmpButton.put("key", "" + 3);
                tmpButton.put("name", button3.getName());
                tmpButton.put("x", "" + button3.getContentXPos());
                tmpButton.put("y", "" + button3.getContentPosition());
                buttonInfo.add(tmpButton);
            }
        }
        for (CDLinkButton button4: this.mButtonMgr.getLinkButtons()) {
            tmpButton = new JSONObject();
            tmpButton.put("key", "" + 4);
            tmpButton.put("name", button4.getName());
            tmpButton.put("cp", "" + button4.getContentPosition());
            tmpButton.put("x", "" + button4.getBox().x);
            tmpButton.put("y", "" + button4.getBox().y);
            tmpButton.put("width", "" + button4.getBox().width);
            tmpButton.put("height", "" + button4.getBox().height);
            buttonInfo.add(tmpButton);
        }
        try {
            FileWriter file = new FileWriter(this.mSavePath + "/ptCurve.json");
            file.write(ptCurveInfo.toJSONString());
            file.flush();
            file.close();
            file = new FileWriter(this.mSavePath + "/button.json");
            file.write(buttonInfo.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("save done");
    }
    
    public void load() throws IOException, ParseException {
        JSONParser parser = new JSONParser(); 
        try {
            System.out.println("Loading savefile...");
            Object arr = parser.parse(
                new FileReader(this.mSavePath + "/ptCurve.json"));
            JSONArray loaded = (JSONArray) arr;
            Iterator<JSONObject> iterator = loaded.iterator();
            while (iterator.hasNext()) {
                JSONObject ptCurve = iterator.next();
                Color c = new Color((int) (long) ptCurve.get("Rcolor"),
                    (int) (long) ptCurve.get("Gcolor"),
                    (int) (long) ptCurve.get("Bcolor"),
                    (int) (long) ptCurve.get("Acolor"));
                BasicStroke s = new BasicStroke(
                    (float) (double) ptCurve.get("width"),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND);
                double x0 = Double.parseDouble((String) ptCurve.get("x0"));
                double y0 = Double.parseDouble((String) ptCurve.get("y0"));
                Point2D.Double pt0 = new Point2D.Double(x0, y0);
                CDPtCurve tmpPtCurve = new CDPtCurve(pt0, c, s, 0);
                
                for (int i = 0; i < (ptCurve.size() - 5) / 2; i++) {
                    double x = Double.parseDouble(
                        (String) ptCurve.get("x" + i));
                    double y =  Double.parseDouble(
                        (String) ptCurve.get("y" + i));
                    Point2D.Double pt = new Point2D.Double(x, y);
                    tmpPtCurve.addPt(pt);
                }
                this.mPtCurveMgr.getPtCurves().add(tmpPtCurve);
            }
            arr = parser.parse(new FileReader(this.mSavePath + "/button.json"));
            loaded = (JSONArray) arr;
            iterator = loaded.iterator();
            CDContentButton button1 = null;
            while (iterator.hasNext()) {
                JSONObject b = iterator.next();
                if (Integer.parseInt(b.get("key").toString()) == 1) {
                    button1 = new CDContentButton((String) b.get("name"),
                        new Rectangle(Integer.parseInt(b.get("x").toString()),
                        Integer.parseInt(b.get("y").toString()), 
                        Integer.parseInt(b.get("width").toString()),
                        Integer.parseInt(b.get("height").toString())));
                    this.mButtonMgr.addContentButton(button1);
                } else if (Integer.parseInt(b.get("key").toString()) == 2) {
                    CDHierarchyButton button2 = new CDHierarchyButton(
                        (String) b.get("name"), 
                        Double.parseDouble(b.get("y").toString()), button1);
                    button1.setHierarchyButton(button2);
                    this.mButtonMgr.getHierarchyButtons().add(button2);
                } else if (Integer.parseInt(b.get("key").toString()) == 3) {
                    CDImplyButton button3 = new CDImplyButton(
                        (String) b.get("name"),
                        Double.parseDouble(b.get("x").toString()), 
                        Double.parseDouble(b.get("y").toString()));
                    button1.addImplyButton(button3);
                } else {
                    CDLinkButton button4 = new CDLinkButton(
                        (String) b.get("name"),
                        Double.parseDouble(b.get("cp").toString()),
                        new Rectangle(Integer.parseInt(b.get("x").toString()),
                        Integer.parseInt(b.get("y").toString()), 
                        Integer.parseInt(b.get("width").toString()),
                        Integer.parseInt(b.get("height").toString())));
                    this.mButtonMgr.addLinkButton(button4);
                }
                
            }
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
    }
}
