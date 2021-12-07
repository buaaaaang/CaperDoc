package cd.cmd;

import java.awt.Point;
import cd.scenario.CDSelectScenario;
import cd.CDBox;
import cd.scenario.CDCropScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateCropBox extends XLoggableCmd {
    //fields
    Point mPt = null;
    
    //private constructor
    private CDCmdToCreateCropBox(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app, Point pt) {
        CDCmdToCreateCropBox cmd = new CDCmdToCreateCropBox(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CDBox cropBox = new CDBox(this.mPt);
        CDCropScenario.getSingleton().setCropBox(cropBox);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
