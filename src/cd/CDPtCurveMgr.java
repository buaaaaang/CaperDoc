package cd;

import java.util.ArrayList;

public class CDPtCurveMgr {
    public static final float STROKE_MIN_WIDTH = 1f;
    
    private CDPtCurve mCurPtCurve = null;
    public CDPtCurve getCurPtCurve() {
        return this.mCurPtCurve;
    }
    public void setCurPtCurve(CDPtCurve ptCurve) {
        this.mCurPtCurve = ptCurve;
    }
    private ArrayList<CDPtCurve> mPtCurves = null;    
    public ArrayList<CDPtCurve> getPtCurves() {
        return this.mPtCurves;
    }  
    public void setPtCurves(ArrayList<CDPtCurve> list) {
        this.mPtCurves = list;
    }
    private ArrayList<CDPtCurve> mPastSelectedPtCurves = null;
    public ArrayList<CDPtCurve> getPastSelectedPtCurves() {
        return this.mPastSelectedPtCurves;
    }
    public void setPastSelectedPtCurves(ArrayList<CDPtCurve> ptCurves) {
        this.mPastSelectedPtCurves = ptCurves;
    }
    private ArrayList<CDPtCurve> mSelectedPtCurves = null;
    public ArrayList<CDPtCurve> getSelectedPtCurves() {
        return this.mSelectedPtCurves;
    }
    public void setSelectedPtCurves(ArrayList<CDPtCurve> list) {
        this.mSelectedPtCurves = list;
    }
     
    public CDPtCurveMgr() {        
        this.mPtCurves = new ArrayList<>();
        this.mSelectedPtCurves = new ArrayList<>();
        this.mPastSelectedPtCurves= new ArrayList<>();
    }
}
