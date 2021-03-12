package poker.model;

import java.util.Stack;
import java.util.Collections;

public class Deck {
	private Stack<Card> cards;
	private final char[] suits = {'S', 'H', 'D', 'C'};

	public Deck() {
		this.cards = new Stack<Card>();

		int n = 13;
		for (int i = 0; i < this.suits.length; i++) {
			for (int j = 0; j < n; j++) {
				this.cards.push(new Card(this.suits[i], j + 1));
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	public boolean isEmpty() {
		return this.cards.empty();
	}

	public Card draw() {
		return this.cards.pop();
	}
	
	@Override
	public String toString() {
		return this.cards.toString();
	}
}
