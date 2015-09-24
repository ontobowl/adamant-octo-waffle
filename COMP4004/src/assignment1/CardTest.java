package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
	public void testGetRank() {
		Card aCard = new Card(2,Card.Suits.DIAMONDS);
		assertEquals(2,aCard.getRank());
	}
	public void testGetSuit() {
		Card spadesCard = new Card(3,Card.Suits.SPADES);
		assertEquals(Card.SPADES,spadesCard.getSuit());
	}
	publci void testPrintCard() {
		Card aCard = new Card(4,Card.Suits.CLUBS);
		assertEquals("FourClubs",aCard.print());
	}

}
