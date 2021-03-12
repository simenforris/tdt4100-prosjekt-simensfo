package poker.model;

import java.util.Arrays;

public class Hand {
	private Card[] cards;

	public Hand(int handSize) throws IllegalArgumentException {
		if (handSize < 3) {
			throw new IllegalArgumentException("Handsize must be at least 3");
		}
		this.cards = new Card[handSize];
	}

	public void addCard(int i, Card card) {
		this.cards[i] = card;
	}

	public Card getCard(int i) {
		return this.cards[i];
	}

	public Card playCard(int i) {
		Card card = this.cards[i];
		this.cards[i] = null;
		return card;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.cards);
	}
}
