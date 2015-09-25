package cells;

public class SlimeMoldCell extends Cell {
    
    private double myCampAmount;
    
    public double getMyCampAmount () {
        return myCampAmount;
    }

    public void setMyCampAmount (double myCampAmount) {
        this.myCampAmount = myCampAmount;
    }

    public SlimeMoldCell() {
        myCampAmount = 0;
    }
    
    public void addToCamp (double amountToAdd) {
        myCampAmount += amountToAdd;
    }

    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub
        
    }

}
