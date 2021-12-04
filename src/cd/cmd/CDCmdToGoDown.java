package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToGoDown extends XLoggableCmd {
    // constants
    private static final int amount = 30;
    
    // fields
    private int dir;
    
    // private constructor
    private CDCmdToGoDown(XApp app, int dir) {
        super(app);
        this.dir = dir;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, int amount) {
        CDCmdToGoDown cmd = new CDCmdToGoDown(app, amount);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getXform().translateUp(this.dir * amount);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
