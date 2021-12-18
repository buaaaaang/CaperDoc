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
}
