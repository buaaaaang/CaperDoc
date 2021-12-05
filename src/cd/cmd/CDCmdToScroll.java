package cd.cmd;

import cd.CD;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToScroll extends XLoggableCmd {
    // constants
    private static final int SCROOL_AMOUT = 100;
    
    // fields
    private int mDir;
    
    // private constructor
    private CDCmdToScroll(XApp app, int dir) {
        super(app);
        this.mDir = dir;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, int dir) {
        CDCmdToScroll cmd = new CDCmdToScroll(app, dir);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getXform().translateUp(this.mDir * CDCmdToScroll.SCROOL_AMOUT);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
