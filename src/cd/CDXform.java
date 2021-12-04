package cd;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class CDXform {
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
    
    public CDXform() {
        this.mCurXformFromScreenToWorld = new AffineTransform();
        this.mCurXformFromWorldToScreen = new AffineTransform();
        this.mStartXformFromWorldToScreen = new AffineTransform();
        this.mStartScreenPt = new Point();
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
        
        double scale = amount;
        if (direction > 0) { scale = 1 / amount; }
        
        this.mCurXformFromWorldToScreen.translate(worldPt.x, worldPt.y);
        this.mCurXformFromWorldToScreen.scale(scale, scale);
        this.mCurXformFromWorldToScreen.translate(-worldPt.x, -worldPt.y);
        this.updateCurXformFromScreenToWorld();    
        return true;
    }
    
    public boolean scale(double scale) {
        this.mCurXformFromWorldToScreen.scale(scale, scale);
        this.updateCurXformFromScreenToWorld();    
        return true;
    }
    
}
