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
        double tempUtility;
        double delta;
        double oriUtility;
        //State tempState;

        do { //while pass condition has not been met
            delta = 0; //reset delta (change in state utility)
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    if(!(gridWorldContainer.getState(col, row).getType()==0)) { //if not wall
                        oriUtility = gridWorldContainer.getState(col, row).getReward();
                        tempUtility = oriUtility+ Info.discount*newUtilityCalculator(col, row); //update utility from adjacent utilities
                        //to find max delta of this sweep
                        if (abs(oriUtility - tempUtility) > delta) delta = abs(oriUtility - tempUtility);
                        System.out.printf("(%d, %d): \ntype: %d\nchange: %f\n", col, row, gridWorldContainer.getState(col, row).getType(), abs(oriUtility - tempUtility));
                        System.out.printf("utility: %f\n\n", tempUtility);
                        gridWorldContainer.getState(col, row).setReward(tempUtility); //apply new utility to state
                    }
                }
            }
        } while (delta>Info.gammma);
    }

    //helper method for utilityUpdater for updating each state's utility
    private double newUtilityCalculator(int col, int row){
        double maxUtility = Double.NEGATIVE_INFINITY; //double to find max of utility from all actions

        //array of the adjacent states.
        State[] adjState = new State[4];
        double tempUtility = gridWorldContainer.getState(col, row).getReward();
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
                adjState[i].setType(0);
            }
        }

        //create array of possible utilities from taking each action
        for (int i=0;i<4;i++){

            if(adjState[(i)%4].getType()==0) utilFrontAction = gridWorldContainer.getState(col , row ).getReward();
            else utilFrontAction = adjState[i%4].getReward();

            if(adjState[(3+i)%4].getType()==0)utilLeftAction = gridWorldContainer.getState(col , row ).getReward();
            else utilLeftAction = adjState[(3+i)%4].getReward();

            if(adjState[(1+i)%4].getType()==0)utilRightAction = gridWorldContainer.getState(col , row ).getReward();
            else utilRightAction = adjState[(1+i)%4].getReward();

            tempUtility += 0.8*+utilFrontAction + 0.1*utilLeftAction + 0.1*utilRightAction;
            maxUtility = Math.max(tempUtility, maxUtility);
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
