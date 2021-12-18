package cd;

import cd.scenario.CDButtonScenario;
import cd.scenario.CDColorScenario;
import cd.scenario.CDCropScenario;
import cd.scenario.CDDefaultScenario;
import cd.scenario.CDDrawScenario;
import cd.scenario.CDNavigateScenario;
import cd.scenario.CDSelectScenario;
import cd.scenario.CDSideButtonScenario;
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
        this.mScenarios.add(CDSelectScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDCropScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDButtonScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDSideButtonScenario.createSingleton(this.mApp));
        this.mScenarios.add(CDColorScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        this.setCurScene(CDDefaultScenario.ReadyScene.getSingleton());
    }
    
    
    
}
