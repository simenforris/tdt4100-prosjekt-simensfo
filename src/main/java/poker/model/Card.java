package poker.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Card {
	private final char suit;
	private final int face;

	public Card(char suit, int face) throws IllegalArgumentException {
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

	public VBox model() {
		VBox newCard = new VBox();
		newCard.getStyleClass().add("card");
		newCard.setAlignment(Pos.CENTER);
		newCard.minHeight(140.0);
		newCard.minWidth(110.0);
		
		DropShadow shadow = new DropShadow();
		shadow.setHeight(8.0);
		shadow.setRadius(3.5);
		shadow.setWidth(8.0);
		newCard.setEffect(shadow);
		
		Label cardText = new Label(this.toString());
		cardText.setFont(new Font(36.0));
		cardText.setTextAlignment(TextAlignment.CENTER);
		if (this.suit == 'H' || this.suit == 'D') {
			cardText.setTextFill(Color.RED);
		}

		newCard.getChildren().add(cardText);

		return newCard;
	}
}
