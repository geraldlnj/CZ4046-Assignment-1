package control;
import data.Info;
import entity.Grid;
import entity.State;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.*;

public class ValueIteration {

    private Grid gridWorldContainer;
    private int iterations=0;


    public ValueIteration(Grid gridWorldContainer){
        this.gridWorldContainer = gridWorldContainer;
        utilityUpdater();
        CommonMethods.utilityPrinter(gridWorldContainer);
        setOptimalPolicy(gridWorldContainer);
        CommonMethods.printOptimalPolicy(gridWorldContainer);
        System.out.printf("Iterations: %d\n",iterations);

    }

    //this method modifies the Grid object passed in from MainApp
    private void utilityUpdater(){
        double newUtility;
        double delta;
        double oldUtility;
        //State tempState
        double[][] utilityCopy = new double[Info.numCols][Info.numRows];

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new File("NewData.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        String ColumnNamesList = "iteration, (col|row), utility\n";
        builder.append(ColumnNamesList + "\n");


        for (int row = 0; row < Info.numRows; row++) {
            for (int col = 0; col < Info.numCols; col++) {
                utilityCopy[col][row] = gridWorldContainer.getState(col, row).getUtility();
            }
        }

        do { //while pass condition has not been met
            for (int col = 0; col < Info.numCols; col++) {
                for (int row = 0; row < Info.numRows; row++) {
                    builder.append(String.format("%d, %d|%d, %f\n", iterations, col, row, gridWorldContainer.getState(col, row).getUtility()));
                }
            }
            iterations++;
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
                        newUtility = gridWorldContainer.getState(col, row).getReward()+ Info.discount*CommonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[0]; //update utility from adjacent states

                        //to find max delta of this sweep
                        if (abs(oldUtility - newUtility) > delta) delta = abs(oldUtility - newUtility);
                        utilityCopy[col][row] = newUtility; //apply to utilityCopy array
                        /*
                        if (Info.debug) {
                            System.out.printf("(%d, %d): \ntype: %d\nchange: %f\n", col, row, gridWorldContainer.getState(col, row).getType(), abs(oldUtility - newUtility));
                            System.out.printf("utility: %f\n\n", newUtility);

                        }*/
                    }
                }
            }

        } while (delta>Info.gammma);
        pw.write(builder.toString());
        pw.close();
    }

    public static void setOptimalPolicy(Grid gridWorldContainer){
        for (int col = 0; col< Info.numCols; col++){
            for (int row = 0; row<Info.numRows; row++){
                if (gridWorldContainer.getState(col, row).getType()== 0) continue;
                gridWorldContainer.getState(col, row).setAction((int)CommonMethods.maxUtilityCalculator(gridWorldContainer, col, row)[1]);
            }
        }
    }






}
