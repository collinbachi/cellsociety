package cells;

public abstract class SugarScapeCell extends CellWithPatch {

    private int myMaxSugar;
    private int myAgentsSugar;
    private int mySugarMetabolism;
    private int myVision;

    @Override
    public void initializeWithState (int state) {
        myNextState = state;
        myPatchAmount = myMaxSugar;
    }

    public void setMyMaxSugar (int myMaxSugar) {
        this.myMaxSugar = myMaxSugar;
    }

    public int getMyMaxSugar () {
        return myMaxSugar;
    }

    public void setMyVision (int myVision) {
        this.myVision = myVision;
    }

    public int getMyVision () {
        return myVision;
    }

    public int getMySugarMetabolism () {
        return mySugarMetabolism;
    }

    public void setMySugarMetabolism (int mySugarMetabolism) {
        this.mySugarMetabolism = mySugarMetabolism;
    }

    public int getMyAgentsSugar () {
        return myAgentsSugar;
    }

    public void setMyAgentsSugar (int myAgentsSugar) {
        this.myAgentsSugar = myAgentsSugar;
    }

}
