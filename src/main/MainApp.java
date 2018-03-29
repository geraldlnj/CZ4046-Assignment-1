package main;

import data.*;
import entity.*;
import control.*;
import java.util.Scanner;



public class MainApp {

	public static void main(String[] args) {
		Grid gridWorldContainer = new Grid();
		gridWorldContainer.constructGrid();
		gridWorldContainer.displayGrid();
        System.out.println("Discount: " + Info.discount);
        System.out.println("rMax: " + Info.rMax);
        System.out.println("Constant 'c': " + Info.c);
		System.out.println("Epsilon (c * Rmax): " + Info.epsilon);
		System.out.println("k = " + Info.k);

		System.out.println("1: Value Iteration\n2: Policy Iteration");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1:
                control.ValueIteration valueIterator = new control.ValueIteration(gridWorldContainer);
                break;
            case 2:
                //policyiteration(gridWorldContainer)
                break;
            default:
                System.out.println("Error. Run again.");
                return;
        }

	}

}
