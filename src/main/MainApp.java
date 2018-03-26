package main;

import data.*;
import entity.*;


public class MainApp {

	public static void main(String[] args) {
		Grid gridWorld = new Grid();
		gridWorld.constructGrid();
		gridWorld.displayGrid();
		System.out.println("true");
	}

}
