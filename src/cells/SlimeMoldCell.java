package cells;

public class SlimeMoldCell extends CellWithPatch {

    public SlimeMoldCell() {
        myPatchAmount = 0;
    }
    
    public void addToCamp (double amountToAdd) {
        myPatchAmount += amountToAdd;
    }

    @Override
    public void initializeWithState (int state) {
        myNextState = state;
        myPatchAmount = 0;
    }

}
