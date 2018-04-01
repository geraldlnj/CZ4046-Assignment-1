package control;
import data.Info;
import entity.Grid;
import entity.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.*;

public class PolicyIteration {
    private Grid gridWorldContainer;
    private int iterations=0;

    public PolicyIteration(Grid gridWorldContainer){
        this.gridWorldContainer = gridWorldContainer;
        policyUpdater();
        CommonMethods.utilityPrinter(gridWorldContainer);
        CommonMethods.printOptimalPolicy(gridWorldContainer);
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

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new File("NewData.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        String ColumnNamesList = "iteration, (col, row), utility\n";
        builder.append(ColumnNamesList + "\n");





        do { //while pass condition has not been met
            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    builder.append(String.format("%d, %d|%d, %f\n", iterations, col, row, gridWorldContainer.getState(col, row).getUtility()));
                }
            }
            iterations++;
            unchanged = true; //reset delta (change in state utility)

            for (int i = 0;i<Info.k; i++) {
                //policy evaluation
                for (int row = 0; row < Info.numRows; row++) {
                    for (int col = 0; col < Info.numCols; col++) {
                        adjStates = CommonMethods.adjStates(gridWorldContainer, col, row);
                        adjStateUtilities = CommonMethods.adjStateUtilities(gridWorldContainer, adjStates, col, row);
                        newUtility = gridWorldContainer.getState(col, row).getReward() + Info.discount * adjStateUtilities[gridWorldContainer.getState(col, row).getAction()];
                        gridWorldContainer.getState(col, row).setUtility(newUtility);
                    }
                }
            }




            for (int row = 0; row < Info.numRows; row++) {
                for (int col = 0; col < Info.numCols; col++) {
                    if(gridWorldContainer.getState(col, row).getType()==0) continue;//skip if wall

                    curAction = gridWorldContainer.getState(col, row).getAction();

                    adjStates = CommonMethods.adjStates(gridWorldContainer, col, row);
                    adjStateUtilities = CommonMethods.adjStateUtilities(gridWorldContainer, adjStates, col, row);
                    curUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*adjStateUtilities[curAction];

                    maxUtilityAchievable = gridWorldContainer.getState(col, row).getReward()+ Info.discount*CommonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[0]; //update utility from adjacent states
                    if (maxUtilityAchievable>curUtility) {
                        System.out.printf("curUtility: %f\n", curUtility);
                        System.out.printf("maxUtility: %f\n", maxUtilityAchievable);
                        newAction = (int)CommonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[1];
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
        pw.write(builder.toString());
        pw.close();
    }






}
