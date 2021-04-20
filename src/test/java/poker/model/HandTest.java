package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HandTest {
	private Deck deck;
	private final int handSize = 5;

	@BeforeEach
	public void setup() {
		deck = new Deck();
		deck.shuffle();
	}

	@Test
	@DisplayName("Test Hand constructor")
	public void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Hand(2);
		});
		Hand hand = new Hand(5);
		assertEquals(handSize, hand.size());
	}

	@Test
	@DisplayName("Test Add card")
	public void testAddCard() {
		Hand hand = new Hand(handSize);
		for (int i = 0; i < hand.size() - 2; i++) {
			hand.addCard(i, deck.draw());
		}
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.addCard(handSize + 2, deck.draw());
		});
		assertThrows(IllegalStateException.class, () -> {
			hand.addCard(0, deck.draw());
		});
		Card card = deck.draw();
		hand.addCard(hand.size() - 2, card);
		assertThrows(IllegalArgumentException.class, () -> {
			hand.addCard(hand.size() - 1, card);
		});
	}

	@Test
	@DisplayName("Test get card")
	public void testGetCard() {
		Hand hand = new Hand(handSize);
		for (int i = 0; i < hand.size(); i++) {
			hand.addCard(i, deck.draw());
		}
		int randomIndex = new Random().nextInt(handSize);
		assertTrue(hand.getCard(randomIndex) instanceof Card);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.getCard(handSize + 2);
		});
	}

	@Test
	@DisplayName("Test play card")
	public void testPlayCard() {
		Hand hand = new Hand(handSize);
		for (int i = 0; i < hand.size(); i++) {
			hand.addCard(i, deck.draw());
		}
		int randomIndex = new Random().nextInt(handSize);
		Card card = hand.getCard(randomIndex);
		hand.playCard(card);
		assertNull(hand.getCard(randomIndex));
		assertThrows(IllegalArgumentException.class, () -> {
			hand.playCard(deck.draw());
		});
	}
}
