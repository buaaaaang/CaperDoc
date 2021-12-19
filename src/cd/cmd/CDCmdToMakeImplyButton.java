package cd.cmd;

import cd.CD;
import cd.button.CDContentButton;
import cd.button.CDImplyButton;
import cd.scenario.CDWorldButtonScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToMakeImplyButton extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToMakeImplyButton(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToMakeImplyButton cmd = new CDCmdToMakeImplyButton(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDContentButton b = cd.getButtonMgr().getCurMouseContentButton();
        CDWorldButtonScenario s = CDWorldButtonScenario.getSingleton();
        CDContentButton from = s.getCurHandlingContentButton();
        if (!s.isChoosed(s.getCurHandlingContentButton())) {
            s.addChoosedButton(s.getCurHandlingContentButton());
        }
        CDContentButton to = b;
        CDImplyButton ib = new CDImplyButton(to.getName(),
            to.getBox().x + to.getBox().width, 
            to.getBox().y + to.getBox().height / 2);
        from.addImplyButton(ib);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
