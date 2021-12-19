package cd.cmd;

import cd.CD;
import cd.button.CDSideButton;
import cd.scenario.CDSideButtonScenario;
import x.XApp;
import x.XCmdToChangeScene;
import x.XLoggableCmd;

public class CDCmdToMakeDummy extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToMakeDummy(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToMakeDummy cmd = new CDCmdToMakeDummy(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDSideButton hb = CDSideButtonScenario.
            getSingleton().getCurHandlingSideButton();
        CDSideButtonScenario.getSingleton().setDummyName(
            hb.getName());
        CDSideButtonScenario.getSingleton().setDummyContentPosition(
            hb.getContentPosition());
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
