package cells;

public abstract class SugarScapeCell extends CellWithPatch {
    
    private int mySugar;
    private int myMaxSugar;
    private int myAgentsSugar;
    private int mySugarMetabolism = 0;
    private int myVision = 0;

    @Override
    public void initializeWithState (int state) {
        mySugar = myMaxSugar;
    }
    
    public void setMySugar(int mySugar) {
        this.mySugar = mySugar;
    }
    
    public int getMySugar() {
        return mySugar;
    }
    
    public void setMyMaxSugar(int myMaxSugar) {
        this.myMaxSugar = myMaxSugar;
    }
    
    public int getMyMaxSugar() {
        return myMaxSugar;
    }
    
    public void setMyVision(int myVision) {
        this.myVision = myVision;
    }
    
    public int getMyVision() {
        return myVision;
    }
    
    public int getMySugarMetabolism() {
        return mySugarMetabolism;
    }
    
    public void setMySugarMetabolism(int mySugarMetabolism) {
        this.mySugarMetabolism = mySugarMetabolism;
    }
    
    public int getMyAgentsSugar() {
        return myAgentsSugar;
    }
    
    public void setMyAgentsSugar(int myAgentsSugar) {
        this.myAgentsSugar = myAgentsSugar;
    }

}
