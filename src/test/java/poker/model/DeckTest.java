package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeckTest {
	private Deck deck;
	private static final char[] suits = {'S', 'H', 'D', 'C'};

	@BeforeEach
	public void setup() {
		deck = new Deck(true);
	}

	@Test
	@DisplayName("Test Deck constructor")
	public void testConstructor() {
		assertEquals(52, deck.size(), "The deck does not have the right amount of cards");
		for (char suit : suits) {
			ArrayList<Card> cards = new ArrayList<Card>();
			for (int i = 0; i < deck.size(); i++) {
				if (deck.getCard(i).getSuit() == suit) {
					cards.add(deck.getCard(i));
				}
			}
			assertEquals(13, cards.size(), "A suit has too many cards");
			for (int i = 1; i < cards.size(); i++) {
				assertEquals(cards.get(i - 1).getFace() + 1, cards.get(i).getFace(), "Some cards are the same");
			}
		}
	}

	@Test
	@DisplayName("Test Shuffle")
	public void testShuffle() {
		int rand1, rand2, rand3, rand4, rand5;
		Random random = new Random();
		rand1 = random.nextInt(deck.size());
		rand2 = random.nextInt(deck.size());
		rand3 = random.nextInt(deck.size());
		rand4 = random.nextInt(deck.size());
		rand5 = random.nextInt(deck.size());
		Card card1, card2, card3, card4, card5;
		card1 = deck.getCard(rand1);
		card2 = deck.getCard(rand2);
		card3 = deck.getCard(rand3);
		card4 = deck.getCard(rand4);
		card5 = deck.getCard(rand5);
		deck.shuffle();
		assertFalse(card1 == deck.getCard(rand1) && card2 == deck.getCard(rand2), "Cards do not shuffle properly");
		assertFalse(card3 == deck.getCard(rand2) && card4 == deck.getCard(rand4), "Cards do not shuffle properly");
		assertFalse(card3 == deck.getCard(rand3) && card5 == deck.getCard(rand5), "Cards do not shuffle properly");
	}

	@Test
	@DisplayName("Test Draw")
	public void testDraw() {
		deck.shuffle();
		int oldSize = deck.size();
		Card card1 = deck.draw();
		Card card2 = deck.draw();
		Card card3 = deck.draw();
		assertTrue(deck.size() == oldSize - 3);
		for (int i = 0; i < deck.size(); i++) {
			assertTrue(deck.getCard(i) != card1, "A card was not removed from the deck");
			assertTrue(deck.getCard(i) != card2, "A card was not removed from the deck");
			assertTrue(deck.getCard(i) != card3, "A card was not removed from the deck");
		}
		int j = deck.size();
		for (int i = 0; i < j; i++) {
			deck.draw();
		}
		assertThrows(EmptyStackException.class, () -> {
			deck.draw();
		});
	}
}
