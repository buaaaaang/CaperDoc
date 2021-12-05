package cd;

import cd.scenario.CDDefaultScenario;
import cd.scenario.CDDrawScenario;
import cd.scenario.CDNavigateScenario;
import x.XScenarioMgr;

public class CDScenarioMgr extends XScenarioMgr {
    //constructor
    public CDScenarioMgr(CD cd) {
        super(cd);
    }

    @Override
    protected void addScenarios() {
        this.mScenarios.add(CDDefaultScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDNavigateScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDDrawScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        this.setCurScene(CDDefaultScenario.ReadyScene.getSingleton());
    }
    
    
    
}
