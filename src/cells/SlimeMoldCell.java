package cells;

public class SlimeMoldCell extends CellWithPatch {

    public double getMyCampAmount () {
        return myPatchAmount;
    }

    public void setMyCampAmount (int myCampAmount) {
        this.myPatchAmount = myCampAmount;
    }

    public SlimeMoldCell() {
        myPatchAmount = 0;
    }
    
    public void addToCamp (double amountToAdd) {
        myPatchAmount += amountToAdd;
    }

    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub
        
    }

}
