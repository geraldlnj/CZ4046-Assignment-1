package entity;

public class State {
	private double stateReward ;
	private double stateUtility = 0.00;
	private int type;
	private String stateAction;
	
	public State(double reward) {
		stateReward = reward;
	}
	
	public double getReward() {
		return stateReward;
	}
	public void setReward(double reward) {
		stateReward = reward;
	}

	public double getUtility(){
		return stateUtility;
	}
	public void setUtility(double utility){
		this.stateUtility = utility;
	}
	
	public int getType() {
	    return type;
	}
	public void setType(int type) {
        //0=wall
        //1=white
        //3=green
        //4=orange
	    this.type = type;
	}

	public String getAction() {
		return stateAction;
	}
    public void setAction(String action){
	    stateAction = action;
    }
}
