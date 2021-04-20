package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComputerTest {
	private final int handSize = 5;

	@Test
	@DisplayName("Test Computer make play")
	public void testMakePlay() {
		Computer computer = new Computer(handSize);
		Card card1 = new Card('C', 2, true);
		Card card2 = new Card('C', 8, true);
		Card card3 = new Card('C', 5, true);
		Card card4 = new Card('H', 9, true);
		Card card5 = new Card('S', 10, true);
		Card[] cards = {card1, card2, card3, card4, card5};
		for (int i = 0; i < computer.size(); i++) {
			computer.addCard(i, cards[i]);
		}

		Play bestPlay = new Play(true);
		bestPlay.push(card2);
		bestPlay.push(card4);
		bestPlay.push(card5);

		Play computerPlay = computer.makePlay(true);
		
		assertEquals(bestPlay.getScore(), computerPlay.getScore());
	}
}
