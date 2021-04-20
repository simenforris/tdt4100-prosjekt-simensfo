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
		play = new Play("test", false);
	}

	@Test
	@DisplayName("Test getCard")
	public void testGetCard() {
		assertThrows(EmptyStackException.class, () -> {
			play.pop();
		});
		play.push(new Card('C', 3));
		play.push(new Card('C', 6));
		play.push(new Card('C', 11));
		assertThrows(IndexOutOfBoundsException.class, () -> {
			play.getCard(play.size() + 2);
		});
	}

	@Test
	@DisplayName("Test score calculation")
	public void testScore() {
		play.push(new Card('C', 9));
		play.push(new Card('C', 10));
		play.push(new Card('C', 11));
		assertEquals(6, play.getScore());
		play.clear();
		play.push(new Card('S', 9));
		play.push(new Card('C', 9));
		play.push(new Card('H', 9));
		assertEquals(5, play.getScore());
		play.clear();
		play.push(new Card('S', 2));
		play.push(new Card('C', 3));
		play.push(new Card('H', 4));
		assertEquals(4, play.getScore());
		play.clear();
		play.push(new Card('H', 5));
		play.push(new Card('H', 3));
		play.push(new Card('H', 11));
		assertEquals(3, play.getScore());
		play.clear();
		play.push(new Card('S', 7));
		play.push(new Card('D', 7));
		play.push(new Card('H', 11));
		assertEquals(2, play.getScore());
		play.clear();
		play.push(new Card('S', 3));
		play.push(new Card('D', 9));
		play.push(new Card('H', 13));
		assertEquals(1, play.getScore());
	}
}
