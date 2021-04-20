package poker.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayTest {
	private Play play;

	@BeforeEach
	public void setUp() {
		play = new Play();
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

	}
}
