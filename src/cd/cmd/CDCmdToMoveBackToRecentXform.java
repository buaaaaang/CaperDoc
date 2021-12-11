package cd.cmd;

import cd.CD;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToMoveBackToRecentXform extends XLoggableCmd {
    //fields
    private int mStep = 0;
    public void setStep(int step) {
        this.mStep = step;
    }
    
    //private constructor
    private CDCmdToMoveBackToRecentXform(XApp app, int step) {
        super(app);
        this.mStep = step;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, int step) {
        CDCmdToMoveBackToRecentXform cmd = new CDCmdToMoveBackToRecentXform(app, step);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        
        ArrayList<AffineTransform> history = cd.getXform().getXformHistory();
        if (this.mStep == -1 && 
            cd.getXform().getCurPosOnHistory() == history.size() - 1 &&
            history.get(history.size() - 1) != cd.getXform().
            getCurXformFromWorldToScreen()) {
            System.out.println("^^^^^^^^^^^^^");
            AffineTransform xform = cd.getXform().
                getCurXformFromWorldToScreen();
            cd.getXform().addXformHistory(xform);
            cd.getXform().transCurPosOnHistory(1);
        }
        
        cd.getXform().transCurPosOnHistory(this.mStep);
        int newPos = cd.getXform().getCurPosOnHistory();
        cd.getXform().setCurXformFromWorldToScreen(history.get(newPos));
        cd.getXform().updateCurXformFromScreenToWorld();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
