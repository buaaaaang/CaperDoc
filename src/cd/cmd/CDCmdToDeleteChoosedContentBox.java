package cd.cmd;

import cd.CD;
import cd.button.CDContentButton;
import cd.scenario.CDWorldButtonScenario;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToDeleteChoosedContentBox extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToDeleteChoosedContentBox(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToDeleteChoosedContentBox cmd = new CDCmdToDeleteChoosedContentBox(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        ArrayList<CDContentButton> selected = 
            CDWorldButtonScenario.getSingleton().getChoosedButton();
        ArrayList<CDContentButton> all = cd.getButtonMgr().getContentButtons();
        for (CDContentButton b: selected) {
            all.remove(b);
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
