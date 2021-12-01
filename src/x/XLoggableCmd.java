package x;

public abstract class XLoggableCmd implements XExecutable {
    //fields
    protected XApp mApp = null;
    
    //constructor
    protected XLoggableCmd(XApp app) {
        this.mApp = app;
    }
    
    @Override
    public final boolean execute() {
        if (this.defineCmd()) {
            this.mApp.getXLogMgr().addLog(this.createLog());
            return true;
        } else {
            return false;
        }
    };
    
    protected abstract boolean defineCmd();
    protected abstract String createLog();
    
}