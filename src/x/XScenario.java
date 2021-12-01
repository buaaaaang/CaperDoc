package x;

import java.util.ArrayList;

public abstract class XScenario {
    //fields
    protected XApp mXApp = null;
    public XApp getApp() {
        return this.mXApp;
    }
    protected ArrayList<XScene> mScenes = null;
    
    //constructor
    protected XScenario(XApp app) {
        this.mXApp = app;
        this.mScenes = new ArrayList<XScene>();
        this.addScenes();
    }
    
    //abstract methods
    protected abstract void addScenes();
    
    //concrete methods
    protected void addScene(XScene scene) {
        this.mScenes.add(scene);
    }
}
