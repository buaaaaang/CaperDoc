package cd.cmd;

import java.awt.BasicStroke;
import cd.CD;
import cd.scenario.CDDefaultScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToIncreaseStrokeWidthForCurPtCurve extends XLoggableCmd {
    //fields
    private float mWDelta = Float.NaN;
    
    //private constructor
    private CDCmdToIncreaseStrokeWidthForCurPtCurve(XApp app, float dw) {
        super(app);
        this.mWDelta = dw;
    }

    public static boolean execute(XApp app, float dw) {
        CDCmdToIncreaseStrokeWidthForCurPtCurve cmd = 
            new CDCmdToIncreaseStrokeWidthForCurPtCurve(app, dw);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDDefaultScenario.getSingleton().setDrawPenTip(true);
        BasicStroke bs = 
            (BasicStroke) cd.getCanvas().getCurStrokeForPtCurve();
        cd.getCanvas().increaseStrokeWidthForCurPtCurve(this.mWDelta);
        bs = (BasicStroke) cd.getCanvas().getCurStrokeForPtCurve();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
}
