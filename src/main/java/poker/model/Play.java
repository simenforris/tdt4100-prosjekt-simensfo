package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
public class Play implements Serializable {
	private Stack<Card> cards;
	private String label;
	private Boolean textOnTop;

	public Play(String label, boolean textOnTop) {
		this.cards = new Stack<Card>();
		this.label = label;
		this.textOnTop = textOnTop;
	}

	public String getLabel() {
		return label;
	}

	public boolean getTextOnTop() {
		return textOnTop;
	}

	public Card getCard(int i) throws IndexOutOfBoundsException{
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return cards.get(i);
	}

	public int size() {
		return cards.size();
	}

	public void push(Card card) {
		cards.push(card);
	}

	public void clear() {
		cards.clear();
	}

	public Card pop() throws EmptyStackException {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return cards.pop();
	}

	// Score calculation
	private int checkSuits() {
		if (getCard(0).getSuit() == getCard(1).getSuit() 
			&& getCard(1).getSuit() == getCard(2).getSuit()) {
			// Flush
			return 3;
		} else {
			return 1;
		}
	}

	private int checkFaces() {
		int arr[] = {getCard(0).getFace(), getCard(1).getFace(), getCard(2).getFace()};
		Arrays.sort(arr);
		if (arr[0] == arr[1]
			&& arr[1] == arr[2]) {
			// Three of a kind
			return 5;
		} else if (arr[0] + 1 == arr[1] && arr[1] + 1 == arr[2]) {
			// Straight
			return 4;
		} else if (arr[0] == arr[1]
			|| arr[1] == arr[2]
			|| arr[0] == arr[2]) {
			// Two of a kind
			return 2;
		} else {
			// Nothing
			return 1;
		}
	}

	public int getScore() {
		int f = checkFaces();
		int s = checkSuits();
		if (f == 4 && s == 3) {
			// Straight Flush
			return 6;
		} else {
			return Math.max(f, s);
		}
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
		if (! (obj instanceof Play)) {
			return false;
		}
		Play p = (Play) obj;
		if (p.size() != size()) {
			return false;
		}
		for (int i = 0; i < p.size(); i++) {
			if (! p.getCard(i).equals(getCard(i))) {
				return false;
			}
		}
		return p.getTextOnTop() == textOnTop && p.getLabel().compareTo(label) == 0;
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		cards = (Stack<Card>) input.readObject();
		label = input.readUTF();
		textOnTop = input.readBoolean();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeObject(cards);
		output.writeUTF(label);
		output.writeBoolean(textOnTop);
	}
}
