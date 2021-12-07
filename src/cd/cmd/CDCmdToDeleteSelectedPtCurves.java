package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToDeleteSelectedPtCurves extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToDeleteSelectedPtCurves(XApp app) {
        super(app);
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app) {
        CDCmdToDeleteSelectedPtCurves cmd =
            new CDCmdToDeleteSelectedPtCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
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
