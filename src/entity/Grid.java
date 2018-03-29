package entity;

import data.Info;
import entity.State;

public class Grid {
	private State[][] gridWorld;
	
	public Grid() {
		gridWorld = new State[Info.numCols][Info.numRows];
		constructGrid();
	}

	public State getState(int col, int row){
	    return gridWorld[col][row];
    }

    public void setState(State newState, int col, int row){
	    gridWorld[col][row] = newState;
    }

	public void constructGrid() {
		for (int row=0;row<Info.numRows;row++) {
			for (int col=0;col<Info.numCols;col++) {
				gridWorld[col][row] = new State(Info.whiteReward);
                gridWorld[col][row].setType(1);
			}
		}
		
		for (int[] i:Info.greenStates) {
            gridWorld[i[0]][i[1]].setType(3);
			gridWorld[i[0]][i[1]].setReward(Info.greenReward);}
		for (int[] i:Info.orangeStates) {
            gridWorld[i[0]][i[1]].setType(4);
		    gridWorld[i[0]][i[1]].setReward(Info.orangeReward);}
		for (int[] i:Info.walls) {
			gridWorld[i[0]][i[1]].setType(0);
			gridWorld[i[0]][i[1]].setReward(Info.wallReward);
			}

	}
	
	public void displayGrid() {
		
		State temp;
		
		StringBuilder display = new StringBuilder();
		
		display.append("|");
		for (int col = 0; col<Info.numCols; col++) display.append("----|");
		display.append("\n");
		
		for (int row = 0; row < Info.numRows; row++) {
			display.append("|");
			for (int col = 0; col<Info.numCols; col++) {
				temp = gridWorld[col][row];
				if (temp.getType()==0) display.append(String.format(" %-2s |", "WW"));
				else if (temp.getReward() != Info.whiteReward) display.append(String.format(" %+1.0f |", temp.getReward()));
				else display.append(String.format("%4s|", ""));
			}
			display.append("\n");
			display.append("|");
			for (int col = 0; col<Info.numCols; col++) display.append("----|");
			display.append("\n");
		}
		System.out.println(display.toString());
	}

}
