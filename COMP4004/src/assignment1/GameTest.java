package assignment1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class GameTest {

	@Test
	public void testValidNumPlayers() {
		
		
		String input = "2\n3\n4\n1\n5\n3";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		Game game = new Game(stream);
		game.queryNumPlayers();
		assertEquals(2,game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(3,game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(4,game.getNumPlayers());
		game.queryNumPlayers();
		assertEquals(3,game.getNumPlayers());
	}
	@Test
	public void testInputPlayerHand() {
		
		
		String input = "2\n"
						+ "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
						+ "2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		
		Game game = new Game(stream);
		game.queryNumPlayers();
		game.queryPlayerHands();
		assertEquals("1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs", game.getHands().get(0).toString());
		assertEquals("2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds", game.getHands().get(1).toString());
		assertEquals(2, game.getHands().size());
	}
	@Test
	public void testInvalidPlayerHand() {
		
		String input = "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "5\n2\n"
				+ "0 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs\n"
				+ "AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds\n"
				+ "2 AceHearts KingClubs QueenDiamonds JackClubs NineClubs\n"
				+ "4 TwoSpades TwoClubs KingHearts TwoHearts QueenDiamonds\n"
				+ "1 AceClubs AceHearts KingSpades ThreeDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		
		
		Game game = new Game(stream);
		game.queryNumPlayers();
		game.queryPlayerHands();
		assertEquals("1 AceClubs AceHearts KingSpades ThreeDiamonds", game.getHands().get(0).toString());
		assertEquals("2 AceHearts KingClubs QueenDiamonds JackClubs NineClubs", game.getHands().get(1).toString());
		assertEquals(2,game.getHands().size());
	}
	@Test
	public void testGameWinner() {
		
		String input = "2\n"
						+ "1 ThreeClubs FourClubs FiveClubs SixClubs SevenClubs"
						+ "2 AceClubs KingDiamonds AceHearts KingSpades ThreeDiamonds";
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		
		Game game = new Game(stream);
		assertNull(game.getWinner());
		game.queryNumPlayers();
		assertNull(game.getWinner());
		game.queryPlayerHands();
		assertEquals(1, game.getRanking()[0]);
	}

}
