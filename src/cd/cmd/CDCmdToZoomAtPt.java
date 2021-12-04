package cd.cmd;

import cd.CD;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToZoomAtPt extends XLoggableCmd {
    // constants
    private static final double AMOUNT = 1.005;
    
    // fields
    private MouseWheelEvent mEvent;
    
    // private constructor
    private CDCmdToZoomAtPt(XApp app, MouseWheelEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseWheelEvent e) {
        CDCmdToZoomAtPt cmd = new CDCmdToZoomAtPt(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        Point pt = this.mEvent.getPoint();
        int dir = this.mEvent.getWheelRotation();
        cd.getXform().dialate(pt, dir, CDCmdToZoomAtPt.AMOUNT);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
