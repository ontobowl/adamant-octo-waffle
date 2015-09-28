package assignment1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class HandTest {

	@Test
	public void testNumCards() {

		List<Card> deck = makeDeck();
		Card tmpCard;

		Hand aHand = new Hand(1 + " " + deck.remove(0) + " " + deck.remove(0) + " " + deck.remove(0) + " "
				+ deck.remove(0) + " " + deck.remove(0));

		assertTrue(aHand.isComplete());

		try {
			tmpCard = deck.remove(0);
			aHand.addCard(tmpCard.getRank(), tmpCard.getSuit());
		} catch (Exception e) {
			fail("hand exception");
		}
	}

	@Test
	public void testDuplicateCard() {
		Boolean thrown = false;
		try {
			new Hand("1 JackClubs AceSpades FourHearts ThreeHearts AceSpades");
		} catch (IllegalArgumentException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testInvalidID() { // fails if player ID is not in the range
									// [1,4]
		Boolean thrown1 = false;
		Boolean thrown2 = false;

		try {
			new Hand("5 TenHearts JackHearts QueenHearts KingHearts AceHearts");
		} catch (IllegalArgumentException e) {
			thrown1 = true;
		}

		try {
			new Hand("0 TenHearts JackHearts QueenHearts KingHearts AceHearts");
		} catch (IllegalArgumentException e) {
			thrown2 = true;
		}

		assertTrue(thrown1);
		assertTrue(thrown2);
	}

	@Test
	public void testHandCompare() {
		Hand royalFlushHand1 = new Hand("1 TenHearts JackHearts QueenHearts KingHearts AceHearts");
		Hand royalFlushHand2 = new Hand("1 TenSpades JackSpades QueenSpades KingSpades AceSpades");

		Hand straightFlushHand1 = new Hand("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs");
		Hand straightFlushHand2 = new Hand("1 ThreeDiamonds FourDiamonds FiveDiamonds SixDiamonds SevenDiamonds");

		Hand fourOfAKindHand1 = new Hand("3 TwoClubs TwoHearts TwoSpades TwoDiamonds FiveClubs");
		Hand fourOfAKindHand2 = new Hand("3 TwoClubs JackDiamonds TwoSpades TwoDiamonds TwoHearts");

		Hand fullHouseHand1 = new Hand("2 AceHearts AceClubs AceSpades KingDiamonds KingHearts");
		Hand fullHouseHand2 = new Hand("2 AceHearts AceClubs KingSpades KingDiamonds KingHearts");

		Hand flushHand1 = new Hand("1 AceClubs TenClubs FiveClubs ThreeClubs NineClubs");
		Hand flushHand2 = new Hand("1 AceClubs TenClubs FourClubs ThreeClubs NineClubs");

		Hand straightHand1 = new Hand("1 TwoClubs ThreeClubs FourSpades FiveClubs SixDiamonds");
		Hand straightHand2 = new Hand("1 SevenClubs ThreeClubs FourSpades FiveHearts SixDiamonds");

		Hand threeOfAKindHand1 = new Hand("4 TwoSpades TwoClubs KingHearts TwoHearts QueenDiamonds");
		Hand threeOfAKindHand2 = new Hand("4 TwoSpades TwoClubs AceHearts TwoHearts QueenDiamonds");

		Hand twoPairHand1 = new Hand("2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds");
		Hand twoPairHand2 = new Hand("2 AceClubs KingDiamonds AceHearts KingSpades AceDiamonds");

		Hand onePairHand1 = new Hand("3 AceHearts AceDiamonds KingSpades FourClubs SixHearts");
		Hand onePairHand2 = new Hand("3 KingHearts AceClubs KingSpades FourClubs SixHearts");

		Hand highCardHand1 = new Hand("1 AceHearts KingClubs QueenDiamonds JackClubs NineClubs");
		Hand highCardHand2 = new Hand("1 AceClubs KingDiamonds QueenSpades JackClubs NineClubs");

		assertEquals(0, royalFlushHand2.compareTo(royalFlushHand1));
		assertEquals(0, straightFlushHand1.compareTo(straightFlushHand2));
		assertEquals(-1, fourOfAKindHand1.compareTo(fourOfAKindHand2));
		assertEquals(1, fullHouseHand1.compareTo(fullHouseHand2));
		assertEquals(1, flushHand1.compareTo(flushHand2));
		assertEquals(-1, straightHand1.compareTo(straightHand2));
		assertEquals(-1, threeOfAKindHand1.compareTo(threeOfAKindHand2));
		assertEquals(-1, twoPairHand1.compareTo(twoPairHand2));
		assertEquals(1, onePairHand1.compareTo(onePairHand2));
		assertEquals(0, highCardHand1.compareTo(highCardHand2));

		assertEquals(-1, highCardHand1.compareTo(onePairHand1));
		assertEquals(-1, onePairHand1.compareTo(twoPairHand1));
		assertEquals(-1, threeOfAKindHand1.compareTo(straightHand1));
		assertEquals(-1, straightHand1.compareTo(flushHand1));
		assertEquals(-1, flushHand1.compareTo(fullHouseHand1));
		assertEquals(-1, fullHouseHand1.compareTo(fourOfAKindHand1));
		assertEquals(-1, fourOfAKindHand1.compareTo(straightFlushHand1));
		assertEquals(-1, straightFlushHand1.compareTo(royalFlushHand1));

		assertEquals(1, royalFlushHand1.compareTo(flushHand1));
		assertEquals(1, threeOfAKindHand1.compareTo(onePairHand1));

		
		
		List<Card> deck = makeDeck();
		int n = 2;

		String input = n + "\n";

		List<Hand> hands = new ArrayList<Hand>();
		String handString = "";
		String tmp = "";
		for (int i = 1; i <= n; i++) {
			input += i;
			handString = "";
			handString += i;
			for (int j = 0; j < 5; j++) {
				tmp = deck.remove(0).toString();
				input += " " + tmp;
				handString += " " + tmp;
			}
			input += "\n";
			hands.add(new Hand(handString));
		}

		// get sorted order

		List<Card> sortedCards;
		Card tmpCard;

		for (int g = 0; g < n; g++) {
			sortedCards = new ArrayList<Card>();

			for (Card c : hands.get(g).getCards())
				sortedCards.add(c);

			for (int i = 0; i < 4; i++) {
				for (int j = i + 1; j < 5; j++) {
					if (sortedCards.get(i).getRank().rank > sortedCards.get(j).getRank().rank) {
						tmpCard = sortedCards.get(i);
						sortedCards.set(i, sortedCards.get(j));
						sortedCards.set(j, tmpCard);
					}
				}
			}
			hands.get(g).setCards(sortedCards);
		}

		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		System.out.println(n);
		for (int i = 0; i < n; i++) {
			System.out.println(hands.get(i).toString() + ", rank: " + hands.get(i).getHandRanking().name());
		}

		Game game = new Game(stream);
		game.play();
		for (int i = 0; i < game.getNumPlayers(); i++) {
			System.out.print(game.getRanking().get(i + 1));
		}
		System.out.println();
		System.out.println(hands.get(0).compareTo(hands.get(1)));

	}

	@Test
	public void testHandToString() {
		Hand aHand = new Hand("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds");
		assertEquals("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds", aHand.toString());
	}

	@Test
	public void testParseHand() {
		Hand aHand = new Hand("3 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds");

		assertEquals(3, aHand.getID());
		assertEquals("TwoHearts", aHand.getCard1().toString());
		assertEquals("FiveClubs", aHand.getCard2().toString());
		assertEquals("KingSpades", aHand.getCard3().toString());
		assertEquals("ThreeClubs", aHand.getCard4().toString());
		assertEquals("TenDiamonds", aHand.getCard5().toString());
	}

	@Test
	public void testParseHandFromString() {
		// Boolean thrown = false;
		try {
			Hand aHand = new Hand("2 TwoHearts FiveClubs KingSpades ThreeClubs TenDiamonds");
			assertEquals(2, aHand.getID());
			assertEquals("TwoHearts", aHand.getCard1().toString());
			assertEquals("FiveClubs", aHand.getCard2().toString());
			assertEquals("KingSpades", aHand.getCard3().toString());
			assertEquals("ThreeClubs", aHand.getCard4().toString());
			assertEquals("TenDiamonds", aHand.getCard5().toString());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testGetHandRanking() {
		// check royal flush
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
		for (Card.Suit s : Card.Suit.values()) {
			for (Card.Rank r : Card.Rank.values()) {
				cards.add(new Card(r, s));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}
}
