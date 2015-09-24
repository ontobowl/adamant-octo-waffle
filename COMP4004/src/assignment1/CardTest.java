package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
	public void testGetRank() {
		Card aCard = new Card(2,Card.Suit.DIAMONDS);
		assertEquals(2,aCard.getRank());
	}
	@Test
	public void testGetSuit() {
		Card spadesCard = new Card(3,Card.Suit.SPADES);
		assertEquals(Card.Suit.SPADES,spadesCard.getSuit());
	}
	@Test
	public void testPrintCard() {
		Card aCard = new Card(4,Card.Suit.CLUBS);
		assertEquals("FourClubs",aCard.print());
	}

}
