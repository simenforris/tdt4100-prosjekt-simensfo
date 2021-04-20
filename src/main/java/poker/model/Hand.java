package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Hand implements Serializable {
	protected Card[] cards;

	public Hand(int handSize) throws IllegalArgumentException {
		if (handSize < 3) {
			throw new IllegalArgumentException("Handsize must be at least 3");
		}
		this.cards = new Card[handSize];
	}

	public int size() {
		return this.cards.length;
	}

	public void addCard(int i, Card card) {
		this.cards[i] = card;
	}

	public Card getCard(int i) {
		return this.cards[i];
	}

	public int getCard(Card card) {
		return Arrays.asList(this.cards).indexOf(card);
	}

	public Card playCard(int i) {
		Card card = this.cards[i];
		this.cards[i] = null;
		return card;
	}

	// Må ha error handling
	public Card playCard(Card card) {
		int index = getCard(card);
		Card cardToPlay = this.cards[index];
		this.cards[index] = null;
		return cardToPlay;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.cards);
	}

	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		cards = (Card[]) input.readObject();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeObject(cards);
	}
}
