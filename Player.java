/************************************************************
* Player.java
* By Matthew McNierney
* Written for CS5 Lab #1
* 
* Stores information about the players in a game of Chips.
************************************************************/

public class Player {
	
	private String myName;	// Player's name
	private int myChips;		// Player's number of chips
	
	/**********************************************************
	* Player constructor
	* Constructs a player with instance variables for the
	* player's name (passed to the constructor as a parameter)
	* and the player's initial number of chips (0).
	**********************************************************/
	public Player(String playerName) {
		myName = playerName;
		myChips = 0;
	}
	
	/**********************************************************
	* getName()
	* Returns player's name as a String object.
	**********************************************************/
	public String getName() {
		return myName;
	}
	
	/**********************************************************
	* addChips(numChips)
	* Adds chips to the player's total number of chips. Takes
	* the number of chips to add as a parameter.
	**********************************************************/
	public void addChips(int numChips) {
		myChips += numChips;
	}
	
	/**********************************************************
	* chips()
	* Returns the number of chips the player current has.
	**********************************************************/
	public int chips() {
		return myChips;
	}
}