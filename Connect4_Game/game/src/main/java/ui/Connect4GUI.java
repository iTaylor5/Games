package ui;

import java.util.Random;
import java.util.Scanner;
import core.Connect4;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * GUI for the Connect4 Game
 * @author Ian Taylor
 * version - 3
 *
 */
public class Connect4GUI extends Application{
	
	/** Variable containing the connect4 game logic*/
	private Connect4 game;
	
	/** rotates the players turn */
	private int whosTurn = 0;
	
	/** Holds what type of game the player to to initiate */
	public char gameType;
	
	public GridPane gridPane;

	/**
	 * First checks whether the users would like to play the computer 
	 * or another player. 
	 * Then launches the FX application
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		game = new Connect4();
		
		// Add either another player or a computer opposition
		game.addPlayer(gameType);
		
		stage.setTitle("Connect4 Game");
		
		gridPane = new GridPane();
		
        BorderPane rootPane = new BorderPane();
        
        GridPane gridPane = createGrid();
        //grid();
        
        rootPane.setCenter(gridPane);
        
        rootPane.setStyle("-fx-background-color: CADETBLUE");
        
        Scene scene = new Scene(rootPane, 475, 475);
        
        scene.setFill(Color.LIGHTGREY);
        
        stage.setScene(scene);
        
        stage.show();

	}
	/**
	 * Launches the FX application.
	 * @param args from main to run game.
	 * @param opponentType is the game type
	 */
	public void initialGame(String [] args, char opponentType) {
		
		gameType = opponentType;
		
		Application.launch(args);	
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * Exits the program
	 */
	@ Override
	public void stop() {
	    System.exit(0);
	}
	/**
	 * Create a grid of the board game.
	 * @return a gridPane of the game
	 */
	public GridPane createGrid() {
		
		GridPane gridPane = new GridPane();
	
        for (int y = 2; y < 9; y++) {    	
     		for (int x = 2; x < 8; x++) {
     			
     			gridPane.setHgap(5);
            	gridPane.setVgap(5);
     			Circle cir = new Circle(30);

     			GridPane.setColumnIndex(cir, y);
     			GridPane.setRowIndex(cir, x);
     			
     			gridPane.add(cir, y, x);

     			cir.setFill(Color.LIGHTSTEELBLUE);
		
     			cir.setOnMouseClicked(e -> {

     				if ("Computer" == game.listOfplayers[1].name) {
 
                        boolean roundComplete = false;
                        
                        while(!roundComplete) {
                            
                            boolean pieceAdded = false;
                            boolean acceptMove = false;
                        
                            
                            int colIndex = GridPane.getColumnIndex(cir)-2;
                            int rowIndex = GridPane.getRowIndex(cir)-2;
                            
                            System.out.println("Before -2=");
                            System.out.println("Colum = " + colIndex+2 + "rowIndex" + rowIndex+2);
                         
                            
                            acceptMove = addPieceGUI(colIndex, rowIndex);
                            
                            if(acceptMove) {
                            	while(!pieceAdded) {
                            		pieceAdded = game.addPiece(colIndex, game.listOfplayers[0]);
                            	}
                            	System.out.println("Col = " + colIndex + "row " + rowIndex);
                            	cir.setFill(Color.WHITE);
                            }else {
                            	break;
                            }
                        
                            game.checkIfWon(rowIndex, colIndex, game.listOfplayers[0]); 
                            
                            boolean CmpMoveAccepted = false;
                            
                            while (!CmpMoveAccepted) {
                            	CmpMoveAccepted = addComputersMove(gridPane);
                            }
                            // Need to remove
                            game.printBoard();
                            game.checkIfWon(rowIndex, colIndex, game.listOfplayers[1]);
                                           
                            roundComplete = true;	
                        }
     				}else {
                        boolean pieceAdded = false;
                        boolean player = false;
                        boolean acceptMove = false;
                        
                        int colIndex = GridPane.getColumnIndex(cir)-2;
                        int rowIndex = GridPane.getRowIndex(cir)-2;
                        
                        if (whosTurn == 0) {
                        	
                        	acceptMove = addPieceGUI(colIndex, rowIndex);
                            
                        	while(!player) {
                        		if(acceptMove) {
                                	while(!pieceAdded) {
                                		pieceAdded = game.addPiece(colIndex, game.listOfplayers[0]);
                                	}
                                	whosTurn = 1;
                                	cir.setFill(Color.RED);
                                	player = true;
                                }else {
                                	break;
                                }
                        	}
                        	acceptMove = false;
                        	pieceAdded = false;
                        	player = false;
                            game.checkIfWon(rowIndex, colIndex, game.listOfplayers[0]);

                        }else {
                        	
                        	acceptMove = addPieceGUI(colIndex, rowIndex);
                            
                        	while(!player) {
                        		if(acceptMove) {
                                	while(!pieceAdded) {
                                		pieceAdded = game.addPiece(colIndex, game.listOfplayers[1]);
                                	}
                                	whosTurn = 0;
                                	cir.setFill(Color.BLUE);
                                	player = true;
                                }else {
                                	break;
                                }
                        	}
                            game.checkIfWon(rowIndex, colIndex, game.listOfplayers[1]);
                        }               
     				}
     				if (game.won) {
     					PopUp pop = new PopUp();
     					pop.display();
                        stop();
                    }
     			});
     		}
     	}
        return gridPane;
	}
	/**
	 * Checks whether the move chosen by a player can be accepted.
	 * @param colIndex, the column index the selected from gridPane.
	 * @param rowIndex, the row index they selected from gridPane.
	 * @return true or false on whether its an accepted move.
	 */
	public boolean addPieceGUI (int colIndex, int rowIndex){
		// Starts at the bottom of the board looking for an empty spot.
		
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
	 * Makes a move for the computer.
	 * 
	 * @param pane - GridPane storing the board
	 * @return A populated gridPane.
	 */
	public boolean addComputersMove(GridPane pane) {
		
		int col = 0;
		
		boolean noValidMove = false;
		
		while(!noValidMove) {
			
			// Random object called to create and object.
			Random r = new Random();
			col = r.nextInt((7-1)+1);
			noValidMove = game.checkIfColHasSpace(col);
		}
		for (int x = 5; x >= 0; x--) {
			if(game.board[x][col] == ' ') {
					
			game.addPiece(col, game.listOfplayers[1]);
					
			x = x+2;
			col = col +2;
					
			Circle cir = new Circle(30);
	     	GridPane.setColumnIndex(cir, x);
	     	GridPane.setRowIndex(cir, col);
	     
	     	cir.setFill(Color.BLACK);
	     			
			pane.add(cir, col, x);		
					
			return true;
			}		
		}
		return true;
	}
	/**
	 * Create a pop up window displaying a winner.
	 * @author Ian Taylor
	 *
	 */
	public class PopUp{
		
		public void display() {
		Stage popupwindow=new Stage();
		      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("CONGRATULATIONS");
		      
		Label label1= new Label("THERE IS A WINNER");
		      
		Button button1= new Button("Close this pop up window");
		     
		button1.setOnAction(e -> popupwindow.close());
		     
		VBox layout= new VBox(10);
		         
		layout.getChildren().addAll(label1, button1);
		      
		layout.setAlignment(Pos.CENTER);
		      
		Scene scene1= new Scene(layout, 300, 250);
		      
		popupwindow.setScene(scene1);
		      
		popupwindow.showAndWait();
		       
		}

	}
	
}