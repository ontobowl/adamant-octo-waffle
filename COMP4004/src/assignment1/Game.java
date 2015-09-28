package assignment1;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Game {

	Scanner scanner;
	int numPlayers;
	List<Hand> hands;

	public Game(InputStream stream) {
		scanner = new Scanner(stream);
		numPlayers = 0;
	}

	public void queryNumPlayers() {
		
		System.out.println("Enter the number of players participating in the round, from 2 to 4.");
		numPlayers = 0;

		String sNum;
		while (numPlayers == 0) {
			sNum = scanner.nextLine();
			try {
				if (Integer.parseInt(sNum) >= 2 && Integer.parseInt(sNum) <= 4) {
					numPlayers = Integer.parseInt(sNum);
					break;
				} else {
					System.out.println("Invalid number of players, please give a number from 2 to 4.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid number of players, please give a number from 2 to 4.");
			}
		}

	}

	public void queryPlayerHands() {
		System.out.println("Enter each player's hand, composed of their ID (from 2 to " + numPlayers + ", in the format \"# RankSuit RankSuit RankSuit RankSuit RankSuit");
		String sHand;
		int[] validHands = new int[numPlayers];
		Hand newHand;
		hands = new ArrayList<Hand>();
		List<Hand> tmpHands = new ArrayList<Hand>();

		Boolean isDuplicate = false;

		for (int i = 0; i < numPlayers; i++) {
			
			sHand = scanner.nextLine();
			try {
				newHand = new Hand(sHand);
				if (newHand.getID() >= 1 && newHand.getID() <= 4 && newHand.getID() <= numPlayers) {
					if (validHands[newHand.getID() - 1] == 0) {
						for (int j = 0; j < tmpHands.size(); j++) {
							for (int k = 0; k < 5; k++) {
								for (int l = 0; l < 5; l++) {
									if (newHand.getCards().get(l).toString()
											.equals(tmpHands.get(j).getCards().get(k).toString())) {
										// there is a card in newHand that is a
										// duplicate of another card in a
										// different hand
										isDuplicate = true;
									}
								}
							}
						}
						if (isDuplicate) {
							i--;
							isDuplicate = false;
						} else {
							tmpHands.add(newHand);
							validHands[newHand.getID() - 1] = 1;
						}
					} else {
						// there is a player with that ID already
						System.out.println(
								"There is already a player with that ID, please retry with a valid and unused ID.");
						i--;
					}

				} else
					i--;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid format for a player hand, please retry.");
				i--;
			}
		}

		Hand tmpHand;

		for (int i = 0; i < numPlayers - 1; i++) {
			for (int j = i + 1; j < numPlayers; j++) {
				if (tmpHands.get(i).getID() > tmpHands.get(j).getID()) {
					tmpHand = tmpHands.get(i);
					tmpHands.set(i, tmpHands.get(j));
					tmpHands.set(j, tmpHand);
				}

			}
		}
		hands = tmpHands;
	}

	public List<Hand> getHands() {
		return hands;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public HashMap<Integer, ArrayList<Hand>> getRanking() {
		if (numPlayers < 2)
			return null;
		if (hands == null)
			return null;
		if (hands.size() != numPlayers)
			return null;

		List<Hand> handsCopy = new ArrayList<Hand>(hands);

		HashMap<Integer, ArrayList<Hand>> rankMap = new HashMap<Integer, ArrayList<Hand>>();
		for (int i = 1; i <= numPlayers; i++) {
			rankMap.put(i, new ArrayList<Hand>());
		}

		Hand maxHand;
		int tmpNum = numPlayers;

		for (int j = 0; j < tmpNum; j++) {
			maxHand = handsCopy.get(0);
			for (int i = 1; i < handsCopy.size(); i++) {
				if (maxHand.compareTo(handsCopy.get(i)) == -1) {
					maxHand = handsCopy.get(i);
				}
			}

			handsCopy.remove(maxHand);
			for (int i = 0; i < handsCopy.size(); i++) {
				if (maxHand.compareTo(handsCopy.get(i)) == 0) {
					rankMap.get(j + 1).add(handsCopy.get(i));
					handsCopy.remove(handsCopy.get(i));
					i--;
					tmpNum--;
				}
			}
			rankMap.get(j + 1).add(maxHand);
		}
		return rankMap;
	}

	public void printRanking() {
		HashMap<Integer, ArrayList<Hand>> rankMap = getRanking();

		for (int i : rankMap.keySet()) {
			for (Hand h : rankMap.get(i)) {
				System.out.println("Rank " + i + ": " + h.toString());
			}
		}
	}

	public void play() {
		//plays a single game round
		if(scanner == null)
			scanner = new Scanner(System.in);
		queryNumPlayers();
		queryPlayerHands();
		printRanking();
	}

	public void reset() {
		//resets all attributes of Game
		numPlayers=0;
		hands=null;
		scanner=null;
	}
	
	public static void main(String[] args) {
		Game game = new Game(System.in);
		while(true) {
			game.play();
			game.reset();
		}
	}
}
