package cd.cmd;

import cd.CD;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToScrollWorld extends XLoggableCmd {
    // constants
    private static final int SCROLL_AMOUNT = 100;
    
    // fields
    private MouseWheelEvent mEvent = null;
    
    // private constructor
    private CDCmdToScrollWorld(XApp app, MouseWheelEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseWheelEvent e) {
        CDCmdToScrollWorld cmd = new CDCmdToScrollWorld(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        int k = cd.getPDFViewer().onWhatBranch(this.mEvent.getPoint());
        int dir = ((this.mEvent.getWheelRotation() > 0) ? -1 : 1);
        if (k == -1) {
            cd.getXform().translateUp(dir * CDCmdToScrollWorld.SCROLL_AMOUNT);
        } else {
            cd.getBranchYPoses().set(
                k, cd.getBranchYPoses().get(k) + dir * 100);
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
