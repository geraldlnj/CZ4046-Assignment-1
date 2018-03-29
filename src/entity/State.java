package entity;

public class State {
	private double stateReward ;
	private boolean stateIsWall = false;
	private String stateAction = null;
	
	public State(double reward) {
		stateReward = reward;
	}
	
	public double getReward() {
		return stateReward;
	}
	
	public void setReward(double reward) {
		stateReward = reward;
	}
	
	public boolean isWall() {
		return stateIsWall;
	}
	
	public void setWall(boolean wall) {
		stateIsWall = wall;
	}

	public String getAction() {
		return stateAction;
	}

    public void setAction(String action){
	    stateAction = action;
    }
}
