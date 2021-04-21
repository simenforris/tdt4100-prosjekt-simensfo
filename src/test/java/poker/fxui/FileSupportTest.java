package poker.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import poker.model.Card;
import poker.model.Hand;
import poker.model.PokerGame;

public class FileSupportTest {
	private final FileSupport fileSupport = new FileSupport();

	private final int handSize = 5;
	private PokerGame game;

	@BeforeEach
	public void setUp() {
		// Create a game and simulate one round
		game = new PokerGame(handSize);
		game.refillHands();
		LinkedList<Card> selectedCards = new LinkedList<Card>();
		Hand pHand = game.getPlayerHand();
		for (int i = 0; i < 3; i++) {
			selectedCards.add(pHand.getCard(i));
		}
		game.makePlays(selectedCards);
		game.calculateWinner();
	}

	@Test
	@DisplayName("Test instance integrity")
	public void testInstance() throws IOException, ParseException, ClassNotFoundException {
		fileSupport.writeSaveFile(game, "testInstance." + FileSupport.EXTENSION);

		Object deserialized = fileSupport.readSaveFile("testInstance." + FileSupport.EXTENSION);

		assertTrue(deserialized instanceof PokerGame);
		assertEquals(game, deserialized);
	}

	@Test
	@DisplayName("Test save consistency")
	public void testConsistency() throws IOException, ParseException, ClassNotFoundException {
		fileSupport.writeSaveFile(game, "testConsistency." + FileSupport.EXTENSION);

		Object deserialized1 = fileSupport.readSaveFile("testConsistency." + FileSupport.EXTENSION);
		Object deserialized2 = fileSupport.readSaveFile("testConsistency." + FileSupport.EXTENSION);
		assertEquals(deserialized1, deserialized2);
		assertEquals(game, deserialized1);
		assertEquals(game, deserialized2);
	}
}
