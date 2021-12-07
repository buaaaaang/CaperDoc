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
                    ((CDCropScenario)this.mScenario).createCroppedImage();
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
                    ((CDCropScenario)this.mScenario).createCroppedImage();
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
    
    public BufferedImage createCroppedImage() {
        CD cd = (CD)this.getApp();
        Point screenAnchorPt = this.mCropBox.getAnchorPt();
        Point2D.Double worldAnchorPt = 
            cd.getXform().calcPtFromScreenToWorld(screenAnchorPt);
        cd.getViewer();
        
        
        int x = this.mCropBox.x;
        int y = this.mCropBox.y;
        int width = this.mCropBox.width;
        int height = this.mCropBox.height;
        Rectangle screenRect = new Rectangle(x, y, x + width, y + height);
        try{
            BufferedImage pageImage = cd.getViewer().getRenderer().renderImage(1, 1);
            BufferedImage croppedImage = cropImage(pageImage, screenRect);
            File outputfile = new File("Cropped_Image/cropped.jpg");
            ImageIO.write(croppedImage, "jpg", outputfile);
        } catch (IOException e) {
                System.out.println("Error: cannot load page");
        }
        return null;
    }
    
    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
      BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
      return dest; 
   }
}