package core;

import java.util.Random;
/**
 * A computer simulation of a player
 * Initialised in the Connect4TextConsole
 * Uses methods for Connect4
 * @author IanTaylor
 * version 1.0
 *
 */
public class Connect4ComputerPlayer {
	
	/**
	 * move method places a piece on the board,
	 * for the computers turn.
	 * @param game - the actual game object created from Connect4 class
	 */
	public void move(Connect4 game) {
		
		/** Shows whether the move has happened */
		boolean moveIsAccepted = false;
		
		/** the random generated column number*/
		int col = 0;
		
		// Continue looping until a piece has been placed
		while (!moveIsAccepted) {
			
			// Random object called to create and object.
			Random r = new Random();
			
			col = r.nextInt((7-1)+1);
			
			moveIsAccepted = game.checkIfColHasSpace(col);
		}
		
		// add the piece to the board
		game.addPiece(col, game.listOfplayers[1]);
		
		// decrement a piece from the computer.
		game.listOfplayers[1].removePiece();
	}	
}
