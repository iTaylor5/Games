package core;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
//import ui.VariablesForGame;


/**
 * GUI for the Connect4Server 
 * @author Ian Taylor
 * version - 4
 *
 */
public class Connect4Server extends Application implements VariablesForGame {
	
	/** Session numbers */
	private int sessionNo = 1;
	
	/** TextArea */
	TextArea taLog;

	/**
	 * Launches JavaFX
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		taLog = new TextArea();
	
		Scene scene = new Scene(new ScrollPane(taLog), 450, 200);
		primaryStage.setTitle("Connect4 Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8004);
				
				Platform.runLater(() -> taLog.appendText(new Date() +
			          ": Server started at socket 8004\n"));
			  
				while(true) {
					Platform.runLater(() -> taLog.appendText(new Date() +
						": Wait for players to join session " +
						sessionNo + '\n' ));
					
					// Connect player 1
					Socket player1 = serverSocket.accept();
					
					Platform.runLater(() -> { 
						taLog.appendText(new Date() + ": Player 1 joined session " 
							+ sessionNo + '\n');
						taLog.appendText("Player 1's IP address" + 
							player1.getInetAddress().getHostAddress() + '\n');
					});
					
					// Notify that the player is Player 1
					new DataOutputStream(
						player1.getOutputStream()).writeInt(PLAYER1); // Should be Player
					
					// Connect player 2
					Socket player2 = serverSocket.accept();
					
					Platform.runLater(() -> { 
						taLog.appendText(new Date() + ": Player 2 joined session " 
							+ sessionNo + '\n');
						taLog.appendText("Player 2's IP address" + 
							player2.getInetAddress().getHostAddress() + '\n');
					});
					
					// Notify that the player is Player 1
					new DataOutputStream(
						player2.getOutputStream()).writeInt(PLAYER2); // Should be Player
					
					// Display this session and increment session number
					Platform.runLater(() ->
						taLog.appendText(new Date() + 
							": Start a thread for the game session " + sessionNo++ + '\n'));
					
					new Thread(new HandleGameSession(player1, player2)).start();	
				}
				
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
		
	}
	/**
	 * Handles the game session between to players
	 */
	class HandleGameSession implements Runnable {
		/** Socket for player1 */
		private Socket player1;
		/** Socket for player2 */
		private Socket player2;
		
		/** game won or not */
		public boolean won = false;
		
		/** Data Input Stream player 1 */
		private DataInputStream fromPlayer1;
		/** DataOutput Stream player 1 */
	    private DataOutputStream toPlayer1;
	    /** Data Input Stream player 2*/
	    private DataInputStream fromPlayer2;
	    /** Data Input Stream player 2 */
	    private DataOutputStream toPlayer2;
	   
		
		// Create gameBoard
		private char[][] gameBoard = new char[6][7];
		
		/**
		 * Constructor, sets up socket and creates a board.
		 */
		public HandleGameSession(Socket player1, Socket player2) {
			this.player1 = player1;
			this.player2 = player2;
			
			// Initialises the board
			for (int y = 0; y < 6; y++) {
				for (int x = 0; x < 7; x++)
					gameBoard[y][x] = ' ';
			}
		}
		/**
		 * run the thread for the game.
		 */
		@Override
		public void run() {
			try {
				fromPlayer1 = new DataInputStream( 
					player1.getInputStream());
				toPlayer1 = new DataOutputStream(
				    player1.getOutputStream());
				
				fromPlayer2 = new DataInputStream(
				    player2.getInputStream());
				toPlayer2 = new DataOutputStream(
				    player2.getOutputStream());
		        
				// Notify both player Game is beginning
		        toPlayer1.writeInt(CONTINUE);
		        toPlayer2.writeInt(CONTINUE);
		        
		        while (true) {
		        	
		        	// Player 1's move
		        	int row = fromPlayer1.readInt();        
		        	int column = fromPlayer1.readInt();
		        	
		        	// Add piece to servers board
		        	gameBoard[row][column] = 'X';
		        	
		        	// Send move to player 2
		        	sendMove(toPlayer2, row, column);
		        	
		        	// check if won
		            won = checkIfWon(row, column, 'X');

		            if(won) {
		            	
		            	toPlayer1.writeInt(PLAYER1_WON);
		                toPlayer2.writeInt(PLAYER1_WON);
		                break;
		            }
		            //check if board is full
		            else if (isFull()) { 
		                toPlayer1.writeInt(DRAW);
		                toPlayer2.writeInt(DRAW);
		                break;
		            }else {
		            	// Notify player 2 to take the turn
				        toPlayer2.writeInt(CONTINUE);
				        toPlayer1.writeInt(CONTINUE);
		            }

	                // Receive a move from Player 2
		            row = fromPlayer2.readInt();
		            column = fromPlayer2.readInt();
		            
		            // Set token on server board
		            gameBoard[row][column] = '0';
		            sendMove(toPlayer1, row, column);
		            
		            won = checkIfWon(row, column, '0');		            
		            
		            if(won) {
		            	toPlayer1.writeInt(PLAYER2_WON);
		                toPlayer2.writeInt(PLAYER2_WON);
		                break;
		            }
		            else if (isFull()) { // Check if all cells are filled
		                toPlayer1.writeInt(DRAW);
		                toPlayer2.writeInt(DRAW);
		                break;
		            }else {
		            	toPlayer1.writeInt(CONTINUE);
		            	toPlayer2.writeInt(CONTINUE);
		            }
		        }	 
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}	
		}
		
		/** 
		 * Send the move to other player 
		 * */
	    private void sendMove(DataOutputStream out, int row, int column)
	        throws IOException {
	        out.writeInt(row); // Send row index
	        out.writeInt(column); // Send column index
	     
	    }
	    
		/** 
		 * Check if won
		 * @param row, row number
		 * @param col, col number
		 * @param p, players symbol
		 * @return
		 */
		public boolean checkIfWon(int row, int col, char p) {

			// Check Horizontal rows for win
			for (int x = 0; x < 6; x++){
				for (int y = 0; y < 4; y++){
					if (gameBoard[row][y] == p && gameBoard[row][y] == gameBoard[row][y+1] &&
							gameBoard[row][y] == gameBoard[row][y+2] && gameBoard[row][y] == gameBoard[row][y
						+3]){
						won = true;
						return true;
					}
				}
			}
			
			// Check for win vertically
			for (int y  = 0; y < 7; y++){
				for (int x = 0; x < 3; x++){
					if (gameBoard[x][y] == p && gameBoard[x][y] == gameBoard[x+1][y] &&
							gameBoard[x][y] == gameBoard[x+2][y] && gameBoard[x][y] == gameBoard[x+3][y]){
						won = true;
						return true;
					}
				}
			}
		
			// Check for win diagonally, downwards
			for (int x = 0; x < gameBoard.length - 3; x++){
				for (int y = 0; y < gameBoard[row].length - 3; y++){
					if (gameBoard[x][y] == p 
							&& gameBoard[x][y] == gameBoard[x+1][y+1] 
							&& gameBoard[x][y] == gameBoard[x+2][y+2] 
							&& gameBoard[x][y] == gameBoard[x+3][y+3]){
						won = true;
						return true;
					}
				}
			}

			// Check for win diagonally, from left right upwards
			for (int x = 0; x < gameBoard.length - 3; x++){
				for (int y = 3; y < gameBoard[x].length; y++){
					if (gameBoard[x][y] == p
							&& gameBoard[x][y] == gameBoard[x+1][y-1] 
							&& gameBoard[x][y] == gameBoard[x+2][y-2] 
							&& gameBoard[x][y] == gameBoard[x+3][y-3]){
						
						won = true;
							return true;
						}
					}
				}
			return false;
		}
		
		/** Determine if the cells are all occupied */
		private boolean isFull() {

		      for (int i = 0; i < 6; i++)
		        for (int j = 0; j < 7; j++)
		          if (gameBoard[i][j] == ' ')
		            return false; // At least one cell is not filled
		  
		      // All cells are filled
		      return true;
		}
		
		/** print the game board */	
		public void printBoard() {
			int y, x;
			
			System.out.println("* Server Board *");
			for (y = 0; y < 6; y++) {
				System.out.print("|");
		            for (x = 0; x < 7; x++)
		                System.out.print(gameBoard[y][x] + "|");
		            System.out.println();
		    }		
		}	
	}
	/**
	 * Exit server
	 */
	@Override
	public void stop() {

		Platform.exit();
        System.exit(0);
	}
	/**
	 * Main method for launch 
	 * @param args, to run main
	 */
	public static void main(String[] args) {
		
		
		launch(args);
	}
}