package assignment1;



public class Card {
	public enum Rank {
		TWO (2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(11),
		QUEEN(12),
		KING(13),
		ACE(14);
		public int rank;
		private Rank(int r) {
			rank = r;
		}
	}

	public enum Suit { CLUBS, DIAMONDS, SPADES, HEARTS }
	
	private Rank rank;
	private Suit suit;
	
	public Card(Rank r, Suit s){
		rank = r;
		suit = s;
	}
	
	//Getters
	public Rank getRank() { return rank;	}
	public Suit getSuit() { return suit; }
//	private String rankToWord(int r) {
//		String[] rankToWord = {
//				"Two",
//				"Three",
//				"Four",
//				"Five",
//				"Six",
//				"Seven",
//				"Eight",
//				"Nine",
//				"Ten",
//				"Jack",
//				"Queen",
//				"King",
//				"Ace"
//			};
//		
//		return rankToWord[r - 2];
//	}
//	
//	private String suitToWord(Suit s) {
//		switch(s) {
//		case SPADES:
//			return "Spades";
//		case DIAMONDS:
//			return "Diamonds";
//		case CLUBS:
//			return "Clubs";
//		case HEARTS:
//			return "Hearts";
//		}
//		return null;
//	}
	public String toString() {
		String r = Character.toUpperCase(rank.toString().charAt(0)) + (rank.toString().substring(1)).toLowerCase();
		String s = Character.toUpperCase(suit.toString().charAt(0)) + suit.toString().substring(1).toLowerCase();
		return r + s;
	}
}
