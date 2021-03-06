package assignment1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CardTest {

	@Test
	public void testGetRank() {
		Card aCard = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
		assertEquals(Card.Rank.TWO, aCard.getRank());
	}

	@Test
	public void testGetSuit() {
		Card spadesCard = new Card(Card.Rank.THREE, Card.Suit.SPADES);
		assertEquals(Card.Suit.SPADES, spadesCard.getSuit());
	}

	@Test
	public void testPrintCard() {
		Card aCard = new Card(Card.Rank.FOUR, Card.Suit.CLUBS);
		assertEquals("FourClubs", aCard.toString());
	}

	@Test
	public void testDeck() {
		List<Card> cards = new ArrayList<Card>();
		for (Card.Suit s : Card.Suit.values()) {
			for (Card.Rank r : Card.Rank.values()) {
				cards.add(new Card(r, s));
			}
		}
		assert(cards.size() == 52); // all cards were constructed
	}

	@Test
	public void testParseCardFromString() {
		String s1 = "TwoSpades";
		String s2 = "FiveHearts";

		Card c1 = Card.parseCardFromString(s1);
		assertEquals(Card.Rank.TWO, c1.getRank());
		assertEquals(Card.Suit.SPADES, c1.getSuit());

		Card c2 = Card.parseCardFromString(s2);
		assertEquals(Card.Rank.FIVE, c2.getRank());
		assertEquals(Card.Suit.HEARTS, c2.getSuit());

		assertNull(Card.parseCardFromString("f25235GGGG3jf"));
	}

	@Test
	public void testCardEquals() {
		Card c1 = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
		Card c2 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
		Card c3 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
		Card c4 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);

		assertFalse(c1.equals(c2));
		assertFalse(c1.equals(c3));
		assertTrue(c3.equals(c4));
	}
}
