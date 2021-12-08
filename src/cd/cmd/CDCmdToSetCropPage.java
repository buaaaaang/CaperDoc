package cd.cmd;

import cd.CD;
import cd.scenario.CDCropScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToSetCropPage extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToSetCropPage(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToSetCropPage cmd = new CDCmdToSetCropPage(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        int pageToCrop = CDCropScenario.getSingleton().calcPageToCrop();
        CDCropScenario.getSingleton().setCropPage(pageToCrop);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
