package ui;

import java.util.Scanner;
import core.Connect4;
import core.Connect4Client;
import javafx.application.Application;

/**
 * DriverMain holds the main method
 * Request the user whether to play game in console,
 * or whether to run in a GUI.
 * 
 * If they chose console further request on if they
 * are playing the computer or another player.
 * 
 * From here the game branches to other GUI or console.
 * 
 * @author IanTaylor
 * @version 2.0
 *
 */
public class DriverMain {
	
	/**
	 * main method requests game type.
	 * Launches the game.
	 * @param args - Main String [] args
	 */
	public static void main(String[] args) {
		
		DriverMain gameStarter = new DriverMain();
		
		// Scanner object for user input
		Scanner in = new Scanner(System.in);
		
		// Run OnlineOrOffline() to find out the users choice
		// 'P' shall be returned to play player, 
		// 'C' shall be returned to play computer
		char compOrPerson = gameStarter.compOrPerson();
		
		// User wants to play against C.
		// In if statement move branch between GUI or console based
		if(compOrPerson == 'C') {
			
			// now check if they want GUI or console based game
			char consoleOrGUI = gameStarter.consoleOrGUI();
			
			// This is for console and against computer
			if (consoleOrGUI == 'C') {
				
				// launch console pass in C for player to be added
				Connect4TextConsole textConsole = new Connect4TextConsole(consoleOrGUI);
				
				textConsole.gameType(compOrPerson);
				
			}else {
				Connect4GUI guiGame = new Connect4GUI();
				
				guiGame.initialGame(args, compOrPerson);
			}
			
		}else {
			char onlineOrOffline  = gameStarter.onlineOrOffline();
			
			if (onlineOrOffline == 'O') {
				
				System.out.println("Please make sure server is running");
				
				char consoleOrGUI = gameStarter.consoleOrGUI();
				
				// This is for console and against computer
				if (consoleOrGUI == 'G') {			
					Application.launch(Connect4Client.class);
				}else {
					// Create online console game
					System.out.println("Create console game");
					
					Connect4OnlineTextConsole.main(args);
				}
			} else {
				
				// now check if they want GUI or console based game
				char consoleOrGUI = gameStarter.consoleOrGUI();
				
				// This is for console and against computer
				if (consoleOrGUI == 'C') {
					
					// launch console pass in C for player to be added
					Connect4TextConsole textConsole = new Connect4TextConsole(consoleOrGUI);
					
					textConsole.gameType(compOrPerson);
					
				}else {
					
					Connect4GUI guiGame = new Connect4GUI();
					
					guiGame.initialGame(args, compOrPerson);
				}
			}
		}		
	}
	
	public char compOrPerson () {
		// Scanner object for user input
		Scanner in = new Scanner(System.in);
		
		// OnlineOrOffline
		char compOrPerson = ' ';
				
		while(compOrPerson == ' ') {
			System.out.println("Enter ‘P’ if you want to play against another player; "
					+ "enter ‘C’ to play against computer.");
			System.out.print(">> ");
			
			compOrPerson = in.next().charAt(0);
			
			if(compOrPerson != 'P' && compOrPerson != 'p' && compOrPerson != 'C' && compOrPerson != 'c') {
				System.out.println("@Wrong input");
				compOrPerson = ' ';
			}
			if (compOrPerson == 'p')
				compOrPerson = 'P';
			if (compOrPerson == 'c')
				compOrPerson = 'C';
			
		}
	
		return compOrPerson;
	}
	
	public char consoleOrGUI () {
		// Scanner object for user input
		Scanner in = new Scanner(System.in);
				
		// OnlineOrOffline
		char consoleOrGUI = ' ';
						
		while(consoleOrGUI == ' ') {
			System.out.println("Enter ‘C’ if you want to play on the console; "
					+ "enter ‘G’ to play on GUI.");
			
			System.out.print(">> ");
			consoleOrGUI = in.next().charAt(0);
						
			if(consoleOrGUI != 'C' && consoleOrGUI != 'c' && consoleOrGUI != 'G' && consoleOrGUI != 'g') {
				System.out.println("Wrong input");
				consoleOrGUI = ' ';
			}
			if (consoleOrGUI == 'g')
				consoleOrGUI = 'G';
			if (consoleOrGUI == 'c')
				consoleOrGUI = 'C';
			
		}
		return consoleOrGUI;
	}
	
	public char onlineOrOffline () {
		// Scanner object for user input
		Scanner in = new Scanner(System.in);
						
		// OnlineOrOffline
		char onlineOrOffline = ' ';
								
		while(onlineOrOffline == ' ') {
			System.out.println("Enter ‘O’ if you want to play online; "
					+ "enter ‘L’ for local.");
			System.out.print(">> ");
							
			onlineOrOffline = in.next().charAt(0);
								
			if(onlineOrOffline != 'O' && onlineOrOffline != 'o' && onlineOrOffline != 'L' && onlineOrOffline != 'l') {
				System.out.println("Wrong input");
				onlineOrOffline = ' ';
			}
			if (onlineOrOffline == 'o')
				onlineOrOffline = 'O';
			if (onlineOrOffline == 'l')
				onlineOrOffline = 'L';
		}
		return onlineOrOffline;
	}
}
