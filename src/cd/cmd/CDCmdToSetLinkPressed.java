package cd.cmd;

import cd.CD;
import cd.button.CDLinkButton;
import cd.scenario.CDWorldButtonScenario;
import java.awt.Point;
import x.XApp;
import x.XCmdToChangeScene;
import x.XLoggableCmd;

public class CDCmdToSetLinkPressed extends XLoggableCmd {
    //fields
    private Point mPt = null;
    
    //private constructor
    private CDCmdToSetLinkPressed(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, Point pt) {
        CDCmdToSetLinkPressed cmd = new CDCmdToSetLinkPressed(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDLinkButton needButton = 
            cd.getButtonMgr().getCurMouseLinkButton();
        needButton.setInitialPressedPoint(this.mPt);
        needButton.setInitialBox();
        needButton.setHighlight(true);
        CDWorldButtonScenario.getSingleton().
            setCurHandlingNeedButton(needButton);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
