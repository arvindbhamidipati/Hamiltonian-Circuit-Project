package Graphs;

import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	static HamiltonianCycle<String> hc; 
	
	public static void main(String[] args){
		hc = new HamiltonianCycle<String>(); //instantiated HC
		System.out.println("\tWelcome to Hamiltonian Circuit Problem - Europe Edition.");
		readFile();
		//System.out.println("\t\t Please pick one of the options:\n");
		//startScreen();
	}
	
	public static void startScreen(){
		
		System.out.println("**************************************************************");
		System.out.println("\t\t\t  Options:");
		System.out.println("\t1) Read a desired list of cities from another text File");
		System.out.println("\t2) Add a possible edge to the graph");
		System.out.println("\t3) Remove a possible edge from the graph");
		System.out.println("\t4) Undo the previous path removal");
		System.out.println("\t5) Display the graph on the screen");
		System.out.println("\t6) Check for an itenarary that visits each city only once");
		System.out.println("\t7) Write to graph to a file: ");
		System.out.println("\t8) Exit program");
		System.out.print("\n\tWhat would you like to do? Pick a number (1-8): ");
		int choice = userScanner.nextInt();
		userScanner.nextLine();
		while(choice < 1 || choice > 8){
			System.out.println("\t\tInvalid number");
			System.out.print("\n\tWhat would you like to do? Pick a number (1-8): ");
			choice = userScanner.nextInt();
			userScanner.nextLine();
		}
		switch(choice){
		case 1: //reads a list of cities from a text file
			readFile();
			break;
		case 2: //add edge
			addEdge();
			break;
		case 3: //remove edge
			removeEdge();
			break;
		case 4: //undo the previous removal
			undoPreviousRemoval();
			break;
		case 5: 
			displayGraph(); //display graph
			break;
		case 6:
			solveGraph(); //solve and display graph
			break;
		case 7: //write to file
			writeToFile();
			break;
		case 8:
			System.out.println("**************************************************************");
			System.out.println("\tYou have ended the program."); //end program
			System.exit(0);
			break;
		}
		
	}
	
	public static Scanner userScanner = new Scanner(System.in);

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner = null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
        	File file= new File(filename);

        	try{
        		scanner = new Scanner(file);
        	}// end try
        	catch(FileNotFoundException fe){
        		System.out.println("Can't open input file\n");
       	    	return null; // array of 0 elements
        	} // end catch
        	return scanner;
	}
	
	public static void readFile(){
		System.out.println("**************************************************************");
		hc.clear();
		Scanner fileReader = openInputFile();
		if(fileReader != null){
			String input = "";
			String delims = "[ ]+";
			String[] tokens;
			while(fileReader.hasNext()){
				input = fileReader.nextLine();
				tokens = input.split(delims);
				hc.addToVertexSet(tokens[0]);
				for(int i = 1; i < tokens.length; i++){
					hc.addEdge(tokens[0], tokens[i], 0);
				}
				tokens = null;
			}
			System.out.println("The following cities have been added");
			hc.dispCityTable();
			//hc.showAdjTable();
		}
		
		startScreen();
	}
	
	public static void addEdge(){
		System.out.println("**************************************************************");
		int numOfCities = hc.dispCityTable();
		int cityOneInt = 0, cityTwoInt = 0;
		ArrayList<String> cityList = hc.getCities();
		while(cityOneInt < 1 || cityOneInt > cityList.size()){
			System.out.print("Please select a city from the list above that you wish to connect "
					+ "to another city (1-" + numOfCities + "): ");
			cityOneInt = userScanner.nextInt();
			userScanner.nextLine();
		}
		
		String cityOneS = cityList.get(cityOneInt - 1);
		while(cityTwoInt < 1 || cityTwoInt > cityList.size()){
			System.out.print("Please select another city from the list above that you wish to connect "
					+ "to another city (1-" + numOfCities + "): ");
			cityTwoInt = userScanner.nextInt();
			userScanner.nextLine();
		}
		String cityTwoS = cityList.get(cityTwoInt - 1);
		System.out.println("You have connected " + cityOneS + " to " + cityTwoS + ".");		
		hc.addEdge(cityOneS, cityTwoS, 0);
		//hc.showAdjTable();
		startScreen();
	}

	public static void removeEdge(){
		System.out.println("**************************************************************");
		int numOfCities = hc.dispCityTable();
		int cityOneInt = 0, cityTwoInt = 0;
		ArrayList<String> cityList = hc.getCities();
		while(cityOneInt < 1 || cityOneInt > cityList.size()){
			System.out.print("Please select a city from the list above that you wish to remove a connection from"
					 + "(1-" + numOfCities + "): ");
			cityOneInt = userScanner.nextInt();
			userScanner.nextLine();
		}
		
		String cityOneS = cityList.get(cityOneInt - 1);
		while(cityTwoInt < 1 || cityTwoInt > cityList.size()){
			System.out.print("Please select a city from the list above that you wish to disconnect from " + cityOneS
					 + " (1-" + numOfCities + "): ");
			cityTwoInt = userScanner.nextInt();
			userScanner.nextLine();
		}
		String cityTwoS = cityList.get(cityTwoInt - 1);
		//System.out.println("You have connected " + cityOneS + " to " + cityTwoS + ".");		
		if(hc.removeEdge(cityOneS, cityTwoS)){
			System.out.println("You have disconnected " + cityOneS + " from " + cityTwoS + ".");
		}else{
			System.out.println("Unable to disconnect " + cityOneS + " from " + cityTwoS + ".");
		}
		//hc.showAdjTable();
		startScreen();
	}
	
	public static void undoPreviousRemoval(){
		System.out.println("**************************************************************");
		Pair<String, String> lastRemoval = hc.undoRemove();
		if(lastRemoval == null){
			System.out.println("There was no removal to undo");
		}else{
			System.out.println("Re-added the edge between " + lastRemoval.first + " & " + lastRemoval.second);
		}
		hc.showAdjTable();
		startScreen();
	}
	
	public static void displayGraph(){
		System.out.println("**************************************************************");
		System.out.println("  How did you want to view the graph? ");
		System.out.println("\t1. Breadth first traversal");
		System.out.println("\t2. Depth first traversal");
		System.out.println("\t3. Adjacency list");
		int choice = 0;
		while(choice < 1 || choice > 3){
			System.out.print("\tPick a number (1-3): ");
			choice = userScanner.nextInt();
			userScanner.nextLine();
		}
		switch(choice){
		case 1:
			hc.showBFT(new CityVisitor());
			break;
		case 2:
			hc.showDFT(new CityVisitor());
			break;
		case 3:
			hc.showAdjTable();
			break;
		}
		startScreen();
	}
	
	public static void solveGraph(){
		System.out.println("**************************************************************");
		if(hc.calcHamiltonian()){
			System.out.println("Hamiltonian Circuit Found :)");
			String path = hc.printPath();
			System.out.println(path);
			int choice = 0;
			while(choice != 1 && choice != 2){
				System.out.print("Would you like to save the path to a file? (1 = yes, 2 = no): ");
				choice = userScanner.nextInt();
				userScanner.nextLine();
			}
			if (choice == 1)
				writeToFile(path);
		}else{
			System.out.println("Hamiltonian Circuit Not Found :(");
		}
		startScreen();
	}
	
	public static void writeToFile(){
		System.out.println("**************************************************************");
		System.out.print("\tPlease enter the output filename: ");
		String outFile = userScanner.nextLine();
		try {
			PrintWriter writer = new PrintWriter(outFile);
			hc.saveGraph(writer);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startScreen();
	}
	
	public static void writeToFile(String s){
		System.out.println("**************************************************************");
		System.out.print("\tPlease enter the output filename: ");
		String outFile = userScanner.nextLine();
		try
		{
			PrintWriter writer = new PrintWriter(outFile);
			writer.print(s);
			writer.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startScreen();
	}
}



