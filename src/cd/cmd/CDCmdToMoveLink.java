package cd.cmd;

import cd.CD;
import cd.button.CDLinkButton;
import cd.scenario.CDSideButtonScenario;
import cd.scenario.CDWorldButtonScenario;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToMoveLink extends XLoggableCmd {
    //fields
    MouseEvent mEvent = null;
    
    //private constructor
    private CDCmdToMoveLink(XApp app, MouseEvent e) {
        super(app);
        this.mEvent = e;
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app, MouseEvent e) {
        CDCmdToMoveLink cmd = new CDCmdToMoveLink(app, e);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        CDLinkButton button = CDWorldButtonScenario.getSingleton().getCurHandlingLinkButton();
        Point initialPt = button.getInitialPressedPoint();
        if (Math.pow(initialPt.x - this.mEvent.getPoint().x, 2) + 
            Math.pow(initialPt.y - this.mEvent.getPoint().y, 2) >
            Math.pow(CDWorldButtonScenario.MAX_DRAG_DISTANCE_TO_CLICK, 2)) {
            Point2D.Double wp1 = cd.getXform().calcPtFromScreenToWorld(
                this.mEvent.getPoint());
            Point2D.Double wp2 = cd.getXform().calcPtFromScreenToWorld(
                initialPt);
            Rectangle box = button.getInitialBox();
            button.setBox(new Rectangle(box.x + (int) (wp1.x - wp2.x), 
                box.y + (int) (wp1.y - wp2.y), box.width, box.height));
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
