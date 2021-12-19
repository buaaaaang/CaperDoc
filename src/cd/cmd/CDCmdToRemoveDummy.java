package cd.cmd;

import cd.CD;
import cd.scenario.CDSideButtonScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToRemoveDummy extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToRemoveDummy(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToRemoveDummy cmd = new CDCmdToRemoveDummy(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDSideButtonScenario.getSingleton().removeDummy();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
