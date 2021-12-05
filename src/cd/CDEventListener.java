package cd;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class CDEventListener implements MouseListener, MouseMotionListener, 
    MouseWheelListener, KeyListener {
    
    private CD mCD = null;
    
    public CDEventListener(CD cd) {
        this.mCD = cd;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleMousePress(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleMouseRelease(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleMouseDrag(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleMouseScroll(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleKeyDown(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleKeyUp(e);
        this.mCD.getViewer().repaint();
        this.mCD.getCanvas().repaint();
        this.mCD.getPanel().repaint();
    }
}
