package cd.cmd;

import cd.CD;
import java.awt.event.MouseWheelEvent;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToScrollSide extends XLoggableCmd {
    // constants
    private static final int SCROLL_AMOUNT = 10;
    
    // fields
    private MouseWheelEvent mEvent;
    
    // private constructor
    private CDCmdToScrollSide(XApp app, MouseWheelEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseWheelEvent e) {
        CDCmdToScrollSide cmd = new CDCmdToScrollSide(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        cd.getSideViewer().shift(this.mEvent.getWheelRotation() * 
            CDCmdToScrollSide.SCROLL_AMOUNT);

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
