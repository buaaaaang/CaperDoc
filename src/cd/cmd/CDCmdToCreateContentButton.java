package cd.cmd;

import cd.CD;
import cd.CDBox;
import cd.CDPDFViewer;
import cd.CDXform;
import cd.button.CDContentButton;
import cd.button.CDHierarchyButton;
import cd.scenario.CDCropScenario;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import x.XApp;
import x.XLoggableCmd;

public class CDCmdToCreateContentButton extends XLoggableCmd {
    //fields
    Point mPt = null;
    
    //private constructor
    private CDCmdToCreateContentButton(XApp app) {
        super(app);
    }
    
    // JSICmdToDoSomething.execute(jsi, ...)
    public static boolean execute(XApp app) {
        CDCmdToCreateContentButton cmd = new CDCmdToCreateContentButton(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        CD cd = (CD) this.mApp;
        int cropPage = CDCropScenario.getSingleton().calcPageToCrop();
        Rectangle rectToCrop = CDCropScenario.getSingleton().calcRectToCrop();
        BufferedImage croppedImage = 
            CDCropScenario.getSingleton().createCroppedImage(
            cropPage, rectToCrop);
        String ocrText = CDCropScenario.readImage(croppedImage);
        System.out.println("cropped image" + ": " + ocrText);
        CDBox cropBox = CDCropScenario.getSingleton().getCropBox();
        AffineTransform at = cd.getXform().getCurXformFromScreenToWorld();
        Rectangle box = at.createTransformedShape(cropBox).getBounds();
        int k = cd.getPDFViewer().onWhatBranch(new Point(cropBox.x, 0));
        Rectangle PDFBox = new Rectangle(
            (int) (box.x - cd.getPDFViewer().getWorldXPos() - k * 
            CDPDFViewer.PAGE_ROW_INTERVAL), 
            box.y - cd.getBranchYPoses().get(k), box.width, box.height);
        CDContentButton newButton = new CDContentButton(ocrText, PDFBox);
        cd.getButtonMgr().addContentButton(newButton);
        CDHierarchyButton b = 
            new CDHierarchyButton(ocrText, box.y, newButton);
        cd.getButtonMgr().addHierarchyButton(b);
        newButton.setHierarchyButton(b);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
    
}
