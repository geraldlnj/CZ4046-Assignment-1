package entity;

public class State {
	private double stateReward = 0.000;
	private boolean stateIsWall = false;
	
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
	
}
