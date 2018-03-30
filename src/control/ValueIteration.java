package control;
import data.Info;
import entity.Grid;
import entity.State;
import control.*;



import static java.lang.Math.*;

public class ValueIteration {

    private Grid gridWorldContainer;

    public ValueIteration(Grid gridWorldContainer){
        this.gridWorldContainer = gridWorldContainer;
        utilityUpdater();
        utilityPrinter();
        commonMethods.setOptimalPolicy(gridWorldContainer);
        commonMethods.printOptimalPolicy(gridWorldContainer);
    }

    //this method modifies the Grid object passed in from MainApp
    private void utilityUpdater(){
        double newUtility;
        double delta;
        double oldUtility;
        //State tempState
        double[][] utilityCopy = new double[Info.numCols][Info.numRows];

        for (int row = 0; row < Info.numRows; row++) {
            for (int col = 0; col < Info.numCols; col++) {
                utilityCopy[col][row] = gridWorldContainer.getState(col, row).getUtility();
            }
        }

        do { //while pass condition has not been met
            delta = 0; //reset delta (change in state utility)
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    gridWorldContainer.getState(col, row).setUtility(utilityCopy[col][row]); //apply new utility to state
                }
            }
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    if(!(gridWorldContainer.getState(col, row).getType()==0)) { //if not wall
                        oldUtility = gridWorldContainer.getState(col, row).getUtility();
                        newUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*newUtilityCalculator(gridWorldContainer, col, row); //update utility from adjacent states

                        //to find max delta of this sweep
                        if (abs(oldUtility - newUtility) > delta) delta = abs(oldUtility - newUtility);
                        utilityCopy[col][row] = newUtility; //apply to utilityCopy array
                        System.out.printf("(%d, %d): \ntype: %d\nchange: %f\n", col, row, gridWorldContainer.getState(col, row).getType(), abs(oldUtility - newUtility));
                        System.out.printf("utility: %f\n\n", newUtility);
                    }
                }
            }

        } while (delta>Info.gammma);
    }

    //helper method for utilityUpdater for updating each state's utility
    private double newUtilityCalculator(Grid gridWorldContainer, int col, int row){
        //array of the adjacent states.
        double maxUtility;
        State[] adjState;
        double[] adjUtility;

        //get array of achievable states
        adjState = commonMethods.adjStates(gridWorldContainer, col, row);
        adjUtility = commonMethods.adjStateUtilities(gridWorldContainer, adjState, col, row);
        maxUtility = adjUtility[0];
        for (int i = 1; i<4; i++) maxUtility = max(maxUtility, adjUtility[i]);
        return maxUtility;
}

    private void utilityPrinter(){
        double tempUtility;
        System.out.println("Coordinates are in (col,row) format with the top left corner being(0,0).");
        for(int col=0;col<Info.numCols;col++){
            for (int row=0;row<Info.numRows;row++){
                tempUtility = gridWorldContainer.getState(col, row).getUtility();
                System.out.printf("(%d, %d): %f\n", col, row, tempUtility);

            }
        }
    }


}
