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

	public Deck(boolean noModel) {
		this.cards = new Stack<Card>();

		int n = 13;
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < n; j++) {
				this.cards.push(new Card(suits[i], j + 1, noModel));
			}
		}
	}

	public int size() {
		return this.cards.size();
	}

	public Card getCard(int index) {
		return this.cards.get(index);
	}
	
	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	public boolean isEmpty() {
		return this.cards.empty();
	}

	public Card draw() throws EmptyStackException {
		if (this.size() == 0) {
			throw new EmptyStackException();
		}
		return this.cards.pop();
	}
	
	@Override
	public String toString() {
		return this.cards.toString();
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		cards = (Stack<Card>) input.readObject();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeObject(cards);
	}
}
