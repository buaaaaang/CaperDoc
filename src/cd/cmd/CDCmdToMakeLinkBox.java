package cd.cmd;

import cd.CD;
import cd.CDPDFViewer;
import cd.button.CDLinkButton;
import cd.scenario.CDSideButtonScenario;
import java.awt.Rectangle;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToMakeLinkBox extends XLoggableCmd {
    //fields
    int mBranch;
    
    //private constructor
    private CDCmdToMakeLinkBox(XApp app, int branch) {
        super(app);
        this.mBranch = branch;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, int branch) {
        CDCmdToMakeLinkBox cmd = new CDCmdToMakeLinkBox(app, branch);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDSideButtonScenario s = 
            CDSideButtonScenario.getSingleton();
        Rectangle box = new Rectangle(s.getDummyPt().x - this.mBranch * 
            CDPDFViewer.PAGE_ROW_INTERVAL - 
            (int) cd.getPDFViewer().getWorldXPos() - (int) 
            (0.5 * s.getDummyWidth()), s.getDummyPt().y - 
            (int) (0.5 * CDLinkButton.HEIGHT) - 
            cd.getBranchYPoses().get(this.mBranch), s.getDummyWidth(),
            CDLinkButton.HEIGHT);
        CDLinkButton b = new CDLinkButton(s.getDummyName(), 
            s.getDummyContentPosition(), box);
        cd.getButtonMgr().addLinkButton(b);
        s.removeDummy();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
