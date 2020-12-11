package core;

import javafx.scene.paint.Color;

/**
 * Player is a class for each player.
 * Has a variable for their name
 * Amount of pieces their start off with
 * @author IanTaylor
 * @version 2.0
 *
 */
public class Player {
	/** Name for the player*/
	public String name;
	
	/** Number of pieces*/
	private int pieces;
	
	/** The players symbol*/
	public char symbol;
	
	public Color symbolCol;
	
	public boolean myTurn;
	
	/**
	 * Constructor initialises 
	 * @param name Name of the player
	 * @param s Symbol for the player
	 * @param color is for users chip pieces coloring
	 */
	public Player (String name, char s, Color color) {
		this.symbol = s;
		pieces = 21;
		this.name = name;
		
		this.symbolCol = color;
	
	}
	
	/** Gets the number of number of pieces left
	 * 
	 * @return - number of pieces player has left
	 */
	public int piecesLeft() {
		
		return pieces;
	}
	/** Decrements the number of pieces*/
	public void removePiece() {
		pieces--;
	}
	/**
	 * toString method overridden to just print players name
	 */
	public String toString() {
		return name;
	}
}
