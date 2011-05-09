/************************************************************
* Referee.java
* By Matthew McNierney
* Written for CS5 Lab #1
* 
* Keeps track of a game of Chips.
************************************************************/

import java.util.Scanner;

public class Referee {
	private int gameType;			// Index for the game's rules
	private int chipsInPile;	// # of chips remaining
	private int maxChips;			// max # of chips possible to take
	private int turn;					// 0 or 1 depending on turn
	private Player p1;				// Ref to Player 1 object
	private Player p2;				// Ref to Player 2 object
	
	Scanner input = new Scanner(System.in);
	
	/**********************************************************
	* Referee(p1Name,p2Name,maxChipsInPile,gameRules)
	* Referee Constructor
	* Creates Referee object, based on parameters for the
	* players' names, the initial number of chips in the pile,
	* and the game's rules.
	**********************************************************/
	public Referee(String p1Name,String p2Name,int maxChipsInPile,int gameRules) {
		gameType = gameRules;
		chipsInPile = maxChipsInPile;
		
		if (gameType == 2)
			maxChips = maxChipsInPile - 1;
		else
			maxChips = maxChipsInPile / 2;
		
		turn = 0;
		p1 = new Player(p1Name);
		p2 = new Player(p2Name);
	}
	
	/**********************************************************
	* informPlayerOfState()
	* Tells the player how many chips both players have, whose
	* move it is, and how many chips are in the pile.
	**********************************************************/
	public void informPlayerOfState() {
		System.out.println();
		System.out.println(p1.getName() + " has " + p1.chips()
				+ " chip" + ((p1.chips() == 1) ? "" : "s") + ".");
		System.out.println(p2.getName() + " has " + p2.chips()
				+ " chip" + ((p2.chips() == 1) ? "" : "s") + ".");
		System.out.println(currentPlayer().getName() + ", it's your move.");
		System.out.println("There " + ((chipsInPile==1) ? "is 1 chip" : "are "
			+ chipsInPile + " chips") + " in the pile.");
	}
	
	/**********************************************************
	* getNumberOfChips()
	* Tells the player how many chips they can take, and then
	* prompts them to enter a number of chips to take.
	* Returns the number of chips that the player chooses to
	* take.
	**********************************************************/
	public int getNumberOfChips() {
		System.out.println("You can take "
				+ ((maxChips==1) ? (maxChips + " chip.") : ("between 1 and "
				+ maxChips + " chips.")));
		System.out.print("How many chips will you take, "
				+ currentPlayer().getName() + "? ");
		return input.nextInt();
	}
	
	/**********************************************************
	* isLegalMove(numChips)
	* Returns boolean making sure that a player takes more
	* than 0 chips and less than the maximum allowed chips.
	**********************************************************/
	public boolean isLegalMove(int numChips) {
		return numChips > 0 && numChips <= maxChips;
	}
	
	/**********************************************************
	* illegalMoveMsg(numChips,illegalMoves)
	* Based on the number of chips provided by the parameter
	* and the number of illegal moves made in a row, the method
	* returns a string error message informing the player why
	* their move was illegal.
	**********************************************************/
	public String illegalMoveMsg(int numChips, int illegalMoves) {
		if (illegalMoves <= 2) {
			String suffix;
			if (numChips <= 0)
				suffix = "you must take at least one chip.";
			else
				suffix = "you can't take more than " + maxChips
					+ " chip" + ((maxChips==1) ? "" : "s") + ".";
			return "Illegal move: " + suffix;
		}
		else if (illegalMoves <= 4) {
			if (numChips <= 0)
				return "YOU HAVE TO TAKE AT LEAST ONE CHIP!!!!";
			else
				return maxChips + " chip" + ((maxChips==1) ? "" : "s") + ". "
					+ maxChips + " CHIP" + ((maxChips==1) ? "" : "S")
					+ "! That's all you can take, buddy.";
		}
		else
			return "Do that one more time. I dare you.";
	}

	/**********************************************************
	* updateGameState(numChips)
	* Based on the number of chips a player takes provided by
	* the parameter, this method removes those chips from the
	* pile, gives the chips to the player, updates the
	* maximum number of chips a player can take, and then
	* changes whose turn it is.
	**********************************************************/
	public void updateGameState(int numChips) {
		//Take chips out of the pile
		chipsInPile -= numChips;
		
		//Give chips to current player
		currentPlayer().addChips(numChips);
		
		//Change turns
		if (turn == 0) {
			turn = 1;
		}
		else {
			turn = 0;
		}
		
		//Update max # of chips a player can take
		maxChips = numChips * 2;
		if (maxChips > chipsInPile)
			maxChips = chipsInPile;
	}
	
	/**********************************************************
	* isGameOver()
	* Returns true if there are no more chips in the pile.
	**********************************************************/
	public boolean isGameOver() {
		return chipsInPile == 0;
	}
	
	/**********************************************************
	* informWinner()
	* Determines the winner of the game and congratulates them.
	**********************************************************/
	public void informWinner() {
		System.out.println();
		System.out.println("* * * * * * * * * * * * * * * * * *");
		
		System.out.println(p1.getName() + " has " + p1.chips()
				+ " chip" + ((p1.chips() == 1) ? "" : "s") + ".");
		System.out.println(p2.getName() + " has " + p2.chips()
				+ " chip" + ((p2.chips() == 1) ? "" : "s") + ".");
		
		if (chipsInPile == 0) {
			
			//Change winning conditions based on game type
			switch (gameType) {
				//Even number of chips wins
				case 1:
					if (p1.chips() % 2 == 0)
						System.out.println(p1.getName() + " wins!");
					else
						System.out.println(p2.getName() + " wins!");
					break;
				//Last chip wins
				case 2:
					System.out.println(otherPlayer().getName() + " wins!");
					break;
				//Last chip loses
				case 3:
					System.out.println(currentPlayer().getName() + " wins!");
					break;
				case 4:
					if (p1.chips() > p2.chips())
						System.out.println(p1.getName() + " wins!");
					else
						System.out.println(p2.getName() + " wins!");
					break;
			}
			
		}
		else {
			System.out.println(otherPlayer().getName() + " wins!");
			System.out.println("Yes, " + currentPlayer().getName()
					+ ", you can lose for being annoying.");
		}
	}
	
	/**********************************************************
	* askPlayAgain()
	* Asks the player if they want to play the game again and
	* returns true if they answer in the affirmative.
	**********************************************************/
	public boolean askPlayAgain() {
		System.out.print("Would you like to play again? (y/n): ");
		String answer = input.next();
		System.out.println();
		return answer.startsWith("y") || answer.startsWith("Y");
	}
	
	/**********************************************************
	* currentPlayer() and otherPlayer()
	* Helper methods that return the Player object for the
	* current player and other player based on whose turn it
	* is.
	**********************************************************/
	private Player currentPlayer() {
		if (turn == 0)
			return p1;
		else
			return p2;
	}
	private Player otherPlayer() {
		if (turn == 0)
			return p2;
		else
			return p1;
	}
}