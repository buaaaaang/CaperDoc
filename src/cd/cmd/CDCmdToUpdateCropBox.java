package cd.cmd;

import cd.scenario.CDCropScenario;
import java.awt.Point;
import java.awt.event.MouseEvent;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToUpdateCropBox extends XLoggableCmd{
    //fields
    private Point mPt = null;
    
    //private constructor
    private CDCmdToUpdateCropBox(XApp app, MouseEvent e) {
        super(app);
        this.mPt = e.getPoint();
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToUpdateCropBox cmd = new CDCmdToUpdateCropBox(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
//        CD cd = (CD) this.mApp;
        CDCropScenario.getSingleton().getCropBox().update(this.mPt);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
