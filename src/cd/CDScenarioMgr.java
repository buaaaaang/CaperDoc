package cd;

import cd.scenario.CDDefaultScenario;
import x.XScenarioMgr;

public class CDScenarioMgr extends XScenarioMgr {
    //constructor
    public CDScenarioMgr(CD cd) {
        super(cd);
    }

    @Override
    protected void addScenarios() {
        this.mScenarios.add(CDDefaultScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        this.setCurScene(CDDefaultScenario.ReadyScene.getSingleton());
    }
    
    
    
}
