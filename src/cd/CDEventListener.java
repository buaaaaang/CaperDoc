package cd;

import java.awt.Point;
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
    
    private Point mCurPoint = null;
    public Point getCurPoint() {
        return this.mCurPoint;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        this.mCurPoint = e.getPoint();
        this.mCD.getPDFViewer().setFocus(e.getPoint());
        curScene.handleMousePress(e);
        this.mCD.getPanel().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        this.mCurPoint = e.getPoint();
        curScene.handleMouseRelease(e);
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
        this.mCurPoint = e.getPoint();
        curScene.handleMouseDrag(e);
        this.mCD.getPanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mCurPoint = e.getPoint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        this.mCurPoint = e.getPoint();
        curScene.handleMouseScroll(e);
        this.mCD.getPanel().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleKeyDown(e);
        this.mCD.getPanel().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CDScene curScene = (CDScene) this.mCD.getScenarioMgr().getCurScene();
        curScene.handleKeyUp(e);
        this.mCD.getPanel().repaint();
    }
}
