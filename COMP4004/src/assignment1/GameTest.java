package assignment1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class GameTest {

	@Test
	public void testValidNumPlayers() {
		//Tests to see that the Game will only accept 2-4 players
		String input = "2\n3\n4\n1\n5\n3";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		game.queryNumPlayers();
		assertEquals(2, game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(3, game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(4, game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(3, game.getNumPlayers());
	}

	@Test
	public void testInputPlayerHand() {
		//Tests to see that a Game will accept a valid player hand
		System.out.println("In testInputPlayerHand");
		
		String input = "2\n" + "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		game.play();

		assertEquals("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs", game.getHands().get(0).toString());
		assertEquals("2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds", game.getHands().get(1).toString());
		assertEquals(2, game.getHands().size());
	}

	@Test
	public void testInvalidPlayerHand() {
		//Tests to see that a Game will deny invalid play hands until it receives a valid one
		System.out.println("In testInvalidPlayerHand");

		String input = "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n" + "5\n2\n"
				+ "0 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds\n"
				+ "2 AceHearts KingClubs QueenDiamonds JackClubs NineClubs\n"
				+ "4 TwoSpades TwoClubs KingHearts TwoHearts QueenDiamonds\n"
				+ "1 AceClubs ThreeClubs KingSpades ThreeDiamonds FourSpades";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		game.queryNumPlayers();
		game.queryPlayerHands();

		assertEquals("1 AceClubs ThreeClubs KingSpades ThreeDiamonds FourSpades", game.getHands().get(0).toString());
		assertEquals("2 AceHearts KingClubs QueenDiamonds JackClubs NineClubs", game.getHands().get(1).toString());
		assertEquals(2, game.getHands().size());
	}

	@Test
	public void testDuplicateCards() {
		//Tests to see that the Game will deny any duplicate cards, both in the same hand and across multiple hands
		String input = "2\n" + "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "2 AceClubs KingDiamonds AceHearts FourClubs ThreeDiamonds\n"
				+ "3 QueenDiamonds KingDiamonds TenDiamonds JackDiamonds AceDiamonds\n"
				+ "2 QueenDiamonds KingDiamonds TenDiamonds JackDiamonds AceDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		game.queryNumPlayers();
		game.queryPlayerHands();
		System.out.println(game.getHands().get(0).toString());
		System.out.println(game.getHands().get(1).toString());
		assertEquals("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs", game.getHands().get(0).toString());
		assertEquals("2 QueenDiamonds KingDiamonds TenDiamonds JackDiamonds AceDiamonds",
				game.getHands().get(1).toString());
	}

	@Test
	public void testGetRanking() {
		//Tests to see that the Game will rank hands correctly
		
		String input = "2\n" + "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		assertNull(game.getRanking());
		game.queryNumPlayers();
		assertNull(game.getRanking());
		game.queryPlayerHands();

		assertEquals(1, game.getRanking().get(1).get(0).getID());
		assertEquals(2, game.getRanking().get(2).get(0).getID());
	}

	@Test
	public void testGameRound() {
		//Tests a single game round
		InputStream stream = getRandomGameInput();

		Game game = new Game(stream);
		try{
			game.play();
			game.reset();
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void testMultipleRounds() {
		//Tests multiple game rounds
		
		InputStream stream = getRandomGameInput();

		Game game = new Game(stream);
		try{	
			game.play();
			game.reset();
			game.scanner = new Scanner(getRandomGameInput());
			
			game.play();
			game.reset();
			game.scanner = new Scanner(getRandomGameInput());
		} catch(Exception e) {
			fail();
		}
	}

	/**
	 * @return
	 */
	private InputStream getRandomGameInput() {
		//Returns a random, valid input for a single game (number of players, and 2-4 hands)
		Random rand = new Random();
		List<Card> deck = makeDeck();
		int n = rand.nextInt(3) + 2;

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
		return stream;
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
