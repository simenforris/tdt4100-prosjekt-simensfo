package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayTest {
	private Play play;

	@BeforeEach
	public void setUp() {
		play = new Play(true);
	}

	@Test
	@DisplayName("Test getCard")
	public void testGetCard() {
		assertThrows(EmptyStackException.class, () -> {
			play.pop();
		});
		play.push(new Card('C', 3, true));
		play.push(new Card('C', 6, true));
		play.push(new Card('C', 11, true));
		assertThrows(IndexOutOfBoundsException.class, () -> {
			play.getCard(play.size() + 2);
		});
	}

	@Test
	@DisplayName("Test score calculation")
	public void testScore() {
		play.push(new Card('C', 9, true));
		play.push(new Card('C', 10, true));
		play.push(new Card('C', 11, true));
		assertEquals(6, play.getScore());
		play.clear();
		play.push(new Card('S', 9, true));
		play.push(new Card('C', 9, true));
		play.push(new Card('H', 9, true));
		assertEquals(5, play.getScore());
		play.clear();
		play.push(new Card('S', 2, true));
		play.push(new Card('C', 3, true));
		play.push(new Card('H', 4, true));
		assertEquals(4, play.getScore());
		play.clear();
		play.push(new Card('H', 5, true));
		play.push(new Card('H', 3, true));
		play.push(new Card('H', 11, true));
		assertEquals(3, play.getScore());
		play.clear();
		play.push(new Card('S', 7, true));
		play.push(new Card('D', 7, true));
		play.push(new Card('H', 11, true));
		assertEquals(2, play.getScore());
		play.clear();
		play.push(new Card('S', 3, true));
		play.push(new Card('D', 9, true));
		play.push(new Card('H', 13, true));
		assertEquals(1, play.getScore());
	}
}
