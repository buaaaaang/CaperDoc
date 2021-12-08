package cd.cmd;

import cd.CD;
import cd.scenario.CDCropScenario;
import java.awt.Rectangle;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToSetRectToCrop extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToSetRectToCrop(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToSetRectToCrop cmd = new CDCmdToSetRectToCrop(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        Rectangle rectToCrop = CDCropScenario.getSingleton().calcRectToCrop();
        CDCropScenario.getSingleton().setRectToCrop(rectToCrop);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
