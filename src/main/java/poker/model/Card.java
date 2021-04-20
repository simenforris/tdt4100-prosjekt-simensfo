package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

		updateModel();
	}

	public Card(char suit, int face, boolean noModel) throws IllegalArgumentException {
		if (!(suit == 'S' || suit == 'H' || suit == 'D' || suit == 'C')) {
			throw new IllegalArgumentException("Card suit must be either S, H, D, or C");
		} else if (!(face >= 1 && face <= 13)) {
			throw new IllegalArgumentException("Card face must be between 1 and 13");
		}
		this.suit = suit;
		this.face = face;
	}

	public char getSuit() {
		return this.suit;
	}

	public int getFace() {
		return this.face;
	}

	public VBox getModel() {
		return this.model;
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

	private void updateModel() {
		// Create model of card
		this.model = new VBox();
		this.model.getStyleClass().add("card");
		this.model.setAlignment(Pos.CENTER);
		this.model.minHeight(140.0);
		this.model.minWidth(110.0);
		
		DropShadow shadow = new DropShadow();
		shadow.setHeight(8.0);
		shadow.setRadius(3.5);
		shadow.setWidth(8.0);
		this.model.setEffect(shadow);
		
		Label cardText = new Label(this.toString());
		cardText.setFont(new Font(36.0));
		cardText.setTextAlignment(TextAlignment.CENTER);
		if (this.suit == 'H' || this.suit == 'D') {
			cardText.setTextFill(Color.RED);
		}

		this.model.getChildren().add(cardText);
	}

	
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		face = input.readInt();
		suit = input.readChar();

		updateModel();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeInt(face);
		output.writeChar(suit);
	}
}
