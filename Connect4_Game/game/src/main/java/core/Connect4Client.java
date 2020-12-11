package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.VariablesForGame;


/**
 * GUI for the client playing through the server
 * for the Connect4 Game
 * @author Ian Taylor
 * version - 5
 *
 */
public class Connect4Client extends Application implements VariablesForGame {

	/** overallPane used to hold components on the stage */
	BorderPane overalPane;
	
	/** Used to hold the board*/
	public GridPane gridPane;
	
	/** Text area used to display info to players */
	TextArea taLog;
	
	/** The game logic, board and players info */
	private Connect4 clientGame;
	
	/** Input streams from/to server */
	private DataInputStream fromServer;
	
	/** Output streams from/to server */
	private DataOutputStream toServer;
	
	/** Host name or ip */
	private String host = "localhost";
	
	/** Player number */
	int playerNum;
	
	/** Continue to play */
	private boolean continueToPlay = true;
	
	
	/** Constructor initialises the Connect4 game logic and adds a player */
	public Connect4Client() {
		clientGame = new Connect4();
		clientGame.addPlayer('P'); // ON-SERVER IS ONLY GEARED TO 2 PLAYER
	}

	/** FX stage */
	@Override
	public void start(Stage stage) throws Exception {
		
		// Main pane holding all components */
		overalPane = new BorderPane();
		
		stage.setTitle("Connect4 Game");
		
		// Text area to display players info */
		taLog = new TextArea();
		
		taLog.setText("Welcome");
		
		taLog.setPrefHeight(70);
		
		taLog.setStyle("-fx-control-inner-background: LIGHTSTEELBLUE");
		
		overalPane.setBottom(taLog);
		
		// Stores the board, using createGrid()
		gridPane = createGrid();
		
		overalPane.setCenter(gridPane);
		
		Scene scene = new Scene(overalPane, 475, 475);
        
		overalPane.setStyle("-fx-background-color: CADETBLUE");
        
        stage.setScene(scene);
        
        stage.show();
		
        // Connects to server and runs the game
        new Thread(() -> {  
        	connectToServer();
        }).start();
		
	}
	/**
	 * Connects to the server.
	 * Waits for the second player to join,
	 * then begins the game between the 2
	 * of them.
	 */
	private void connectToServer() {
		
		try {
			
			Socket socket = new Socket(host, 8004);
			toServer = new DataOutputStream(socket.getOutputStream());
			fromServer = new DataInputStream(socket.getInputStream());
	
		} catch(Exception ex) { 
			System.out.println("Unable to connect to server");
		}
		
		// Main game thread.
		new Thread(() ->{
			try{
				// Notify what player number they are
				playerNum = fromServer.readInt();
				
				if (playerNum == PLAYER1) {
					Platform.runLater(() -> {
						taLog.appendText("Connected!"
								+ "You are player 1. Waiting for player 2 to join");
					});
				}else if (playerNum == PLAYER2) {
					Platform.runLater(() -> {
						taLog.appendText("Connected!"
								+ "You are player 2.");
					});
				}
				
				// This is the game loop
				while(continueToPlay) {				
					
					// Beginning Game
					Integer data;
					
					// Received CONTINUE to start, or 
					// specify who won or if there is a draw.
					data = fromServer.readInt();
					
					if (data == CONTINUE) {
						
						clientGame.listOfplayers[0].myTurn = true;
						clientGame.listOfplayers[1].myTurn = false;
						
						if (playerNum == PLAYER1) {
							Platform.runLater(() -> {
								taLog.setText("Its your turn...");
							});
							
							int data2 = fromServer.readInt();
							
							// After move server will check for a win
							if(data2 == CONTINUE) {
								Platform.runLater(() -> {
									taLog.setText("Move sent waiting for Player2's move");
								});
								// Receive player 2's move
								int row = fromServer.readInt();
								int column = fromServer.readInt();
								
								// Add player2's move to clients board
								clientGame.board[row][column] = '0';
								
								Platform.runLater(() -> {
									taLog.setText("Move received... Your turn");	
									
									// Update the GUI to show move
									GridPane gridPane2 = new GridPane();
					       			gridPane2 = createGrid();
					            	overalPane.setCenter(gridPane2);
								});
								
							} else if (data2 == PLAYER1_WON){
									Platform.runLater(() -> {
										taLog.setText("You have won.");
										Platform.runLater(() -> {
											taLog.setText("You have won.");
											PopUp pop = new PopUp("CONGRATULATIONS", "You have won." );
					     					pop.display();
										});
									});
									
							} else if (data2 == DRAW) {
								Platform.runLater(() -> 
								taLog.setText("Game is over. It is a Draw!"));
							}
						} else {
							
							Platform.runLater(() -> {
								taLog.setText("Waiting for player 1 to move...");												
							});
							try {
								// Receive Player1's move
								int row = fromServer.readInt();
								int column = fromServer.readInt();
								
								// Add Player1's move to server
								clientGame.board[row][column] = 'X';
								
								Platform.runLater(() -> {
									taLog.setText("Move received...");	
									
									// Update GUI to show move
									GridPane gridPane2 = new GridPane(); //gridPane
			            			gridPane2 = createGrid();
			            			overalPane.setCenter(gridPane2);
								});
								
								// Server notify players if game should continue,
								// or if there has been a win.
								int data2 = fromServer.readInt();
							
								if(data2 == CONTINUE) {
									clientGame.listOfplayers[1].myTurn = true;			
									Platform.runLater(() -> 
										taLog.appendText("\nYour turn..."));
									
								} else if (data2 == PLAYER1_WON) {
									Platform.runLater(() -> 
									taLog.setText("Your Opponent has won. Better luck next time"));
									Platform.runLater(() -> {
										taLog.setText("Your Opponent has won. Better luck next time");
										PopUp pop = new PopUp("DEFEAT", "Your oppenent has one!");
				     					pop.display();
									});	
								}
				
							} catch (IOException e) {
								System.out.println("Failed to receive from server");
							}
						}

					} else if (data == PLAYER2_WON) {
						if (playerNum == PLAYER1) {
							Platform.runLater(() -> {
								taLog.setText("Your Opponent has won. Better luck next time");
								PopUp pop = new PopUp("DEFEAT", "Your oppenent has one");
		     					pop.display();
							});	
						} else {
							Platform.runLater(() -> {
								taLog.setText("You have won.");
								PopUp pop = new PopUp("CONGRATULATIONS", "You have won." );
		     					pop.display();
							});
						}
						
					} else if (data == PLAYER1_WON) {
						if (playerNum == PLAYER1) {
							Platform.runLater(() -> {
								taLog.setText("You have won.");
								PopUp pop = new PopUp("CONGRATULATIONS", "You have won." );
		     					pop.display();
							});
							
						} else {
							Platform.runLater(() -> {
								taLog.setText("Your Opponent has won. Better luck next time");
								PopUp pop = new PopUp("DEFEAT", "Your oppenent has one");
		     					pop.display();
							});	
						}
						
					}else if (data == DRAW) {
						if (playerNum == PLAYER1) {
							Platform.runLater(() -> 
							taLog.setText("Game is over. It is a draw"));
						} else {
							Platform.runLater(() -> 
							taLog.setText("Game is over. It is a draw"));
						}
					}
				}	
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}		
		}).start();		
	}
	/**
	 * Creates the board and adds listener to the circles,
	 * Carries out move if require otherwise ignores event.
	 * @return the game board as a GridPane
	 */
	public GridPane createGrid() {
		
		GridPane guiBoard = new GridPane();
		
		for (int y = 2; y < 9; y++) {    	
     		for (int x = 2; x < 8; x++) {
     			
     			guiBoard.setHgap(5);
     			guiBoard.setVgap(5);
     			Circle cir = new Circle(30);

     			GridPane.setColumnIndex(cir, y);
     			GridPane.setRowIndex(cir, x);
     			
     			guiBoard.add(cir, y, x);
     			
     			int colIndex = GridPane.getColumnIndex(cir)-2;
                int rowIndex = GridPane.getRowIndex(cir)-2;

     			if(clientGame.board[rowIndex][colIndex] == 'X')
     				cir.setFill(Color.TOMATO);
     			else if (clientGame.board[rowIndex][colIndex] == '0')
     				cir.setFill(Color.STEELBLUE);
     			else
     				cir.setFill(Color.LIGHTSTEELBLUE);
     			
     			cir.setOnMouseClicked(e -> {
     				
                    boolean pieceAdded = false;
                    boolean acceptMove = false;
                    
                    if (playerNum == PLAYER1 && clientGame.listOfplayers[0].myTurn) {
                    	
                    	acceptMove = addPieceGUI(clientGame, colIndex, rowIndex);
                        	
                        if(acceptMove) {
                        		
                        	pieceAdded = clientGame.addPiece(colIndex, clientGame.listOfplayers[0]);
                        		
                        	if (pieceAdded) {
                        		try {
        							toServer.writeInt(rowIndex);
        							toServer.writeInt(colIndex);
        						} catch (IOException ex) {
        							System.out.println("Failed to send to server");
        						}
                        		Platform.runLater(() -> {
                        				
            						taLog.setText("Move Sent");
                        		});
                        		GridPane gridPane2 = new GridPane(); //gridPane
                        			
                        		gridPane2 = createGrid();
                        		
                        		overalPane.setCenter(gridPane2);
                        			
                        		clientGame.listOfplayers[0].myTurn = false;
                        	}
                        }

                    } else if (playerNum == PLAYER2 && clientGame.listOfplayers[1].myTurn) {
                    	
                    	acceptMove = addPieceGUI(clientGame, colIndex, rowIndex);
                    	
                    	if(acceptMove) {
                    		
                    		pieceAdded = clientGame.addPiece(colIndex, clientGame.listOfplayers[1]);
                    		
                    		if(pieceAdded) {
                    			try {
    								toServer.writeInt(rowIndex);
    								toServer.writeInt(colIndex);
    							} catch (IOException ex) {
    								System.out.println("Failed to send to server");
    							}
                    			Platform.runLater(() -> {
                    				GridPane gridPane2 = new GridPane(); //gridPane
                        			
                        			gridPane2 = createGrid();
                        			
                        			overalPane.setCenter(gridPane2);
        							
                        			taLog.setText("Move Sent");
        							
        						});
                    			clientGame.listOfplayers[1].myTurn = false;
                    		}             		
                    	}	
                    } 
     			});   			
	
     		}
		}
		return guiBoard;
	}
	/**
	 * Makes sure the circle the player selected is a possible move
	 * @param game the connect 4 game
	 * @param colIndex the column
	 * @param rowIndex the row
	 * @return true or false depending if move is acceptable
	 */
	public boolean addPieceGUI (Connect4 game, int colIndex, int rowIndex){
		
			if(rowIndex == 5) {
				return true;
			}
			if(game.board[rowIndex+1][colIndex] != ' ') {
				//System.out.println();
				return true;
			} else
				return false;
	}
	/**
	 * Launches the FX application.
	 * @param args from main to run game.
	 */
	public void initialGame(String [] args) {
		
		//Application.launch(args);
		
		
	}
	
//	/** Used for starting with out going through the options */
//	public static void main(String[] args) {
//			
//			launch(args);
//	}
	/**
	 * Exit the game
	 */
	@Override
	public void stop() {
		Platform.exit();
        System.exit(0);
	}
	/**
	 * Create a pop up window displaying a winner.
	 * @author Ian Taylor
	 *
	 */
	public class PopUp{
		
		String toBeDisplayed;
		String title;
		
		public PopUp(String title, String display) {
			toBeDisplayed = display;
			this.title = title;
		}
		
		public void display() {
		Stage popupwindow=new Stage();
		      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle(title);
		      
		Label label1= new Label(toBeDisplayed);
		      
		Button button1= new Button("Close this pop up window");
		     
		button1.setOnAction(e -> {
			popupwindow.close();
			stop();
		});
		     
		VBox layout= new VBox(10);
		         
		layout.getChildren().addAll(label1, button1);
		      
		layout.setAlignment(Pos.CENTER);
		
		layout.setStyle("-fx-background-color: CADETBLUE");
		      
		Scene scene1= new Scene(layout, 300, 250);
		      
		popupwindow.setScene(scene1);
		      
		popupwindow.showAndWait();
		       
		}
	}
		
}