package data;
import java.util.ArrayList;
import java.util.Arrays;



public class Info {
	
	public static final int numCols = 6;
	public static final int numRows = 6;
	
	public static final double whiteReward = -0.04;
	public static final double greenReward = 1.00;
	public static final double orangeReward = -1.00;
	public static final double wallReward = 0.00;
	
	public static final int[][] walls = {{1,0},{4,1},{1,4},{2,4},{3,4}};
	public static final int[][] greenStates = {{0,0,},{2,0},{5,0},{3,1},{4,2},{5,3}};
	public static final int[][] orangeStates = {{1,1},{2,2},{3,3},{4,4},{5,1}};	
	
	public static final double actionProb = 0.80;
	public static final double leftActionProb = 0.10;
	public static final double rightActionProb = 0.10;
	
	public static final int[] startCoordinates = {2,3};
	
	public static final double discount = 0.99;
	
	public static final double rMax = 1.000;
		
	public static final double c = 0.100;	// FIXME: What to use for constant c
		
	public static final double epsilon = c*rMax;
		
	public static final int k = 10;

	public static final double gammma = epsilon * (1 - discount)/discount;

	public static final boolean debug = true;
			
}
