package cd.scenario;

import cd.CD;
import cd.CDCanvas2D;
import cd.CDScene;
import cd.CDBox;
import cd.cmd.CDCmdToCreateCropBox;
import cd.cmd.CDCmdToCreateSelectionBox;
import cd.cmd.CDCmdToDestroyCropBox;
import cd.cmd.CDCmdToUpdateCropBox;
import cd.cmd.CDCmdToUpdateSelectionBox;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class CDCropScenario extends XScenario {
    // singleton pattern
    private static CDCropScenario mSingleton = null;
    public static CDCropScenario createSingleton(XApp app) {
        assert (CDCropScenario.mSingleton == null);
        CDCropScenario.mSingleton = new CDCropScenario(app);
        return CDCropScenario.mSingleton;
    }
    public static CDCropScenario getSingleton() {
        assert (CDCropScenario.mSingleton != null);
        return CDCropScenario.mSingleton;
    }
    private int num = 0;
    // private constructor 
    private CDCropScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(CDCropScenario.CropReadyScene.createSingleton(this));
        this.addScene(CDCropScenario.CropScene.createSingleton(this));
    }
    
    public static class CropReadyScene extends CDScene {
        private static CropReadyScene mSingleton = null;
        public static CropReadyScene createSingleton(XScenario scenario) {
            assert (CropReadyScene.mSingleton == null);
            CropReadyScene.mSingleton = new CropReadyScene(scenario);
            return CropReadyScene.mSingleton;
        }
        public static CropReadyScene getSingleton() {
            assert (CropReadyScene.mSingleton != null);
            return CropReadyScene.mSingleton;
        }
        
        public CropReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDCmdToCreateCropBox.execute(cd, e.getPoint());   
            XCmdToChangeScene.execute(cd, 
                CDCropScenario.CropScene.getSingleton(), this.getReturnScene());
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }
        
        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_C:

//                    CDCmdToSaveCroppedImg.execute(cd, e);
//                    CDBox cropBox = ((CDCropScenario) this.mScenario).getCropBox();
//                    if (cropBox != null){
//                        int page = ((CDCropScenario)this.mScenario).calcPageToCrop();
//                        Rectangle rectToCrop = ((CDCropScenario)this.mScenario).calcRectToCrop();
//                        ((CDCropScenario)this.mScenario).createCroppedImage(page, rectToCrop);
////                        System.out.println(String.format("%3.1f, %3.1f, %3.1f", startPos[0], startPos[1], startPos[2]));
////                        System.out.println(String.format("%3.1f, %3.1f, %3.1f", endPos[0], endPos[1], endPos[2]));
//                    }
                    CDCropScenario scenario = (CDCropScenario)this.mScenario;
                    if (scenario.getCropBox() != null){
                        int page = scenario.calcPageToCrop();
                        Rectangle rectToCrop = scenario.calcRectToCrop();
                        BufferedImage croppedImage = scenario.createCroppedImage(page, rectToCrop);
                        String ocrText = scenario.readImage(croppedImage);
//                        System.out.println(ocrText);
                        System.out.println("cropped image" + scenario.num + ": " + ocrText);

                    }
                    CDCmdToDestroyCropBox.execute(cd);
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
            }
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            if (((CDCropScenario) this.mScenario).getCropBox() != null){
                ((CDCropScenario) this.mScenario).drawCropBox(g2);
            }
        }

    }

    public static class CropScene extends CDScene {
        private static CropScene mSingleton = null;
        public static CropScene createSingleton(XScenario scenario) {
            assert (CropScene.mSingleton == null);
            CropScene.mSingleton = new CropScene(scenario);
            return CropScene.mSingleton;
        }
        public static CropScene getSingleton() {
            assert (CropScene.mSingleton != null);
            return CropScene.mSingleton;
        }
        
        public CropScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            CDCmdToUpdateCropBox.execute(cd, e);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            XCmdToChangeScene.execute(cd, 
                CDCropScenario.CropReadyScene.getSingleton(), this.getReturnScene());
        }
        
        @Override
        public void handleMouseScroll(MouseWheelEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            CD cd = (CD) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_C:

//                    CDCmdToSaveCroppedImg.execute(cd, e);
                    CDCropScenario scenario = (CDCropScenario)this.mScenario;
                    if (scenario.getCropBox() != null){
                        int page = scenario.calcPageToCrop();
                        Rectangle rectToCrop = scenario.calcRectToCrop();
                        BufferedImage croppedImage = scenario.createCroppedImage(page, rectToCrop);
                        String ocrText = scenario.readImage(croppedImage);
                        System.out.println("cropped image" + scenario.num + ": " + ocrText);
                    }
                    CDCmdToDestroyCropBox.execute(cd);
                    XCmdToChangeScene.execute(cd, 
                        CDDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
            }
        }

        @Override
        public void renderWorldObjects(Graphics2D g2) {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            if (((CDCropScenario) this.mScenario).getCropBox() != null){
                ((CDCropScenario) this.mScenario).drawCropBox(g2);
            }
        }

    }
    
    private CDBox mCropBox = null;
    
    public CDBox getCropBox(){
        return this.mCropBox;
    }
    
    public void setCropBox(CDBox selectionBox) {
        this.mCropBox = selectionBox;
    }
    
    public void drawCropBox(Graphics2D g2) {
        g2.setColor(CDCanvas2D.COLOR_CROP_BOX);
        g2.setStroke(CDCanvas2D.STROKE_CROP_BOX);
        g2.draw(this.mCropBox);
    }
    
    public Rectangle calcRectToCrop() {
        CD cd = (CD)this.getApp();
        CDBox cropBox = this.getCropBox();
        CDBox reformulatedCropBox = cropBox.getReformulatedBox();
        Point startScreenPt = reformulatedCropBox.getAnchorPt();
        Point endScreenPt = reformulatedCropBox.getEndPt();
        double[] startPos = cd.getViewer().getPageLocationFromPts(startScreenPt);
        double[] endPos = cd.getViewer().getPageLocationFromPts(endScreenPt);
        int page = (int)startPos[0];
        
        Point startCropPt = new Point((int)startPos[1], (int)startPos[2]);
        Point endCropPt = new Point((int)endPos[1], (int)endPos[2]);
        Rectangle rectToCrop = new Rectangle(startCropPt);
        rectToCrop.add(endCropPt);
        
        return rectToCrop;
    }
    
    public int calcPageToCrop() {
        CD cd = (CD)this.getApp();
        CDBox cropBox = this.getCropBox();
        
        Point startScreenPt = cropBox.getAnchorPt();
        double[] startPos = cd.getViewer().getPageLocationFromPts(startScreenPt);
        int page = (int)startPos[0];
        return page;
    }
    
    public BufferedImage createCroppedImage(int page, Rectangle rect) {
        CD cd = (CD)this.getApp();
//        Point screenAnchorPt = this.mCropBox.getAnchorPt();
//        Point2D.Double worldAnchorPt = 
//            cd.getXform().calcPtFromScreenToWorld(screenAnchorPt);
//        cd.getViewer();
//        
//        
//        int x = this.mCropBox.x;
//        int y = this.mCropBox.y;
//        int width = this.mCropBox.width;
//        int height = this.mCropBox.height;
//        Rectangle screenRect = new Rectangle(x, y, x + width, y + height);
        BufferedImage croppedImage = null;
        try{
            BufferedImage pageImage = cd.getViewer().getRenderer().renderImage(page, 1);
            croppedImage = cropImage(pageImage, rect);
            
            File outputfile = new File(String.format("Cropped_Image/cropped%d.jpg", this.num));
            this.num += 1;
            ImageIO.write(croppedImage, "jpg", outputfile);
        } catch (IOException e) {
                System.out.println("Error: cannot load page");
        }
        return croppedImage;
    }
    
    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
//        System.out.println(String.format("%d, %d, %d, %d", rect.x, rect.y, Math.abs(rect.width), Math.abs(rect.height)));
        BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
        return dest; 
   }
    
    private String readImage(BufferedImage image) {
        Tesseract instance = Tesseract.getInstance();
        String result = "";
        try {
            result = instance.doOCR(image);
        } catch (Exception e) {
            System.out.println("Can't read image");
        }
        
        return result;
    }
}