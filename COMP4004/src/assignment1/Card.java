package assignment1;

public class Card {
	public enum Suits { CLUBS, DIAMONDS, SPADES, HEARTS }
	
	private int rank;
	private Suits suit;
	
	public Card(int r, Suits s){
		rank = r;
		suit = s;
	}
	
	//Getters
	public int getRank() { return rank;	}
	public Suits getSuit() { return suit; }
	private String rankToWord(int r) {
		String[] rankToWord = {
				"Two",
				"Three",
				"Four",
				"Five",
				"Six",
				"Seven",
				"Eight",
				"Nine",
				"Ten",
				"Jack",
				"Queen",
				"King",
				"Ace"
			};
		
		return rankToWord[r - 2];
	}
	private String suitToWord(Suits s) {
		switch(s) {
		case SPADES:
			return "Spades";
		case DIAMONDS:
			return "Diamonds";
		case CLUBS:
			return "Clubs";
		case HEARTS:
			return "Hearts";
		}
		return null;
	}
	public String print() {
		return rankToWord(rank) + suitToWord(suit);
	}
}
