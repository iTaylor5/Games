package core;

import java.util.InputMismatchException;
import java.util.Scanner;
import core.Connect4;
import core.Connect4ComputerPlayer;

/**
 * Connect4TextConsole is for the User Interface.
 * It utilises the game logic from the core package.
 * Displays a basic console Connect4 game.
 * @author IanTaylor
 * 
 * @version 2.0
 *
 */
public class Connect4TextConsole {
	
	public char choice;
	
	/** Variable containing the connect4 game logic*/
	private Connect4 game;
	
	/** Object of the Connect4ComputerPlayer class */
	private Connect4ComputerPlayer comp;
	

	/** Constructor initialising the game logic and printing a blank board
	 * 
	 * @param choice - the user decision on who to play
	 */
	public Connect4TextConsole(char choice) {

		game = new Connect4();
		this.choice = choice;
//		game.addPlayer(choice);
//		if(choice == 'C'){
//			comp = new Connect4ComputerPlayer();
//		}
		
	}
	/**
	 * Initialise the game depending on whether player
	 * selected verse computer or another player.
	 * @param gameType the type of opposition to be played.
	 */
	public void gameType(char gameType) {
		
		// Scanner object for user input
		Scanner in = new Scanner(System.in);
		
		System.out.print("Begin Game.");
	
		game.addPlayer(gameType);
		game.printBoard();
		
		if(choice == 'C'){
			comp = new Connect4ComputerPlayer();
		}		
		runGame();
		in.close();
	}
	
	/**
	 * Run the game until no more pieces 
	 * or a player has won
	 */
	public void runGame(){
		
		// Scanner object for input from user created
		Scanner in = new Scanner(System.in);
				
		// If game is not won and player 2 has pieces to play
		while(!game.won && game.listOfplayers[1].piecesLeft() != 0){
					
			// Users column choice
			int col = 0;
			//game.printBoard();
			// iterates between the 2 players
			for (int i = 0; i < game.listOfplayers.length; i++){

				boolean played = false; // players move has been accepted
			
				if(game.listOfplayers[i].name == "Computer") {
					comp.move(game);
				}else {
					while(!played) {
						
						boolean validMove = false; // Confirm if move is valid
								
						while(!validMove) {
							// Input from user & check its a valid column number
							col = inputCol()-1;
							
							validMove = game.checkIfColHasSpace(col);
						}
									
						played = game.addPiece(col, game.listOfplayers[i]); // adds pieces
						
						game.listOfplayers[i].removePiece(); // decrements the players number of moves
					}
				}
				
				game.printBoard(); // displays the updated board
						
				// Checks to see if game has been won.
				if(game.won) {
					System.out.println("Player "+ game.listOfplayers[i].name + " has won!" );
					break;
				}
			}
			// Checks to see if game has been won.
			if(game.won) {
				System.out.println("Congratulations");
			}
		}
		if(!game.won) {
			System.out.println("GAME IS A DRAW");
		}
		in.close();
	}
	/**
	 * This method is used to make sure the user
	 * does not input the wrong choice for when selecting
	 * columns.
	 * @return the integer that can be used or 0.
	 */
	public int inputCol() {
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("It's your turn. ");
		System.out.println("Choose a column number from 1-7");
		
		try {		
			return in.nextInt();
			
		}catch(InputMismatchException ex ) {
			System.out.println("You did not enter a number between 1-7");
			return 0;
			
		}
	}
}
