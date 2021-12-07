package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToDeselectSelectedPtCurves extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToDeselectSelectedPtCurves(XApp app) {
        super(app);
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app) {
        CDCmdToDeselectSelectedPtCurves cmd = 
            new CDCmdToDeselectSelectedPtCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getPtCurveMgr().getPtCurves().addAll(
            cd.getPtCurveMgr().getSelectedPtCurves());
        cd.getPtCurveMgr().getSelectedPtCurves().clear();
        cd.getPtCurveMgr().getPastSelectedPtCurves().clear();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
