package cd.cmd;

import cd.CD;
import cd.CDPDFViewer;
import cd.CDPtCurve;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToDrawPtCurve extends XLoggableCmd {
    //fields
    MouseEvent mEvent = null;
    
    //private constructor
    private CDCmdToDrawPtCurve(XApp app, MouseEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToDrawPtCurve cmd = new CDCmdToDrawPtCurve(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDPtCurve curPtCurve = cd.getPtCurveMgr().getCurPtCurve();
        Point pt = this.mEvent.getPoint();
        int branch = cd.getPDFViewer().onWhatBranch(pt);
        if (branch != curPtCurve.getCreatedBranch()) {
            return false;
        }

        int size = cd.getPtCurveMgr().getCurPtCurve().getPts().size();
        Point2D.Double lastWorldPt =
            curPtCurve.getPts().get(size - 1);
        Point lastScreenPt = cd.getXform().calcPtFromWorldToScreen(
            lastWorldPt);

        Point2D.Double worldPt = 
            cd.getXform().calcPtFromScreenToWorld(pt);
        Point2D.Double PDFPt = new Point2D.Double(
            worldPt.x - branch * CDPDFViewer.PAGE_ROW_INTERVAL,
            worldPt.y - cd.getBranchYPoses().get(branch));
        cd.getPtCurveMgr().getCurPtCurve().addPt(PDFPt);
        cd.getCanvas().repaint();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
