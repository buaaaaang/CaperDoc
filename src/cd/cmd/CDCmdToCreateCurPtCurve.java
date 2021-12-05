package cd.cmd;

import java.awt.Point;
import java.awt.geom.Point2D;
import cd.CD;
import cd.CDPtCurve;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateCurPtCurve extends XLoggableCmd {
    // field
    private Point mScreenPt = null;
    private Point2D.Double mWorldPt = null;
    
    // private constructor
    private CDCmdToCreateCurPtCurve(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        CDCmdToCreateCurPtCurve cmd = new CDCmdToCreateCurPtCurve(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        this.mWorldPt = cd.getXform().calcPtFromScreenToWorld(this.mScreenPt);

        CDPtCurve curPtCurve = new CDPtCurve(this.mWorldPt, 
            cd.getCanvas().getCurColorForPtCurve(),
            cd.getCanvas().getCurStrokeForPtCurve());
        cd.getPtCurveMgr().setCurPtCurve(curPtCurve);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mScreenPt).append("\t");
        sb.append(this.mWorldPt);
        return sb.toString();
        
    }
    
}
