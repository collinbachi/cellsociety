package cells;

import java.util.List;

public class SlimeMoldCell extends CellWithPatch {
    
    public List<Integer> getMyForwardLocations () {
        return myForwardLocations;
    }

    public double getMyCampAmount () {
        return myPatchAmount;
    }

    public void setMyCampAmount (int myCampAmount) {
        this.myPatchAmount = myCampAmount;
    }

    public SlimeMoldCell() {
        myPatchAmount = 0;
        updateForwardLocations(myOrientation);
    }
    
    public void addToCamp (double amountToAdd) {
        myPatchAmount += amountToAdd;
    }

    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub
        
    }

}
