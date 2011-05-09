/************************************************************
* Chips.java
* By Matthew McNierney
* Written for CS5 Lab #1
* 
* Main method for the game.
* Implements "Exasperated Referee" and "Alternate Winning
* Criteria" extra credit (allows player to choose the rules
* at the start of the game).
************************************************************/

import java.util.Scanner;

public class Chips {
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		Referee ref;
		
		//Play again loop
		do {
			
			//Prompt for player names
			System.out.println("Let's play a game of Chips.");
			System.out.print("Player 1, enter your name: ");
			String p1Name = input.next();
			System.out.print("Player 2, enter your name: ");
			String p2Name = input.next();
			
			//Check for legal names
			while (p1Name.equalsIgnoreCase(p2Name)) {
				System.out.println("Both players can't be named "
						+ p1Name + ".");
				System.out.print("Player 2, enter a different name: ");
				p2Name = input.next();
			}
			
			//Prompt for game type
			int gameType; //holds int for game type
			System.out.println("What are the rules for this round?");
			System.out.println("\t1: Even number of chips wins.");
			System.out.println("\t2: Last chip wins.");
			System.out.println("\t3: Last chip loses.");
			System.out.println("\t4: Most chips wins.");
			System.out.print("Which option (1/2/3/4)? ");
			gameType = input.nextInt();
			
			//Make sure gameType is legal
			while (gameType < 1 || gameType > 4) {
				System.out.print("Please enter a valid game type: ");
				gameType = input.nextInt();
			}
			
			//Get number of chips in pile
			System.out.print("How many chips would you like to play with? ");
			int totalChips = input.nextInt();
			
			//Check if number of chips is legal
			//For "even number of chips wins" and "most chips wins"
			if (gameType == 1 || gameType == 4) {
				while (totalChips < 3 || totalChips % 2 == 0) {
					if (totalChips < 3) {
						System.out.println("Number of chips has to be greater than 3.");
					}
					else {
						System.out.println("Number of chips has to be odd.");
					}
					System.out.print("Enter a different number of chips: ");
					totalChips = input.nextInt();
				}
			}
			
			//For "last chip wins" and "last chips loses"
			else {
				while (totalChips < 3) {
					System.out.println("Number of chips has to be greater than 3.");
					System.out.print("Enter a different number of chips: ");
					totalChips = input.nextInt();
				}
			}
			
			//Create game referee
			ref = new Referee(p1Name,p2Name,totalChips,gameType);
			
			//Variable to hold chips taken by player
			int numChipsTaken;
			
			//Variable to hold the number of illegal moves made
			int numIllegalMoves;
			
			//Main game loop
			game: while(!ref.isGameOver()) {
				//Inform player of the state of the game
				ref.informPlayerOfState();
				
				//Get number of chips to take from current player
				numChipsTaken = ref.getNumberOfChips();
				
				//While illegal move, tell the user and reprompt
				numIllegalMoves = 0; // Keeps track of how many illegal moves
				while (!ref.isLegalMove(numChipsTaken)) {
					numIllegalMoves++;
					System.out.println();
					System.out.println(ref.illegalMoveMsg(numChipsTaken,numIllegalMoves));
					numChipsTaken = ref.getNumberOfChips();
					if (numIllegalMoves >= 5) {
						break game;
					}
				}
				
				//Update game state
				ref.updateGameState(numChipsTaken);
			}
			
			//Inform the winner of his/her success
			ref.informWinner();
		}
		while (ref.askPlayAgain());
	}
}