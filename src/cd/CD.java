package cd;

import javax.swing.JFrame;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class CD extends XApp {
    // fields
    private JFrame mFrame = null;
    
    @Override
    public XScenarioMgr getScenarioMgr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public XLogMgr getXLogMgr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // constructor
    public CD() {
        this.mFrame = new JFrame("CaperDoc");
        this.mFrame.setSize(800, 600);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
    }
    
    // main
    public static void main(String[] args) {
        CD myCaperDoc = new CD();
    }
    
}
