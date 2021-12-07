package cd.cmd;

import java.awt.Point;
import java.awt.event.MouseEvent;
import cd.scenario.CDSelectScenario;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToUpdateSelectionBox extends XLoggableCmd{
    //fields
    Point mPt = null;
    
    //private constructor
    private CDCmdToUpdateSelectionBox(XApp app, MouseEvent e) {
        super(app);
        this.mPt = e.getPoint();
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToUpdateSelectionBox cmd = new CDCmdToUpdateSelectionBox(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
//        CD cd = (CD) this.mApp;
        CDSelectScenario.getSingleton().getSelectionBox().update(this.mPt);
        CDSelectScenario.getSingleton().updateSelectedPtCurves();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
