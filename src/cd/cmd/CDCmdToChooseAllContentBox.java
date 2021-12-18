package cd.cmd;

import cd.CD;
import cd.button.CDContentButton;
import cd.scenario.CDWorldButtonScenario;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToChooseAllContentBox extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToChooseAllContentBox(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToChooseAllContentBox cmd = new CDCmdToChooseAllContentBox(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        ArrayList<CDContentButton> buttons = 
            cd.getButtonMgr().getContentButtons();
        CDWorldButtonScenario.getSingleton().initializeChoosedButton();
        for (CDContentButton b: buttons) {
            CDWorldButtonScenario.getSingleton().addChoosedButton(b);
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
