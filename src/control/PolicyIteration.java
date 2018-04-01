package control;
import data.Info;
import entity.Grid;
import entity.State;
import control.*;
import static java.lang.Math.*;

public class PolicyIteration {
    private Grid gridWorldContainer;
    private int iterations=0;

    public PolicyIteration(Grid gridWorldContainer){
        this.gridWorldContainer = gridWorldContainer;
        policyUpdater();
        commonMethods.printOptimalPolicy(gridWorldContainer);
        System.out.printf("Iterations: %d\n",iterations);
    }

    //this method modifies the Grid object passed in from MainApp
    private void policyUpdater(){
        double maxUtilityAchievable;
        double newUtility;
        boolean unchanged;
        double curUtility;
        //State tempState
        int curAction;
        int newAction;
        State[] adjStates;
        double[] adjStateUtilities;





        do { //while pass condition has not been met
            iterations++;
            unchanged = true; //reset delta (change in state utility)


            //policy evaluation
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    adjStates = commonMethods.adjStates(gridWorldContainer, col, row);
                    adjStateUtilities = commonMethods.adjStateUtilities(gridWorldContainer, adjStates, col, row);
                    newUtility = gridWorldContainer.getState(col, row).getReward()+Info.discount*adjStateUtilities[gridWorldContainer.getState(col, row).getAction()];
                    gridWorldContainer.getState(col, row).setUtility(newUtility);
                }
            }



            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    curAction = gridWorldContainer.getState(col, row).getAction();

                    if(gridWorldContainer.getState(col, row).getType()==0) continue;        //skip if wall
                    adjStates = commonMethods.adjStates(gridWorldContainer, col, row);
                    adjStateUtilities = commonMethods.adjStateUtilities(gridWorldContainer, adjStates, col, row);

                    curUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[curAction];
                    maxUtilityAchievable = gridWorldContainer.getState(col, row).getReward()+ Info.discount*commonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[0]; //update utility from adjacent states
                    if (maxUtilityAchievable>curUtility) {
                        newAction = (int)commonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[1];
                        gridWorldContainer.getState(col, row).setAction(newAction);
                    }

                    else newAction = curAction;

                    if (curAction!=newAction) unchanged = false;


                    if (Info.debug) {
                        System.out.printf("(%d, %d): \ntype: %d\nchange: %f\n", col, row, gridWorldContainer.getState(col, row).getType(), abs(curUtility - maxUtilityAchievable));
                        System.out.printf("utility: %f\n\n", maxUtilityAchievable);
                    }
                }
            }
        } while (!unchanged);
    }






}
