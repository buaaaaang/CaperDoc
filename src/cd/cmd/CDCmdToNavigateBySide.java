package cd.cmd;

import cd.CD;
import cd.scenario.CDSideButtonScenario;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToNavigateBySide extends XLoggableCmd {
    //fields
    MouseEvent mEvent = null;
    
    //private constructor
    private CDCmdToNavigateBySide(XApp app, MouseEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToNavigateBySide cmd = new CDCmdToNavigateBySide(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        if (cd.getButtonMgr().getCurMouseHierarchyButton() ==
            CDSideButtonScenario.getSingleton().getCurHandlingSideButton() ||
            cd.getButtonMgr().getCurMouseImplyButton() ==
            CDSideButtonScenario.getSingleton().getCurHandlingSideButton()) {
            if (SwingUtilities.isLeftMouseButton(this.mEvent)) {
                cd.getPDFViewer().addPage(cd.getPDFViewer().getFocus(),
                    (int) cd.getButtonMgr().getCurMouseHierarchyButton().
                    getContentPosition());
            } else {
                cd.getXform().goToYPos((int) cd.getButtonMgr().
                    getCurMouseHierarchyButton().getContentPosition());
            }
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
