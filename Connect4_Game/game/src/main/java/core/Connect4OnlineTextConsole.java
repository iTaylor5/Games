package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import core.Connect4;
import javafx.application.Platform;
/**
 * Console game for online for the Connect4 Game
 * @author Ian Taylor
 * version - 4
 *
 */
public class Connect4OnlineTextConsole implements VariablesForGame {
	
	/** Input streams from */
	private DataInputStream fromServer;
	
	/** Output streams from */
	private DataOutputStream toServer;
	
	/** Player 1 or Player 2*/
	int player = 0;
	
	/** Whether to continue waiting for move*/
	private boolean waiting = true;
	
	/** Continue to play */
	private boolean continueToPlay = true;
	
	/** Variable containing the connect4 game logic*/
	private Connect4 game;
	
	/** Host name */
	private String host = "localhost";
	
	/** My turn */
	private boolean myTurn = false;
	
	/** Indicate the token for the player */
	private char myToken = ' ';
	
	/** Indicate the token for the other player */
	private char otherToken = ' ';
	
	/** Indicate selected row */
	private int rowSelected;
	/** Indicate column by the current move */
	private int columnSelected;
	
	/** */
	Connect4OnlineTextConsole consoleGame;
	
	
	/** 
	 * Constructor, creates a game object
	 */
	public Connect4OnlineTextConsole() {
		game = new Connect4();
		game.addPlayer('P');
		
	}
	
	/**
	 * Main method
	 * @param args, to run main
	 */
	public static void main(String[] args) {
		Connect4OnlineTextConsole consoleGame = new Connect4OnlineTextConsole();
		consoleGame.connectToServer();
	}
	/**
	 * Connect to a server
	 */
	private void connectToServer() {
		
		try {
			System.out.println("Connecting to server");
			// Create a socket to connect to the server
			Socket socket = new Socket(host, 8004);

		    // Create an input and output stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Connected");
		}
		    
		catch (Exception ex) {
		   	System.out.println("Failed to connect");
		}
		new Thread(() -> {
			try {
				// Notify what player number they are
		        player = fromServer.readInt();
		        	
		        // Am I player 1 or 2?
		        if (player == PLAYER1) {
		            myToken = 'X';
		            otherToken = '0';
		            System.out.println("Player 1 your tokens are 'X'");
		            System.out.println("Waiting for player 2 to join");
		        
		        }else if (player == PLAYER2) {

		            myToken = '0';
		            otherToken = 'X';
		            System.out.println("Player 2 your tokens are 'O'");
		            System.out.println("Waiting for player 1 to make a move.");
		      
		        }
		        while (continueToPlay) {

		            // Beginning Game
					Integer data;
						
					// Received CONTINUE to start, or 
					// specify who won or if there is a draw.
					data = fromServer.readInt();
		            
		            if (data == CONTINUE) {
		            	
		            	System.out.println("Game beginning");
		                
		                game.listOfplayers[0].myTurn = true;
		                game.listOfplayers[1].myTurn = false;
		            
		                game.printBoard();
		                	
		                if (player == PLAYER1) {
		                	        		
		                	System.out.println("Player 1 it is your turn...");
		                	
		                	int col = 0;
									
							boolean played = false;
									
							boolean validMove = false; // Confirm if move is valid
			    		    		
				    		while(!validMove) {
				    			
				    		    col = inputCol()-1;
				    		    		
				    		    validMove = game.checkIfColHasSpace(col);
				    		    		
				    		    columnSelected = getRow(col);
				    		    rowSelected = col;
				    		    		
				    		    played = game.addPiece(col, game.listOfplayers[0]); // adds pieces
			    		    			
				    		    game.listOfplayers[0].removePiece();
				    		    		
				    		   	System.out.println("Sending move");
				    		    		
				    		   	sendMove();
				    		
				    		}
				    		game.listOfplayers[0].myTurn = false;
				    		
				    		int data2 = fromServer.readInt();
				    		
				    		if(data2 == CONTINUE) {
				    			System.out.println("Waiting for player 2's move...");
				    			int row = fromServer.readInt();
								int column = fromServer.readInt();
									
									// Add player2's move to clients board
								game.board[row][column] = '0';
								
								game.printBoard();
				    		}else if (data2 == PLAYER1_WON){
				    			System.out.println("CONGRATULATIONS! You have won.");
				    		}
				    		else if (data2 == DRAW) {
				    			System.out.println("Its a draw");
				    		}
								
		                } else if (player == PLAYER2) {
		                	
							int col = 0;
				            System.out.println("Waiting for player 1's move");
			                
				            // Receive Player1's move
							int row = fromServer.readInt();
							int column = fromServer.readInt();	
							
							// Add Player1's move to server
							game.board[row][column] = 'X';
								
							game.printBoard();
								
							// Server notify players if game should continue,
							// or if there has been a win.
							int data2 = fromServer.readInt();
								
							if(data2 == CONTINUE) {
								game.listOfplayers[1].myTurn = true;
								System.out.println("Your turn Player 2");
									
								boolean validMove = false; // Confirm if move is valid
											
					    	    while(!validMove) {
					   		    	// Input from user & check its a valid column number
					    			col = inputCol()-1;
					    						
					    			validMove = game.checkIfColHasSpace(col);
					    				
					    			columnSelected = getRow(col);
					    				
					   			    rowSelected = col;
					    					
					    			game.addPiece(col, game.listOfplayers[1]); // adds pieces
					    		    		
					    		   	game.listOfplayers[1].removePiece();
			
									sendMove();
									
					    	    }
					    	    
					    	    game.listOfplayers[1].myTurn = false;
					    	    
							}else if (data2 == PLAYER1_WON) {
									System.out.println("Sorry you lost!");
							}
		                }
		            } else if (data == PLAYER2_WON) {
		            	if (player == PLAYER1) {
		            		System.out.println("Your Opponent has won. Better luck next time");
		            	}else {
		            		System.out.println("CONGRATULATIONS! You have won.");
		            	}
		            }else if (data == PLAYER1_WON) {
		            	if (player == PLAYER1) {
		            		System.out.println("CONGRATULATIONS! You have won.");
		            	}else {
		            		System.out.println("Your Opponent has won. Better luck next time");
		            	}
		            }else if (data == DRAW) {
						if (player == PLAYER1) {
							System.out.println("Game is over. It is a draw");
						} else {
							System.out.println("Game is over. It is a draw");
						}
					}
			       game.printBoard();
			       System.out.println("GAME ENDED");
			    }
		        
		        }catch(Exception ex) {
		        	ex.printStackTrace();
		        }
		    }).start();
		
	}
	/**
	 * Get which row the token end up in
	 * @param col, what column was selected
	 * @return, the row number
	 */
	public int getRow(int col) {
		int row =0;
		
		// Starts at the bottom of the board looking for an empty spot.
		for (int x = 5; x >= 0; x--) {
			if(game.board[x][col] == ' ') {
						
				return row = x;
			}	
		}
		return -1;

	}
	/**
	 * Send move to server
	 * @throws IOException
	 */
	private void sendMove() throws IOException {
		
			toServer.writeInt(columnSelected);
			toServer.writeInt(rowSelected);	
    }
	/**
	 * Use as a try catch to make sure player enters correct 
	 * column position
	 * @return, column number
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
