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
	
	
	public String toString() {
		String r = Character.toUpperCase(rank.toString().charAt(0)) + (rank.toString().substring(1)).toLowerCase();
		String s = Character.toUpperCase(suit.toString().charAt(0)) + suit.toString().substring(1).toLowerCase();
		return r + s;
	}
	
	public boolean equals(Object o) {
		Card c = (Card) o;
		if(o instanceof Card) {
			if(this.rank == c.getRank() && this.suit == c.getSuit())
				return true;
			else
				return false;
		}
		else return false;
	}

	public static Card parseCardFromString(String s) {
		if(!Character.isUpperCase(s.charAt(0)))
			return null;
		
		int i=1;
		while(!Character.isUpperCase(s.charAt(i)))			
			i++;
		
		if(i == s.length() - 1)
			return null;
		
		
		
		String newRank = s.substring(0, i);
		String newSuit = s.substring(i);
		
		Card.Rank aRank = Card.Rank.valueOf(newRank.toUpperCase());
		Card.Suit aSuit = Card.Suit.valueOf(newSuit.toUpperCase());
		
		return new Card(aRank,aSuit);

	}
}
