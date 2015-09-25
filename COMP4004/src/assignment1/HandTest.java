package assignment1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class HandTest {

	@Test
	public void testNumCards() {
		Hand aHand = new Hand();
		
		List<Card> cards = makeDeck();
		
		
		for(int i=0; i<5; i++) {
			assertFalse(aHand.isComplete());
			aHand.addCard(cards.remove(0));
		}
		assertTrue(aHand.isComplete());
		
		try {
			aHand.addCard(cards.remove(0));
		}
		catch(Exception e) {
			fail("hand exception");
		}
	}
	
	@Test
	public void testDuplicateCard() {
		Hand aHand = new Hand();
		aHand.addCard(Card.Rank.JACK,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.ACE,Card.Suit.SPADES);
		aHand.addCard(Card.Rank.FOUR,Card.Suit.HEARTS);
		aHand.addCard(Card.Rank.THREE,Card.Suit.HEARTS);
		assertFalse(aHand.addCard(Card.Rank.ACE,Card.Suit.SPADES));		
	}
	
	@Test (expected = Exception.class)
	public void testInvalidID() {	//fails if player ID is not in the range [1,4]
			Hand highHand = new Hand();
			Hand lowHand = new Hand();
			
			highHand.setID(5);
			lowHand.setID(0);
	}
	
	@Test
	public void testHandCompare() {
		Hand royalFlushHand = new Hand();
		Hand straightFlushHand = new Hand();
		Hand fourOfAKindHand = new Hand();
		Hand fullHouseHand = new Hand();
		Hand flushHand = new Hand();
		Hand straightHand = new Hand();
		Hand threeOfAKindHand = new Hand();
		Hand twoPairHand = new Hand();
		Hand onePairHand = new Hand();
		Hand highCardHand = new Hand();
		
		assertEquals(2,highCardHand.compareTo(onePairHand));
		assertEquals(2,onePairHand.compareTo(twoPairHand));
		assertEquals(2,threeOfAKindHand.compareTo(straightHand));
		assertEquals(2,straightHand.compareTo(flushHand));
		assertEquals(2,flushHand.compareTo(fullHouseHand));
		assertEquals(2,fullHouseHand.compareTo(fourOfAKindHand));
		assertEquals(2,fourOfAKindHand.compareTo(straightFlushHand));
		assertEquals(2,straightFlushHand.compareTo(royalFlushHand));
	}
		
	@Test
	public void testHandToString() {
		Hand aHand = new Hand();
		
		aHand.setID(2);
		
		aHand.addCard(Card.Rank.TWO,Card.Suit.HEARTS);
		aHand.addCard(Card.Rank.FIVE,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.KING,Card.Suit.SPADES);
		aHand.addCard(Card.Rank.THREE,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.TEN,Card.Suit.DIAMONDS);
		
		assertEquals("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds", aHand.toString());
	}
	
	@Test
	public void testParseHand() {
		Hand aHand = new Hand();
		aHand.setID(3);
		
		aHand.addCard(Card.Rank.TWO,Card.Suit.HEARTS);
		aHand.addCard(Card.Rank.FIVE,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.KING,Card.Suit.SPADES);
		aHand.addCard(Card.Rank.THREE,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.TEN,Card.Suit.DIAMONDS);
		
		assertEquals(3,aHand.getID());
		assertEquals("TwoHearts",aHand.getCard1());
		assertEquals("FiveClubs",aHand.getCard2());
		assertEquals("KingSpades",aHand.getCard3());
		assertEquals("ThreeClubs",aHand.getCard4());
		assertEquals("TenDiamonds",aHand.getCard5());
		
	}
	
	
	private static List<Card> makeDeck() {
		List<Card> cards = new ArrayList<Card>();
		for(Card.Suit s: Card.Suit.values()) {
			for(Card.Rank r: Card.Rank.values()) {
				cards.add(new Card(r,s));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}
}
