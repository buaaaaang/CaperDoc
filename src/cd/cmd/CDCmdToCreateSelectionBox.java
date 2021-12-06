package cd.cmd;

import java.awt.Point;
import cd.scenario.CDSelectScenario;
import cd.CDSelectionBox;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateSelectionBox extends XLoggableCmd {
    //fields
    Point mPt = null;
    
    //private constructor
    private CDCmdToCreateSelectionBox(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    // CDCmdToDoSomething.execute(cd, ...)
    public static boolean execute(XApp app, Point pt) {
        
        CDCmdToCreateSelectionBox cmd = new CDCmdToCreateSelectionBox(app, pt);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CDSelectionBox selectionBox = new CDSelectionBox(this.mPt);
        CDSelectScenario.getSingleton().setCurSelectionBox(selectionBox);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
