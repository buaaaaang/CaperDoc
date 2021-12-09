package cd;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class CDXform {
    // constants
    private static final double MAX_SCALE = 200;
    private static final double MIN_SCALE = 0.4;
    
    // fields
    private CD mCD = null;
    
    private AffineTransform mCurXformFromWorldToScreen = null;
    public AffineTransform getCurXformFromWorldToScreen() {
        return this.mCurXformFromWorldToScreen;
    }
    
    private AffineTransform mCurXformFromScreenToWorld = null;
    public AffineTransform getCurXformFromScreenToWorld() {
        return this.mCurXformFromScreenToWorld;
    }
    
    private AffineTransform mStartXformFromWorldToScreen = null;
    
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
        this.mStartXformFromWorldToScreen = new AffineTransform();
        this.mStartScreenPt = new Point();
        double initialScale = (double) cd.getInitialHeight() / 
            CDPDFViewer.PAGE_INTERVAL;
        this.mCurXformFromWorldToScreen.scale(initialScale, initialScale); 
        this.mCurXformFromScreenToWorld.scale(1/initialScale, 1/initialScale); 
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
        return viewer.getHeight() * 0.7 / CDPDFViewer.PAGE_INTERVAL;
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
    
}
