package cd.cmd;

import java.awt.Point;
import java.awt.geom.Point2D;
import cd.CD;
import cd.CDPDFViewer;
import cd.CDPtCurve;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateCurPtCurve extends XLoggableCmd {
    // field
    private Point mScreenPt = null;
    
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
        int branch = cd.getPDFViewer().onWhatBranch(this.mScreenPt);
        Point2D.Double worldPt = 
            cd.getXform().calcPtFromScreenToWorld(this.mScreenPt);
        Point2D.Double PDFPt = new Point2D.Double(
            worldPt.x - branch * CDPDFViewer.PAGE_ROW_INTERVAL, 
            worldPt.y - cd.getBranchYPoses().get(branch));
        CDPtCurve curPtCurve = new CDPtCurve(PDFPt, 
            cd.getCanvas().getCurColorForPtCurve(),
            cd.getCanvas().getCurStrokeForPtCurve(), branch);
        cd.getPtCurveMgr().setCurPtCurve(curPtCurve);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mScreenPt).append("\t");
        return sb.toString();
        
    }
    
}
