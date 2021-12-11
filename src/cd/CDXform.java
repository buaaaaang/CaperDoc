package cd;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CDXform {
    // constants
    private static final double MAX_SCALE = 200;
    private static final double MIN_SCALE = 0.4;
    private static final int MAX_HISTORY_LEN = 50;
    
    // fields
    private CD mCD = null;
    
    private AffineTransform mCurXformFromWorldToScreen = null;
    public AffineTransform getCurXformFromWorldToScreen() {
        return this.mCurXformFromWorldToScreen;
    }
    public void setCurXformFromWorldToScreen(AffineTransform xform) {
        this.mCurXformFromWorldToScreen = xform;
        this.updateCurXformFromScreenToWorld();
    }
    
    private AffineTransform mCurXformFromScreenToWorld = null;
    public AffineTransform getCurXformFromScreenToWorld() {
        return this.mCurXformFromScreenToWorld;
    }
    
    private AffineTransform mStartXformFromWorldToScreen = null;
    private AffineTransform mSaveXformFromWorldToScreen = null;
    public AffineTransform getSaveXformFromWorldToScreen() {
        return this.mSaveXformFromWorldToScreen;
    }
    
    private Point mStartScreenPt = null;
    public void setStartScreenPt(Point pt) {
        this.mStartScreenPt = pt;
        this.mStartXformFromWorldToScreen.setTransform(
            this.mCurXformFromWorldToScreen);
    }
    
    
    public CDXform(CD cd) {
        this.mCD = cd;
        this.mCurXformFromScreenToWorld = new AffineTransform();
        this.mCurXformFromWorldToScreen = new AffineTransform();
        this.mSaveXformFromWorldToScreen = new AffineTransform();
        this.mStartXformFromWorldToScreen = new AffineTransform();
        this.mStartScreenPt = new Point();
        double initialScale = (double) cd.getInitialHeight() / 
            CDPDFViewer.PAGE_INTERVAL;
        this.mCurXformFromWorldToScreen.scale(initialScale, initialScale); 
        this.mCurXformFromScreenToWorld.scale(1/initialScale, 1/initialScale); 
        this.mXformHistory = new ArrayList<AffineTransform>();
    }
        
    public void updateCurXformFromScreenToWorld(){
        try {
            this.mCurXformFromScreenToWorld =
                    this.mCurXformFromWorldToScreen.createInverse();
        } catch (NoninvertibleTransformException e) {
            System.out.println("Error: inverting singular matrix");
        }
    }
        public Point calcPtFromWorldToScreen(Point2D.Double worldPt){
        Point screenPt = new Point();
        this.mCurXformFromWorldToScreen.transform(worldPt, screenPt);
        return screenPt;
    }
    
    public Point2D.Double calcPtFromScreenToWorld(Point screenPt){
        Point2D.Double worldPt = new Point2D.Double();
        this.mCurXformFromScreenToWorld.transform(screenPt, worldPt);
        return worldPt;
    }
    
    public boolean translateTo(Point pt){
        if (this.mStartScreenPt ==  null){
            return false;
        }
        
        Point2D.Double worldPt0 = this.calcPtFromScreenToWorld(
            this.mStartScreenPt);
        Point2D.Double worldPt1 = this.calcPtFromScreenToWorld(pt);
        double dx = worldPt1.x - worldPt0.x;
        double dy = worldPt1.y - worldPt0.y;
        
        this.mCurXformFromWorldToScreen.setTransform(
            this.mStartXformFromWorldToScreen);
        
        this.mCurXformFromWorldToScreen.translate(dx, dy);
        this.updateCurXformFromScreenToWorld();
        return true;
    }
    
    public boolean translateUp(int amount){
        this.mCurXformFromWorldToScreen.translate(0, amount);
        this.updateCurXformFromScreenToWorld();
        return true;
    }
        
    public boolean dialate(Point pt, int direction, double amount){
        Point2D.Double worldPt = this.calcPtFromScreenToWorld(pt);
        
        double s = (this.calcPtFromScreenToWorld(new Point(100,0)).x - 
            this.calcPtFromScreenToWorld(new Point(0,0)).x) / 100;
        double scale; 
        if (direction > 0) { 
            if (s > CDXform.MAX_SCALE) {
                return false;
            }
            scale = 1 / amount; 
        } else {
            if (s < CDXform.MIN_SCALE) {
                return false;
            }
            scale = amount;
        }
        
        this.mCurXformFromWorldToScreen.translate(worldPt.x, worldPt.y);
        this.mCurXformFromWorldToScreen.scale(scale, scale);
        this.mCurXformFromWorldToScreen.translate(-worldPt.x, -worldPt.y);
        this.updateCurXformFromScreenToWorld();    
        return true;
    }
    
    
    private double getProperDialation() {
        CDPDFViewer viewer = this.mCD.getPDFViewer();
        return viewer.getHeight() / (CDPDFViewer.PAGE_INTERVAL * 0.7);
    }
    
    private AffineTransform getDefaultXformFromWorldToScreen() {
        AffineTransform xform = new AffineTransform();
        xform.scale(this.getProperDialation(), this.getProperDialation());
        return xform;
    }
    
    public boolean goToPage(int page) {
        this.mCurXformFromWorldToScreen = 
            this.getDefaultXformFromWorldToScreen();
        double diff = CDPDFViewer.PAGE_INTERVAL * page;
        this.mCurXformFromWorldToScreen.translate(0, -diff);
        this.updateCurXformFromScreenToWorld();
        return true;
    }
        
    
    private ArrayList<AffineTransform> mXformHistory = null;
    public void addXformHistory(AffineTransform xform) {
        this.mXformHistory.add(xform);
        if (this.mXformHistory.size() > CDXform.MAX_HISTORY_LEN) {
            int cut = 
                Math.min(this.mCurPosOnHistory, CDXform.MAX_HISTORY_LEN / 2);
            this.mXformHistory = new ArrayList<AffineTransform>(
                this.mXformHistory.subList(cut, this.mXformHistory.size()));
                this.transCurPosOnHistory(-1 * cut);
        }
        
    }
    public ArrayList<AffineTransform> getXformHistory() {
        return this.mXformHistory;
    }
    public void freshXformHistory() {
        if (this.mXformHistory.size() == 0) {
            return;
        }
        this.mXformHistory = new ArrayList<AffineTransform>(
            this.mXformHistory.subList(0, this.mCurPosOnHistory + 1));
    }
    
    private int mCurPosOnHistory = 0;
    public int getCurPosOnHistory() {
        return this.mCurPosOnHistory;
    }
    public void setCurPosOnHistory(int step) {
        if ((-1 < step) && (step < this.mXformHistory.size())) {
            this.mCurPosOnHistory = step;
        } else {
            System.out.println("cannot set cur position to given value");
        }
    }
    public void transCurPosOnHistory(int step) {
        int newCandidatePos = this.mCurPosOnHistory + step;
        if ((-1 < newCandidatePos) && (newCandidatePos < this.mXformHistory.size())) {
            this.mCurPosOnHistory = newCandidatePos;
        } else {
            System.out.println("cannot set cur position to given value");
        }
    }
    
    public boolean goToYPos(int y) {
        this.freshXformHistory();
        if (this.mXformHistory.size() == 0 ||
            this.mXformHistory.get(this.mXformHistory.size() - 1) !=
            this.mCurXformFromWorldToScreen) 
//                && 똑같은 곳 여러번 클릭했을때 추가 안되도록 해야함
//            (this.mXformHistory.get(this.mXformHistory.size() - 1).getShearY() !=
//            this.mCurXformFromWorldToScreen.getShearY())
            {
            this.addXformHistory(mCurXformFromWorldToScreen);
            this.transCurPosOnHistory(1);
        }
        this.mCurXformFromWorldToScreen = 
            this.getDefaultXformFromWorldToScreen();
        double diff = y - CDPDFViewer.PAGE_INTERVAL * 0.5 * 0.7;
        this.mCurXformFromWorldToScreen.translate(0, -diff);
        this.updateCurXformFromScreenToWorld();
        return true;
    }
}
