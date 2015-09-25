package assignment1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class HandTest {

	@Test
	public void testNumCards() {
		Hand aHand = new Hand(1);
		
		List<Card> cards = makeDeck();
		
		
		for(int i=0; i<5; i++) {
			assertFalse(aHand.isComplete(), "hand should not be complete");
			aHand.addCard(cards.remove(0));
		}
		assertTrue(aHand.isComplete(), "hand should be complete");
		
		try {
			aHand.addCard(cards.remove(0));
		}
		catch(HandException e) {
			fail("hand exception");
		}
	}
	
	@Test
	public void testDuplicateCard() {
		Hand aHand = new Hand(1);
		aHand.addCard(Card.Rank.JACK,Card.Suit.CLUBS);
		aHand.addCard(Card.Rank.ACE,Card.Suit.SPADES);
		aHand.addCard(Card.Rank.FOUR,Card.Suit.HEARTS);
		aHand.addCard(Card.Rank.THREE,Card.Suit.HEARTS);
		assertFalse(aHand.addCard(Card.Rank.ACE,Card.Suit.SPADES), "should not be able to add duplicate cards");		
	}
	
	@Test (expected = InvalidIDException.class)
	public void testInvalidID() {	//fails if player ID is not in the range [1,4]
			Hand highHand = new Hand(5);
			Hand lowHand = new Hand(0);
	}
	
	@Test
	public void testHandCompare() {
		Hand royalFlushHand = new Hand(1);
		Hand straightFlushHand = new Hand(1);
		Hand fourOfAKindHand = new Hand(1);
		Hand fullHouseHand = new Hand(1);
		Hand flushHand = new Hand(1);
		Hand straightHand = new Hand(1);
		Hand threeOfAKindHand = new Hand(1);
		Hand twoPairHand = new Hand(1);
		Hand onePairHand = new Hand(1);
		Hand highCardHand = new Hand(1);
		
		assertTrue(highCardHand < onePairHand);
		assertTrue(onePairHand < twoPairHand);
		assertTrue(threeOfAKindHand < straightHand);
		assertTrue(straightHand < flushHand);
		assertTrue(flushHand < fullHouseHand);
		assertTrue(fullHouseHand < fourOfAKindHand);
		assertTrue(fourOfAKindHand < straightFlushHand);
		assertTrue(straightFlushHand < royalFlushHand);
	}
		
	@Test
	public void testHandToString() {
		Hand aHand = new Hand(2);
		aHand.addCard("TwoHearts");
		aHand.addCard("FiveClubs");
		aHand.addCard("KingSpades");
		aHand.addCard("ThreeClubs");
		aHand.addCard("TenDiamonds");
		
		assertEquals("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds", aHand.toString());
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
