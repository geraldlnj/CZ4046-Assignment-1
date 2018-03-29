package control;
import data.Info;
import entity.Grid;
import entity.State;



import static java.lang.Math.*;

public class ValueIteration {

    private Grid gridWorldContainer;

    public ValueIteration(Grid gridWorldContainer){
        this.gridWorldContainer = gridWorldContainer;
        utilityUpdater();
        utilityPrinter();
    }

    //this method modifies the Grid object passed in from MainApp
    private void utilityUpdater(){
        boolean pass = false;
        double tempUtility;
        double delta;
        double oriUtility;
        //State tempState;

        while (!pass) { //while pass condition has not been met
            delta = 0; //reset delta (change in state utility
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {

                    oriUtility = gridWorldContainer.getState(col, row).getReward();
                    tempUtility = newUtilityCalculator(col, row); //update utility from adjacent utilities

                    //to find max delta of this sweep
                    if (abs(oriUtility-tempUtility) > delta)delta = abs(oriUtility-tempUtility);
                    if (!(gridWorldContainer.getState(col, row).isWall()))
                        gridWorldContainer.getState(col, row).setReward(tempUtility); //apply new utility to state
                }
            }
            if (delta < Info.gammma) pass = true; //if pass condition met, set to true
            System.out.println(delta);
        }
    }

    //helper method for utilityUpdater for updating each state's utility
    private double newUtilityCalculator(int col, int row){
        double maxUtility = Double.NEGATIVE_INFINITY; //double to find max of utility from all actions

        //array of the adjacent states.
        State[] adjState = new State[4];
        double tempUtility;
        double utilFrontAction;
        double utilLeftAction;
        double utilRightAction;

        /*
        indexes:
        0: up
        1: right
        2: down
        3: left
        */

        int adj[][] = {{0,-1}, {1, 0},{0, 1}, {-1, 0}};


        //create array of states adjacent to current
        for (int i=0;i<4;i++){
            try {
                adjState[i] = gridWorldContainer.getState(col + adj[i][0], row + adj[i][1]);
            } catch(IndexOutOfBoundsException error){ //if out of bounds, treat as a wall
                adjState[i] = new State(0.00);
                adjState[i].setWall(true);
            }
        }

        //create array of possible utilities from taking each action
        for (int i=0;i<4;i++){

            if(adjState[(i)%4].isWall()) utilFrontAction = gridWorldContainer.getState(col , row ).getReward();
            else utilFrontAction = adjState[i%4].getReward();

            if(adjState[(3+i)%4].isWall())utilLeftAction = gridWorldContainer.getState(col , row ).getReward();
            else utilLeftAction = adjState[(3+i)%4].getReward();

            if(adjState[(1+i)%4].isWall())utilRightAction = gridWorldContainer.getState(col , row ).getReward();
            else utilRightAction = adjState[(1+i)%4].getReward();

            tempUtility = 0.8*+utilFrontAction + 0.1*utilLeftAction + 0.1*utilRightAction;
            if (tempUtility>maxUtility) maxUtility = tempUtility;
        }
        return maxUtility;
    }

    private void utilityPrinter(){
        double tempUtility;
        System.out.println("Coordinates are in (col,row) format with the top left corner being(0,0).");
        for(int col=0;col<Info.numCols;col++){
            for (int row=0;row<Info.numRows;row++){
                tempUtility = gridWorldContainer.getState(col, row).getReward();
                System.out.printf("(%d, %d): %f\n", col, row, tempUtility);

            }
        }
    }
}
