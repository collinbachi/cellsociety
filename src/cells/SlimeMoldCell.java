package cells;

import java.util.List;

public class SlimeMoldCell extends CellWithPatch {
    
    private double myCampAmount;
    
    public List<Integer> getMyForwardLocations () {
        return myForwardLocations;
    }

    public double getMyCampAmount () {
        return myCampAmount;
    }

    public void setMyCampAmount (double myCampAmount) {
        this.myCampAmount = myCampAmount;
    }

    public SlimeMoldCell() {
        myCampAmount = 0;
        updateForwardLocations(myOrientation);
    }
    
    public void addToCamp (double amountToAdd) {
        myCampAmount += amountToAdd;
    }

    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub
        
    }

}
