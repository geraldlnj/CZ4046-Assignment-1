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
    //this method modifies the Grid object passed in from MainApp
    private void policyUpdater(){
        double maxUtilityAchievable;
        boolean unchanged;
        double oldUtility;
        //State tempState
        double[][] utilityCopy = new double[Info.numCols][Info.numRows];
        char oldAction;
        char newAction;
        State[] adjStates;
        double[] adjStateUtilities;



        for (int row = 0; row < Info.numRows; row++) {
            for (int col = 0; col < Info.numCols; col++) {
                utilityCopy[col][row] = gridWorldContainer.getState(col, row).getUtility();
            }
        }

        do { //while pass condition has not been met
            iterations++;
            unchanged = true; //reset delta (change in state utility)


            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    gridWorldContainer.getState(col, row).setUtility(utilityCopy[col][row]); //apply new utility to state
                }
            }


            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    oldAction = gridWorldContainer.getState(col, row).getAction();
                    newAction = '*';
                    if(gridWorldContainer.getState(col, row).getType()==0) {
                        continue;
                    }//if not wall
                    adjStates = commonMethods.adjStates(gridWorldContainer, col, row);
                    adjStateUtilities = commonMethods.adjStateUtilities(gridWorldContainer, adjStates, col, row);

                    switch (gridWorldContainer.getState(col, row).getAction()){
                        case '^':
                            oldUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[0];
                            break;
                        case '>':
                            oldUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[1];
                            break;
                        case 'v':
                            oldUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[2];
                            break;
                        case '<':
                            oldUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[3];
                            break;
                        default:
                            oldUtility = 0.00;
                            break;
                    }

                    maxUtilityAchievable = gridWorldContainer.getState(col, row).getReward()+ Info.discount*commonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[0]; //update utility from adjacent states
                    if (maxUtilityAchievable>oldUtility) {

                        utilityCopy[col][row] = maxUtilityAchievable; //apply to utilityCopy array
                        switch((int)commonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[1]){
                            case 0:
                                newAction = '^';
                                break;
                            case 1:
                                newAction = '>';
                                break;
                            case 2:
                                newAction = 'v';
                                break;
                            case 3:
                                newAction = '<';
                                break;
                            default:
                                break;
                        }
                        gridWorldContainer.getState(col, row).setAction(newAction);
                    }

                    if (oldAction!=newAction) unchanged = false;


                    if (Info.debug) {
                        System.out.printf("(%d, %d): \ntype: %d\nchange: %f\n", col, row, gridWorldContainer.getState(col, row).getType(), abs(oldUtility - maxUtilityAchievable));
                        System.out.printf("utility: %f\n\n", maxUtilityAchievable);
                    }
                }
            }
        } while (!unchanged);
    }






}
