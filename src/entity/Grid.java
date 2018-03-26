package entity;

import data.Info;
import entity.State;

public class Grid {
	private State[][] gridWorld = null;
	
	public Grid() {
		gridWorld = new State[Info.numCols][Info.numRows];
		constructGrid();
	}
	
	public void constructGrid() {
		for (int row=0;row<Info.numRows;row++) {
			for (int col=0;col<Info.numCols;col++) {
				gridWorld[col][row] = new State(Info.whiteReward);
			}
		}
		
		for (int[] i:Info.greenStates) gridWorld[i[0]][i[1]].setReward(Info.greenReward);
		for (int[] i:Info.orangeStates) gridWorld[i[0]][i[1]].setReward(Info.orangeReward);
		for (int[] i:Info.walls) {
			gridWorld[i[0]][i[1]].setWall(true);
			gridWorld[i[0]][i[1]].setReward(Info.wallReward);
			}

	}
	
	public void displayGrid() {
		
		State temp = null;
		
		StringBuilder display = new StringBuilder();
		
		display.append("|");
		for (int col = 0; col<Info.numCols; col++) display.append("----|");
		display.append("\n");
		
		for (int row = 0; row < Info.numRows; row++) {
			display.append("|");
			for (int col = 0; col<Info.numCols; col++) {
				temp = gridWorld[col][row];
				if (temp.isWall()) display.append(String.format(" %-2s |", "WW"));
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
