package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToGoDown extends XLoggableCmd {
    // constants
    private static final int AMOUNT = 30;
    
    // fields
    private int mDir;
    
    // private constructor
    private CDCmdToGoDown(XApp app, int dir) {
        super(app);
        this.mDir = dir;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, int dir) {
        CDCmdToGoDown cmd = new CDCmdToGoDown(app, dir);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getXform().translateUp(this.mDir * CDCmdToGoDown.AMOUNT);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
