package assignment1;

import java.util.ArrayList;
import java.util.List;

import assignment1.Card.Rank;
import assignment1.Card.Suit;


public class Hand {
	
	public enum PokerRank {
			HIGHHAND,
			ONEPAIR,
			TWOPAIR,
			THREEOFAKIND,
			STRAIGHT,
			FLUSH,
			FULLHOUSE,			
			FOUROFAKIND,
			STRAIGHTFLUSH,
			ROYALFLUSH
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
		
		//get sorted order
		
		List<Card> sortedCards1 = new ArrayList<Card>();
		for(Card c: cards)
			sortedCards1.add(c);
		
		Card tmpCard;
		for(int i=0;i<4;i++) {
			for(int j=i+1;j<5;j++) {
				if(sortedCards1.get(i).getRank().ordinal() > sortedCards1.get(j).getRank().ordinal()) {
					tmpCard = sortedCards1.get(i);
					sortedCards1.set(i,sortedCards1.get(j));
					sortedCards1.set(j,tmpCard);
				}
			}
		}
		
		List<Card> sortedCards2 = new ArrayList<Card>();
		sortedCards2.add(h.getCard1());
		sortedCards2.add(h.getCard2());
		sortedCards2.add(h.getCard3());
		sortedCards2.add(h.getCard4());
		sortedCards2.add(h.getCard5());
		
		for(int i=0;i<4;i++) {
			for(int j=i+1;j<5;j++) {
				if(sortedCards2.get(i).getRank().ordinal() > sortedCards2.get(j).getRank().ordinal()) {
					tmpCard = sortedCards2.get(i);
					sortedCards2.set(i,sortedCards2.get(j));
					sortedCards2.set(j,tmpCard);
				}
			}
		}
		
		
		//get hand ranking
		PokerRank pRank1 = getHandRanking();
		PokerRank pRank2 = h.getHandRanking();
		
		if(pRank1.ordinal() < pRank2.ordinal())
			return -1;
		if(pRank1.ordinal() > pRank2.ordinal())
			return 1;
		
		Card highCard1 = sortedCards1.get(4);
		Card highCard2 = sortedCards2.get(4);
		
		Card kicker1;
		Card kicker2;
		
		switch(pRank1) {
		case ROYALFLUSH:
			return 0;
		case STRAIGHTFLUSH:
			if(highCard1.getRank().ordinal() > highCard2.getRank().ordinal()) 
				return 1;
			else if (highCard1.getRank().ordinal() < highCard2.getRank().ordinal()) 
				return -1;
			else 
				return 0;
		case FOUROFAKIND:
			Card nonKicker1;
			Card nonKicker2;
			
			//get majority card and kicker card for this hand
			if(sortedCards1.get(0).getRank() == sortedCards1.get(1).getRank()) {
				nonKicker1 = sortedCards1.get(0);
				kicker1 = sortedCards1.get(4);
			}
			else {
				nonKicker1 = sortedCards1.get(4);
				kicker1 = sortedCards1.get(0);
			}
			
			//get majority card and kicker card for the other hand
			if(sortedCards2.get(0).getRank() == sortedCards2.get(1).getRank()) {
				nonKicker2 = sortedCards2.get(0);
				kicker2 = sortedCards2.get(4);
			}
			else {
				nonKicker2 = sortedCards2.get(4);
				kicker2 = sortedCards2.get(0);
			}
			
			
			if(nonKicker1.getRank().ordinal() > nonKicker2.getRank().ordinal()) {
				return 1;
			} else if (nonKicker1.getRank().ordinal() < nonKicker2.getRank().ordinal()) {
				return -1;
			} else {
				if(kicker1.getRank().ordinal() > kicker2.getRank().ordinal()) {
					return 1;
				} else if (kicker1.getRank().ordinal() < kicker2.getRank().ordinal()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		case FULLHOUSE:
			if(sortedCards1.get(2).getRank().ordinal() > sortedCards2.get(2).getRank().ordinal())
				return 1;
			else if(sortedCards1.get(2).getRank().ordinal() < sortedCards2.get(2).getRank().ordinal())
				return -1;
			else {
				if(	sortedCards1.get(0).getRank().ordinal() > sortedCards2.get(0).getRank().ordinal() ||
					sortedCards1.get(4).getRank().ordinal() > sortedCards2.get(4).getRank().ordinal()) { 
					return 1;
				} else if(	sortedCards1.get(0).getRank().ordinal() < sortedCards2.get(0).getRank().ordinal() ||
							sortedCards1.get(4).getRank().ordinal() < sortedCards2.get(4).getRank().ordinal()) {
					return -1;
				} else return 0;
			}
				
		case FLUSH:
			//compare the highest cards of each hand, if one card's rank is greater then that hand is greater
			//otherwise, compare the next highest card and so on. if both hands' cards have equivalent ranks,
			//then they have equal rank
			int rankDiff;

			for(int i=4;i>=0;i--) {
				rankDiff = sortedCards1.get(i).getRank().ordinal() - sortedCards2.get(i).getRank().ordinal();
				System.out.println(rankDiff);
				if(rankDiff > 0)
					return 1;
				else if (rankDiff < 0)
					return -1;
			}
			return 0;
			
		case STRAIGHT:
			//following the assumption that aces are only high and not high/low
			System.out.println(sortedCards1.toString());
			System.out.println(sortedCards2.toString());
			
			if(sortedCards1.get(4).getRank().ordinal() > sortedCards2.get(4).getRank().ordinal())
				return 1;
			else if(sortedCards1.get(4).getRank().ordinal() < sortedCards2.get(4).getRank().ordinal())
				return -1;
			else
				return 0;
			
		case THREEOFAKIND:
			if(sortedCards1.get(4).getRank().ordinal() > sortedCards2.get(4).getRank().ordinal())
				return 1;
			else if(sortedCards1.get(4).getRank().ordinal() < sortedCards2.get(4).getRank().ordinal())
				return -1;
			else {
				if(sortedCards1.get(1).getRank().ordinal() > sortedCards2.get(1).getRank().ordinal())
					return 1;
				else if(sortedCards1.get(1).getRank().ordinal() < sortedCards2.get(1).getRank().ordinal())
					return -1;
				else {
					if(sortedCards1.get(0).getRank().ordinal() > sortedCards2.get(0).getRank().ordinal())
						return 1;
					else if(sortedCards1.get(0).getRank().ordinal() < sortedCards2.get(0).getRank().ordinal())
						return -1;
					else
						return 0;
				}
			}
		case TWOPAIR:
			Card highPair1;
			Card lowPair1;
			//Card kicker1;
			Card highPair2;
			Card lowPair2;
			//Card kicker2;
			
			//figure out each two-pairs' forms
			//form of this hand
			if(sortedCards1.get(3).getRank() == sortedCards1.get(4).getRank()) {
				highPair1 = sortedCards1.get(3);
				if(sortedCards1.get(1).getRank() == sortedCards1.get(2).getRank()) {
					lowPair1 = sortedCards1.get(1);
					kicker1 = sortedCards1.get(0);
				} else {
					lowPair1 = sortedCards1.get(0);
					kicker1 = sortedCards1.get(2);
				}
			}
			else {
				highPair1 = sortedCards1.get(3);
				kicker1 = sortedCards1.get(4);
				lowPair1 = sortedCards1.get(0);
			}
			
			//form of the other hand
			if(sortedCards2.get(3).getRank() == sortedCards2.get(4).getRank()) {
				highPair2 = sortedCards2.get(3);
				if(sortedCards2.get(1).getRank() == sortedCards2.get(2).getRank()) {
					lowPair2 = sortedCards2.get(1);
					kicker2 = sortedCards2.get(0);
				} else {
					lowPair2 = sortedCards2.get(0);
					kicker2 = sortedCards2.get(2);
				}
			}
			else {
				highPair2 = sortedCards2.get(3);
				kicker2 = sortedCards2.get(4);
				lowPair2 = sortedCards2.get(0);
			}
			
			if(highPair1.getRank().ordinal() > highPair2.getRank().ordinal())
				return 1;
			else if(highPair1.getRank().ordinal() < highPair2.getRank().ordinal())
				return -1;
			else {
				if(lowPair1.getRank().ordinal() > lowPair2.getRank().ordinal())
					return 1;
				else if(lowPair1.getRank().ordinal() < lowPair2.getRank().ordinal())
					return -1;
				else {
					if(kicker1.getRank().ordinal() > kicker2.getRank().ordinal())
						return 1;
					else if(kicker1.getRank().ordinal() < kicker2.getRank().ordinal())
						return -1;
					else
						return 0;
				}
			}
		case ONEPAIR:
			highPair1 = sortedCards1.get(4);
			highPair2 = sortedCards2.get(4);
			for(int i=3;i>=0;i--) {
				if(sortedCards1.get(i).getRank() == sortedCards1.get(i+1).getRank())
					highPair1 = sortedCards1.get(i);
				if(sortedCards2.get(i).getRank() == sortedCards2.get(i+1).getRank())
					highPair2 = sortedCards2.get(i);
			}
			if(highPair1.getRank().ordinal() > highPair2.getRank().ordinal())
				return 1;
			else if(highPair1.getRank().ordinal() > highPair2.getRank().ordinal())
				return -1;
			else {
				for(int i=4;i>=0;i--) {
					if(sortedCards1.get(i).getRank().ordinal() > sortedCards2.get(i).getRank().ordinal())
						return 1;
					else if(sortedCards1.get(i).getRank().ordinal() > sortedCards2.get(i).getRank().ordinal())
						return -1;
				}
				return 0;
			}
		case HIGHHAND:
			for(int i=4;i>=0;i--) {
				if(sortedCards1.get(i).getRank().ordinal() > sortedCards2.get(i).getRank().ordinal())
					return 1;
				else if(sortedCards1.get(i).getRank().ordinal() > sortedCards2.get(i).getRank().ordinal())
					return -1;
			}
			return 0;
		}
			
		
		
		
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
	public List<Card> getCards() {
		return cards;
	}
}
