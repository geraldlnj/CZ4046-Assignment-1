package control;
import data.Info;
import entity.*;

import static java.lang.Math.max;

public class commonMethods {

    //returns array of states achievable
    public static State[] adjStates(Grid gridWorldContainer, int col, int row){
        int adj[][] = {{0,-1}, {1, 0},{0, 1}, {-1, 0}};
        State[] adjState = new State[4];
        //create array of states adjacent to current
        for (int i=0;i<4;i++){
            try {
                adjState[i] = gridWorldContainer.getState(col + adj[i][0], row + adj[i][1]);
            } catch(IndexOutOfBoundsException error){ //if out of bounds, treat as a wall
                adjState[i] = new State(0.00);
                adjState[i].setType(0);
            }
        }
        return adjState;
    }

    public static double[] adjStateUtilities(Grid gridWorldContainer,  State[] adjState, int col, int row){
        double tempUtility = gridWorldContainer.getState(col, row).getUtility();
        double utilFrontAction;
        double utilLeftAction;
        double utilRightAction;
        double[] utilities = new double[4];

        for (int i=0;i<4;i++){
            utilFrontAction = (adjState[(i)%4].getType()==0)? tempUtility : adjState[i%4].getUtility();
            utilLeftAction = (adjState[(3+i)%4].getType()==0)? tempUtility : adjState[(3+i)%4].getUtility();
            utilRightAction = (adjState[(1+i)%4].getType()==0)? tempUtility : adjState[(1+i)%4].getUtility();

            utilities[i] = 0.8*+utilFrontAction + 0.1*utilLeftAction + 0.1*utilRightAction;
        }
        return utilities;
    }

    public static void setOptimalPolicy(Grid gridWorldContainer){
        for (int col = 0; col< Info.numCols; col++){
            for (int row = 0; row<Info.numRows; row++){
                if (gridWorldContainer.getState(col, row).getType()== 0) continue;
                State[] adjState;
                double[] adjUtility;
                int index = 0;
                char action;

                adjState = adjStates(gridWorldContainer, col, row);
                adjUtility = commonMethods.adjStateUtilities(gridWorldContainer, adjState, col, row);
                for (int i = 1; i< 4; i++){
                    if (adjUtility[i] > adjUtility[index]) index = i;
                }

                switch (index){
                    case 0:
                        action = '^';
                        break;
                    case 1:
                        action = '>';
                        break;
                    case 2:
                        action = 'v';
                        break;
                    case 3:
                        action = '<';
                        break;
                    default:
                        //shouldnt happen.
                        action = '?';
                        break;
                }

                gridWorldContainer.getState(col, row).setAction(action);

            }
        }
    }

    public static void printOptimalPolicy(Grid gridWorldContainer) {
        State temp;

        StringBuilder display = new StringBuilder();

        display.append("|");
        for (int col = 0; col < Info.numCols; col++) display.append("----|");
        display.append("\n");

        for (int row = 0; row < Info.numRows; row++) {
            display.append("|");
            for (int col = 0; col < Info.numCols; col++) {
                temp = gridWorldContainer.getState(col, row);
                if (temp.getType()==0) display.append(String.format(" %-2s |", "WW"));
                else display.append(String.format(" %s  |", temp.getAction()));
            }
            display.append("\n");
            display.append("|");
            for (int col = 0; col < Info.numCols; col++) display.append("----|");
            display.append("\n");
        }
        System.out.println(display.toString());
    }

    public static void utilityPrinter(Grid gridWorldContainer){
        double tempUtility;
        System.out.println("Coordinates are in (col,row) format with the top left corner being(0,0).");
        for(int col=0;col<Info.numCols;col++){
            for (int row=0;row<Info.numRows;row++){
                tempUtility = gridWorldContainer.getState(col, row).getUtility();
                System.out.printf("(%d, %d): %f\n", col, row, tempUtility);

            }
        }
    }
    //helper method for utilityUpdater for getting max utility achievable from any action
    public static double[] maxUtilityCalculator(Grid gridWorldContainer, int col, int row){
        double[] maxUtilityAchievable = new double[2];
        //maxUtilityAchievable[0] gives the max utility achievable
        //maxUtilityAchievable[1] gives the action to get this max utility. quite cumbersome, but it works.

        State[] adjState; //array of the adjacent states.
        double[] adjUtility;

        //get array of achievable states
        adjState = commonMethods.adjStates(gridWorldContainer, col, row);
        adjUtility = commonMethods.adjStateUtilities(gridWorldContainer, adjState, col, row);

        maxUtilityAchievable[0] = adjUtility[0];
        maxUtilityAchievable[1] = (double)0;
        for (int i = 1; i<4; i++) {
            if (adjUtility[i]>maxUtilityAchievable[0]){
                maxUtilityAchievable[0] = adjUtility[i];
                maxUtilityAchievable[1] = (double)i;
            }
        }
        return maxUtilityAchievable;
    }
}
