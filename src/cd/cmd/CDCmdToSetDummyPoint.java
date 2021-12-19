package cd.cmd;

import cd.CD;
import cd.scenario.CDSideButtonScenario;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToSetDummyPoint extends XLoggableCmd {
    //fields
    MouseEvent mEvent = null;
    
    //private constructor
    private CDCmdToSetDummyPoint(XApp app, MouseEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToSetDummyPoint cmd = new CDCmdToSetDummyPoint(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        Point2D.Double wPt = cd.getXform().
            calcPtFromScreenToWorld(this.mEvent.getPoint());
        CDSideButtonScenario.getSingleton().setDummyPt(
            new Point((int) wPt.x, (int) wPt.y));
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
