package assignment1;

import java.util.ArrayList;
import java.util.List;

import assignment1.Card.Rank;
import assignment1.Card.Suit;


public class Hand {
	
	public enum PokerRank {
			ROYALFLUSH,
			STRAIGHTFLUSH,
			FOUROFAKIND,
			FULLHOUSE,
			FLUSH,
			STRAIGHT,
			THREEOFAKIND,
			TWOPAIR,
			ONEPAIR,
			HIGHHAND
	}
	
	private int id;
	private List<Card> cards = new ArrayList<Card>();

	public Hand() {
		
	}
	
	public Hand(String h) throws IllegalArgumentException{
		parseHandFromString(h);
	}
	
	public void parseHandFromString(String h) {
		String[] tokens = h.split("[ ]+");
		
		if(tokens.length != 6) 
			throw new IllegalArgumentException();
		
		id = Integer.parseInt(tokens[0]);
		cards.add(Card.parseCardFromString(tokens[1]));
		cards.add(Card.parseCardFromString(tokens[2]));
		cards.add(Card.parseCardFromString(tokens[3]));
		cards.add(Card.parseCardFromString(tokens[4]));
		cards.add(Card.parseCardFromString(tokens[5]));
	}

	public Boolean isComplete() { return cards.size() == 5; }

	public Boolean addCard(Rank r, Suit s) {
		Card c = new Card(r,s);
		if(cards.size() == 5 || cards.contains(c))
			return false;
		
		cards.add(c);
		return true;
		
	}

	public Boolean setID(int newID) { 
		if(newID < 1 || newID > 5)
			return false;
		id = newID; 
		return true;
	}
	
	//Getters
	public int getID() { return id; }
	public Card getCard1() { return cards.get(0); }
	public Card getCard2() { return cards.get(1); }
	public Card getCard3() { return cards.get(2); }
	public Card getCard4() { return cards.get(3); }
	public Card getCard5() { return cards.get(4); }

	public int compareTo(Hand h) {			
		
		
		
		return 0;
	}
	
	public PokerRank getHandRanking() {
		//get sorted order
		
		List<Card> sortedCards = new ArrayList<Card>();
		for(Card c: cards)
			sortedCards.add(c);
		
		Card tmpCard;
		for(int i=0;i<4;i++) {
			for(int j=i+1;j<5;j++) {
				if(sortedCards.get(i).getRank().ordinal() > sortedCards.get(j).getRank().ordinal()) {
					tmpCard = sortedCards.get(i);
					sortedCards.set(i,sortedCards.get(j));
					sortedCards.set(j,tmpCard);
				}
			}
		}
		
		
		//check royal flush
		if((sortedCards.get(0).getRank().toString() == "TEN" &&
			sortedCards.get(1).getRank().toString() == "JACK" &&
			sortedCards.get(2).getRank().toString() == "QUEEN" &&
			sortedCards.get(3).getRank().toString() == "KING" &&
			sortedCards.get(4).getRank().toString() == "ACE")
				&&
			(sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit() &&
			sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit() &&
			sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit() &&
			sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit()))
			return PokerRank.ROYALFLUSH;
		
		//check straight flush
		int lowestRank = sortedCards.get(0).getRank().ordinal();
		if((sortedCards.get(1).getRank().ordinal() == lowestRank+1 &&
			sortedCards.get(2).getRank().ordinal() == lowestRank+2 &&
			sortedCards.get(3).getRank().ordinal() == lowestRank+3 &&
			sortedCards.get(4).getRank().ordinal() == lowestRank+4)
				&&
			sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit() &&
			sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit() &&
			sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit() &&
			sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit())
			return PokerRank.STRAIGHTFLUSH;
										
		//check four of a kind
		if(	sortedCards.get(0).getRank() == sortedCards.get(1).getRank()) 
			if(	sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
				sortedCards.get(2).getRank() == sortedCards.get(3).getRank())
				return PokerRank.FOUROFAKIND;
		else if(sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
				sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
				sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
				return PokerRank.FOUROFAKIND;
				
		//check full house
		if((sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
		   ||
		   (sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
		  ) {
				return PokerRank.FULLHOUSE;
			}
		
		//check flush
		if(	sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit() &&
			sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit() &&
			sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit() &&
			sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit())
			return PokerRank.FLUSH;
		
		//check straight
		if(	sortedCards.get(1).getRank().ordinal() == lowestRank+1 &&
			sortedCards.get(2).getRank().ordinal() == lowestRank+2 &&
			sortedCards.get(3).getRank().ordinal() == lowestRank+3 &&
			sortedCards.get(4).getRank().ordinal() == lowestRank+4)
			return PokerRank.STRAIGHT;
		
		//check three of a kind
		if((sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
			||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
			||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank()))
				return PokerRank.THREEOFAKIND; 
		
		//check two pair
		if((sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
			||
		   (sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
		    ||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank()))
			return PokerRank.TWOPAIR;
		
		//check one pair
		if((sortedCards.get(0).getRank() == sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
			||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() == sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
		    ||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() == sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
		    ||
		   (sortedCards.get(0).getRank() != sortedCards.get(1).getRank() &&
			sortedCards.get(1).getRank() != sortedCards.get(2).getRank() &&
			sortedCards.get(2).getRank() != sortedCards.get(3).getRank() &&
			sortedCards.get(3).getRank() == sortedCards.get(4).getRank()))
			return PokerRank.ONEPAIR;
		
		//otherwise, high hand
		return PokerRank.HIGHHAND;
	}
	
	public String toString() {
		return id 
				+ " " + cards.get(0).toString() 
				+ " " + cards.get(1).toString() 
				+ " " + cards.get(2).toString() 
				+ " " + cards.get(3).toString()
				+ " " + cards.get(4).toString();
	}

}
