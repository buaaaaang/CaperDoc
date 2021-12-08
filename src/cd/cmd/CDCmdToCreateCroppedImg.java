package cd.cmd;

import cd.CD;
import cd.scenario.CDCropScenario;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateCroppedImg extends XLoggableCmd {
    //fields
    
    //private constructor
    private CDCmdToCreateCroppedImg(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToCreateCroppedImg cmd = new CDCmdToCreateCroppedImg(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        int cropPage = CDCropScenario.getSingleton().getCropPage();
        Rectangle rectToCrop = CDCropScenario.getSingleton().getRectToCrop();
        BufferedImage croppedImage = 
            CDCropScenario.getSingleton().createCroppedImage(
            cropPage, rectToCrop);
//        CDCropScenario.getSingleton().

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
