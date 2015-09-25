package assignment1;

import java.util.ArrayList;
import java.util.List;

import assignment1.Card.Rank;
import assignment1.Card.Suit;


public class Hand {
	
	private int id;
	private List<Card> cards = new ArrayList<Card>();

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
		// TODO Auto-generated method stub
		return 0;
	}

}
