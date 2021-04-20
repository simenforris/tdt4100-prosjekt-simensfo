package poker.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PokerGameTest {
	private PokerGame game;

	private final int handSize = 5;

	@BeforeEach
	public void setUp() {
		game = new PokerGame(handSize, true);
	}

	@Test
	@DisplayName("Test refillhands")
	public void testRefillHands() {
		for (int i = 0; i < handSize; i++) {
			assertNull(game.getPlayerHand().getCard(i));
			assertNull(game.getComputerHand().getCard(i));
		}
		game.refillHands();
		for (int i = 0; i < handSize; i++) {
			assertTrue(game.getPlayerHand().getCard(i) instanceof Card);
			assertTrue(game.getComputerHand().getCard(i) instanceof Card);
		}
	}

	@Test
	@DisplayName("Test make play")
	public void testMakePlay() {
		LinkedList<Card> selectedCards = new LinkedList<Card>();

		Hand playerHand = game.getPlayerHand();
		Card[] playCards = {new Card('S', 11, true), new Card('C', 12, true), new Card('D', 13, true)};
		playerHand.addCard(0, playCards[0]);
		playerHand.addCard(1, playCards[1]);
		playerHand.addCard(2, playCards[2]);
		playerHand.addCard(3, new Card('S', 2, true));
		playerHand.addCard(4, new Card('D', 1, true));
		for (Card card : playCards) {
			selectedCards.add(card);
		}

		Hand computerHand = game.getComputerHand();
		Card[] comCards = {new Card('H', 6, true), new Card('H', 8, true), new Card('H', 11, true)};
		computerHand.addCard(0, comCards[0]);
		computerHand.addCard(1, comCards[1]);
		computerHand.addCard(2, comCards[2]);
		computerHand.addCard(3, new Card('C', 2, true));
		computerHand.addCard(4, new Card('S', 1, true));

		game.makePlays(selectedCards, true);
		Play playerPlay = game.getPlayerPlay();
		for (int i = 0; i < playerPlay.size(); i++) {
			assertTrue(Arrays.asList(playCards).contains(playerPlay.getCard(i)));
		}
		Play computerPlay = game.getComputerPlay();
		for (int i = 0; i < computerPlay.size(); i++) {
			assertTrue(Arrays.asList(comCards).contains(computerPlay.getCard(i)));
		}
	}


	@Test
	@DisplayName("Test Calculate Winner")
	public void testCalculateWinner() {
		// Test Player win
		Play playerPlay = game.getPlayerPlay();
		playerPlay.push(new Card('S', 11, true));
		playerPlay.push(new Card('C', 12, true));
		playerPlay.push(new Card('D', 13, true));

		Play computerPlay = game.getComputerPlay();
		computerPlay.push(new Card('H', 6, true));
		computerPlay.push(new Card('H', 8, true));
		computerPlay.push(new Card('H', 11, true));

		game.calculateWinner();
		assertTrue(game.getWinner() == "player");
		assertTrue(game.getPlayerWon().size() == 6);
		assertTrue(game.getWarCards().size() == 0);
		assertTrue(game.getComputerWon().size() == 0);

		playerPlay.clear();
		computerPlay.clear();

		// Test Tie
		playerPlay.push(new Card('S', 10, true));
		playerPlay.push(new Card('S', 2, true));
		playerPlay.push(new Card('S', 5, true));

		computerPlay.push(new Card('C', 1, true));
		computerPlay.push(new Card('C', 8, true));
		computerPlay.push(new Card('C', 3, true));

		game.calculateWinner();
		assertTrue(game.getWinner() == "tie");
		assertTrue(game.getPlayerWon().size() == 6);
		assertTrue(game.getWarCards().size() == 6);
		assertTrue(game.getComputerWon().size() == 0);

		playerPlay.clear();
		computerPlay.clear();

		// Test Computer win
		playerPlay.push(new Card('S', 2, true));
		playerPlay.push(new Card('H', 8, true));
		playerPlay.push(new Card('D', 9, true));

		computerPlay.push(new Card('D', 11, true));
		computerPlay.push(new Card('D', 12, true));
		computerPlay.push(new Card('C', 13, true));

		game.calculateWinner();
		assertTrue(game.getWinner() == "computer");
		assertTrue(game.getPlayerWon().size() == 6);
		assertTrue(game.getWarCards().size() == 0);
		assertTrue(game.getComputerWon().size() == 12);

		playerPlay.clear();
		computerPlay.clear();
	}
}
