package cd.cmd;

import cd.CD;
import cd.button.CDLinkButton;
import cd.scenario.CDWorldButtonScenario;
import java.awt.event.MouseEvent;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToMakeNewBranch extends XLoggableCmd {
    //fields
    MouseEvent mEvent;
    
    //private constructor
    private CDCmdToMakeNewBranch(XApp app, MouseEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToMakeNewBranch cmd = new CDCmdToMakeNewBranch(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDLinkButton button = CDWorldButtonScenario.getSingleton().
            getCurHandlingNeedButton();
        int curBranch = cd.getPDFViewer().onWhatBranch(this.mEvent.getPoint());
        cd.getPDFViewer().addPage(curBranch, 
            (int) button.getContentPosition());
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
