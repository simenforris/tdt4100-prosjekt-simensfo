package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.layout.VBox;
import poker.fxui.ModelBuilder;

public class Card implements Serializable {
	private char suit;
	private int face;
	private VBox model;

	public Card(char suit, int face) throws IllegalArgumentException {
		if (!(suit == 'S' || suit == 'H' || suit == 'D' || suit == 'C')) {
			throw new IllegalArgumentException("Card suit must be either S, H, D, or C");
		}
		if (!(face > 0 && face <= 13)) {
			throw new IllegalArgumentException("Card face must be between 1 and 13");
		}
		this.suit = suit;
		this.face = face;
	}

	public char getSuit() {
		return suit;
	}

	public int getFace() {
		return face;
	}

	public VBox getModel(ModelBuilder modelBuilder) {
		if (model == null) {
			model = modelBuilder.buildModel(this);
		}
		return model;
	}

	@Override
	public String toString() {
		String suit;
		switch (this.suit) {
			case 'S':
				suit = "\u2660";
				break;
			case 'H':
				suit = "\u2665";
				break;
			case 'D':
				suit = "\u2666";
				break;
			case 'C':
				suit = "\u2663";
				break;
			default:
				suit = "?";
				break;
		}
		String face;
		switch (this.face) {
			case 1:
				face = "A";
				break;
			case 11:
				face = "J";
				break;
			case 12:
				face = "Q";
				break;
			case 13:
				face = "K";
				break;
			default:
				face = String.valueOf(this.face);
				break;
		}
		return suit + face;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (! (obj instanceof Card)) {
			return false;
		}
		Card c = (Card) obj;
		return c.suit == suit && c.face == face;
	}
	
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		face = input.readInt();
		suit = input.readChar();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeInt(face);
		output.writeChar(suit);
	}
}
