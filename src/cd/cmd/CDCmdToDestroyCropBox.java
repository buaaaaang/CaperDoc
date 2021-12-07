package cd.cmd;

import cd.CD;
import cd.scenario.CDCropScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToDestroyCropBox extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToDestroyCropBox(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToDestroyCropBox cmd = new CDCmdToDestroyCropBox(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDCropScenario.getSingleton().setCropBox(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
