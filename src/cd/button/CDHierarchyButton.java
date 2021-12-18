package cd.button;

public class CDHierarchyButton extends CDSideButton {
    
    protected CDContentButton mContentButton = null;
    public CDContentButton getContentButton() {
        return this.mContentButton;
    }
    
    public CDHierarchyButton(String name, double y, CDContentButton b) {
        super(name, y);
        this.mKind = CDButton.Button.HIERARCHY;
        this.mContentButton = b;
    }

//    @Override
//    public boolean contains(Point pt) {
//        if (this.mCD.getSideViewer().getMode() == CDSideViewer.Mode.IMPLY) {
//            return false;
//        }
//        int n = this.mCD.getButtonMgr().getHierarchyButtons().indexOf(this);
//        int shift = this.mCD.getSideViewer().getShiftAmount();
//        boolean b1 = pt.y > (CDHierarchyButton.HEIGHT * n - shift) &&
//            pt.y < (CDHierarchyButton.HEIGHT * (n + 1) - shift);
//        boolean b2 = pt.x < CD.HIERARCHY_WIDTH - GAP_SIDE;
//        return b1 && b2;
//    }
}
