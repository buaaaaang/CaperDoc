package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToSaveFile extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToSaveFile(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToSaveFile cmd = new CDCmdToSaveFile(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
