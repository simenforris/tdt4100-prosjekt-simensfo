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

	public void addCard(int i, Card card) throws IndexOutOfBoundsException, IllegalStateException, IllegalArgumentException {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		if (this.cards[i] != null) {
			throw new IllegalStateException("Hand slot already taken");
		}
		if (Arrays.asList(this.cards).indexOf(card) != -1) {
			throw new IllegalArgumentException("Card already in hand");
		}
		this.cards[i] = card;
	}

	public Card getCard(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return this.cards[i];
	}

	public Card playCard(Card card) throws IllegalArgumentException {
		int index = Arrays.asList(this.cards).indexOf(card);
		if (index == -1) {
			throw new IllegalArgumentException("Card not in hand");
		}
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
