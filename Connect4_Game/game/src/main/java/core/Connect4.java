package core;

import javafx.scene.paint.Color;

/**
 * Connect4 class holds all the game logic for connect4.
 * 
 * @author IanTaylor
 * @version 2.0
 * 
 * This class uses the Player class in this project
 * to create a player object for each player.
 *
 */
public class Connect4 {
	
	/** boolean - if the a player has four in row */
	public boolean won = false; 
	
	/** 2D array of chars which creates the board */
	public char board[][];
	
	/** An array of the players */
	public Player [] listOfplayers;
	
	/** 
	 * Constructor for the Connect4 game. 
	 * 
	 * Creates the board of 6 rows and 7 columns 
	 * and initialises each piece to ' '.
	 * 
	 */
	public Connect4() {
		
		board = new char[6][7]; // Creates the board
		
		// Initialises the board
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 7; x++)
				board[y][x] = ' ';
		}	
	}
	/**
	 * addPlayers is use to create the player objects.
	 * 
	 * @param choice - is whether to initiate a computer opponent
	 * or another player.
	 */
	public void addPlayer(char choice) {
		
		// List storing the players
		listOfplayers = new Player [2];
		
		// Player 1
		Player playerX = new Player("Player X", 'X', Color.RED);
		listOfplayers[0] = playerX;
		playerX.myTurn = true;
		
		if(choice == 'P') {
			// Initialise second player
			Player player0 = new Player("Player 0",'0', Color.BLUE);
			listOfplayers[1] = player0;
			player0.myTurn = false;
			
		}else {
			Player comp = new Player("Computer",'0', Color.BLUE);
			listOfplayers[1] = comp;
			comp.myTurn = false;
			System.out.println("Comp player is added");
		}
	}
	
	/** 
	 * addPiece - Adds a piece to the gameboard[][]
	 * First finds an empty spot then adds piece.
	 * Verifies if game has been won or not
	 * @author IanTaylor
	 * @param col - User's selected column
	 * @param p Player who's turn it is
	 * @return true if piece was add, else false.
	 * 
	 */
	public boolean addPiece(int col, Player p) {
	
		// Starts at the bottom of the board looking for an empty spot.
		for (int x = 5; x >= 0; x--) {
			if(board[x][col] == ' ') {
				
				board[x][col] = p.symbol; // adds the players symbol to board
				//System.out.println("IN addPiece + " + "x = " + x + "col = " + col);
				// Checks if the game has been won
				if(checkIfWon(x, col, p)) {
					won = true;
				}
				//System.out.println("In add piece and col = " + col + " row is " + x);
				return true;	
			}
		}
		return false;
	}
	/**
	 * checkIfColHasSpace - checks if the users select column is valid.
	 * Once the column is valid it checks if the column has space for a piece.
	 * Returns true if this is the case.
	 * @author IanTaylor
	 * @param col Users selected column 
	 * @return true if valid otherwise false
	 */
	public boolean checkIfColHasSpace(int col) {
		
		// whether space is available
		boolean isSpace = false;
		
		// First checks users chosen column is within the board parameters
		if(col > 6 || col < 0) {
			System.out.println("Not a valid move.");
			return false;
		}
		
		// Checks if the column has space for a piece
		for (int x = 5; x >= 0; x--) {
			if(board[x][col] == ' ')
				isSpace = true;
		}
		// Displays result
		if( isSpace == false) {
			System.out.println("Column is full");
			return false;
		}
		
		return true;
	}
	/**
	 * checkIfWon method checks if the game has been won.
	 * It checks horizontally first, vertically second and diagonals third
	 * @param row - The current row whether the piece was added
	 * @param col - The current column the piece was added
	 * @param p - The current player
	 * @return True if won, otherwise false
	 */
	public boolean checkIfWon(int row, int col, Player p) {

		// Check Horizontal rows for win
		for (int x = 0; x < 6; x++){
			for (int y = 0; y < 4; y++){
				if (board[row][y] == p.symbol && board[row][y] == board[row][y+1] &&
					board[row][y] == board[row][y+2] && board[row][y] == board[row][y
					+3]){
					won = true;
					return true;
				}
			}
		}
		
		// Check for win vertically
		for (int y  = 0; y < 7; y++){
			for (int x = 0; x < 3; x++){
				if (board[x][y] == p.symbol && board[x][y] == board[x+1][y] &&
				board[x][y] == board[x+2][y] && board[x][y] == board[x+3][y]){
					won = true;
					return true;
				}
			}
		}
	
		// Check for win diagonally, downwards
		for (int x = 0; x < board.length - 3; x++){
			for (int y = 0; y < board[row].length - 3; y++){
				if (board[x][y] == p.symbol 
						&& board[x][y] == board[x+1][y+1] 
						&& board[x][y] == board[x+2][y+2] 
						&& board[x][y] == board[x+3][y+3]){
					won = true;
					return true;
				}
			}
		}

		// Check for win diagonally, from left right upwards
		for (int x = 0; x < board.length - 3; x++){
			for (int y = 3; y < board[x].length; y++){
				if (board[x][y] == p.symbol 
						&& board[x][y] == board[x+1][y-1] 
						&& board[x][y] == board[x+2][y-2] 
						&& board[x][y] == board[x+3][y-3]){
					
					won = true;
					return true;
					}
				}
			}
		return false;
	}
	
	/**
	 * @author IanTaylor
	 * Prints the board to console
	 */
	public void printBoard() {
		int y, x;
		
		System.out.println("* Connect4Game *");
		for (y = 0; y < 6; y++) {
			System.out.print("|");
	            for (x = 0; x < 7; x++)
	                System.out.print(board[y][x] + "|");
	            System.out.println();
	    }		
	}
}
