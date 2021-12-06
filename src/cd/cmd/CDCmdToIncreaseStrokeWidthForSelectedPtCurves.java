package cd.cmd;

import java.awt.BasicStroke;
import cd.CD;
import cd.CDCanvas2D;
import cd.CDPtCurve;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToIncreaseStrokeWidthForSelectedPtCurves extends 
    XLoggableCmd {
    //fields
    private float mWDelta = Float.NaN;
    
    //private constructor
    private CDCmdToIncreaseStrokeWidthForSelectedPtCurves(XApp app, 
        float dw) {
        super(app);
        this.mWDelta = dw;
        
    }

    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app, float dw) {
        CDCmdToIncreaseStrokeWidthForSelectedPtCurves cmd = 
            new CDCmdToIncreaseStrokeWidthForSelectedPtCurves(app, dw);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getPtCurveMgr().getSelectedPtCurves().forEach(ptCurve -> {
            ptCurve.increaseStokeWidth(this.mWDelta);
        });
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
