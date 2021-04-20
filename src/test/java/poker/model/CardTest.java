package poker.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CardTest {
	private static final char[] suits = {'S', 'H', 'D', 'C'};

	private static final Random random = new Random();

	@Test
	@DisplayName("Test Card constructor")
	public void testConstructor() {
		for (int i = 0; i < 5; i++) {
			var card = new Card(suits[random.nextInt(suits.length)], random.nextInt(13) + 1, true);
			assertTrue(Arrays.asList(suits).contains(card.getSuit()), "Card did not get right suit");
			assertTrue(card.getFace() > 0 && card.getFace() < 14, "Card did not get right face");
		}
		assertThrows(IllegalArgumentException.class, () -> {
			new Card('F', 3);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Card('C', 19);
		});
	}
}
