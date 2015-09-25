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
		
		List<Card> deck = makeDeck();
		Card tmpCard;
		
		for(int i=0; i<5; i++) {
			assertFalse(aHand.isComplete());
			tmpCard = deck.remove(0);
			aHand.addCard(tmpCard.getRank(),tmpCard.getSuit());
		}
		assertTrue(aHand.isComplete());
		
		try {
			tmpCard = deck.remove(0);
			aHand.addCard(tmpCard.getRank(),tmpCard.getSuit());
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
	
	@Test
	public void testInvalidID() {	//fails if player ID is not in the range [1,4]
			Hand highHand = new Hand();
			Hand lowHand = new Hand();
			
			assertFalse(highHand.setID(6));
			assertFalse(lowHand.setID(0));
	}
	
	@Test
	public void testHandCompare() {
		Hand royalFlushHand = new Hand("1 TenHearts JackHearts QueenHearts KingHearts AceHearts");
		Hand straightFlushHand = new Hand("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs");
		Hand fourOfAKindHand = new Hand("3 TwoClubs TwoHearts TwoSpades TwoDiamonds FiveClubs");
		Hand fullHouseHand = new Hand("2 AceHearts AceClubs AceSpades KingDiamonds KingHearts");
		Hand flushHand = new Hand("1 AceClubs TenClubs FiveClubs ThreeClubs NineClubs");
		Hand straightHand = new Hand("1 TwoClubs ThreeClubs FourSpades FiveClubs SixDiamonds");
		Hand threeOfAKindHand = new Hand("4 TwoSpades TwoClubs KingHearts TwoHearts QueenDiamonds");
		Hand twoPairHand = new Hand("2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds");
		Hand onePairHand = new Hand("3 AceHearts AceDiamonds KingSpades FourClubs SixHearts");
		Hand highCardHand = new Hand("1 AceHearts KingClubs QueenDiamonds JackClubs NineClubs");
		
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
		assertEquals("TwoHearts",aHand.getCard1().toString());
		assertEquals("FiveClubs",aHand.getCard2().toString());
		assertEquals("KingSpades",aHand.getCard3().toString());
		assertEquals("ThreeClubs",aHand.getCard4().toString());
		assertEquals("TenDiamonds",aHand.getCard5().toString());
	} 
	
	@Test
	public void testParseHandFromString() {
		//Boolean thrown = false;
		try {
			Hand aHand = new Hand("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds");
			assertEquals(2,aHand.getID());
			assertEquals("TwoHearts",aHand.getCard1().toString());
			assertEquals("FiveClubs",aHand.getCard2().toString());
			assertEquals("KingSpades",aHand.getCard3().toString());
			assertEquals("ThreeClubs",aHand.getCard4().toString());
			assertEquals("TenDiamonds",aHand.getCard5().toString());
		} catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
		

	}
	
	@Test
	public void testGetHandRanking() {
		//check royal flush
		Hand royalFlushHand = new Hand("1 JackHearts TenHearts QueenHearts KingHearts AceHearts");
		Hand straightFlushHand = new Hand("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs");
		Hand fourOfAKindHand = new Hand("3 TwoClubs TwoHearts TwoSpades TwoDiamonds FiveClubs");
		Hand fullHouseHand = new Hand("2 AceHearts AceClubs AceSpades KingDiamonds KingHearts");
		Hand flushHand = new Hand("1 AceClubs TenClubs FiveClubs ThreeClubs NineClubs");
		Hand straightHand = new Hand("1 TwoClubs ThreeClubs FourSpades FiveClubs SixDiamonds");
		Hand threeOfAKindHand = new Hand("4 TwoSpades TwoClubs KingHearts TwoHearts QueenDiamonds");
		Hand twoPairHand = new Hand("2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds");
		Hand onePairHand = new Hand("3 AceHearts AceDiamonds KingSpades FourClubs SixHearts");
		Hand highCardHand = new Hand("1 AceHearts KingClubs QueenDiamonds JackClubs NineClubs");
	
		
		assertEquals(Hand.PokerRank.ROYALFLUSH, royalFlushHand.getHandRanking());
		assertEquals(Hand.PokerRank.STRAIGHTFLUSH, straightFlushHand.getHandRanking());
		assertEquals(Hand.PokerRank.FOUROFAKIND, fourOfAKindHand.getHandRanking());
		assertEquals(Hand.PokerRank.FULLHOUSE, fullHouseHand.getHandRanking());
		assertEquals(Hand.PokerRank.FLUSH, flushHand.getHandRanking());
		assertEquals(Hand.PokerRank.STRAIGHT, straightHand.getHandRanking());
		assertEquals(Hand.PokerRank.THREEOFAKIND, threeOfAKindHand.getHandRanking());
		assertEquals(Hand.PokerRank.TWOPAIR, twoPairHand.getHandRanking());
		assertEquals(Hand.PokerRank.ONEPAIR, onePairHand.getHandRanking());
		assertEquals(Hand.PokerRank.HIGHHAND, highCardHand.getHandRanking());
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
