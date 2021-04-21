package poker.model;

import java.util.Stack;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EmptyStackException;

public class Deck implements Serializable {
	private Stack<Card> cards;
	private static final char[] suits = {'S', 'H', 'D', 'C'};

	public Deck() {
		this.cards = new Stack<Card>();

		int n = 13;
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < n; j++) {
				this.cards.push(new Card(suits[i], j + 1));
			}
		}
	}

	public int size() {
		return cards.size();
	}

	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}

	public boolean isEmpty() {
		return cards.empty();
	}

	public Card draw() throws EmptyStackException {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return cards.pop();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (! (obj instanceof Deck)) {
			return false;
		}
		Deck d = (Deck) obj;
		if (d.size() != size()) {
			return false;
		}
		for (int i = 0; i < d.size(); i++) {
			if (! d.getCard(i).equals(getCard(i))) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		cards = (Stack<Card>) input.readObject();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeObject(cards);
	}
}
