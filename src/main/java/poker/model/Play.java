package poker.model;

import java.util.Stack;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Play {
	private Stack<Card> cards;

	public Play() {
		this.cards = new Stack<Card>();
	}

	public void push(Card card) {
		this.cards.push(card);
	}

	public void clear() {
		this.cards.clear();
	}

	public Card pop() {
		return this.cards.pop();
	}

	public GridPane model(String label, boolean textOnTop) {
		GridPane play = new GridPane();
		play.setMinSize(360.0, 200.0);
		play.setVgap(5.0);
		play.setHgap(5.0);
		play.setAlignment(Pos.CENTER);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(110.0);
		col1.setHalignment(HPos.CENTER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPrefWidth(110.0);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPrefWidth(110.0);
		play.getColumnConstraints().addAll(col1, col2, col3);


		Label text = new Label(label);
		text.setFont(new Font(24.0));
		text.setTextAlignment(TextAlignment.CENTER);;
		
		// Think this needs some cleaning
		RowConstraints row1 = new RowConstraints();
		row1.setPrefHeight(25.0);
		RowConstraints row2 = new RowConstraints();
		row2.setPrefHeight(172.0);

		int textRow = textOnTop ? 0 : 1;
		int cardRow = textOnTop ? 1 : 0;

		if (textOnTop) {
			play.getRowConstraints().addAll(row1, row2);
		} else {
			play.getRowConstraints().addAll(row2, row1);
		}

		play.add(text, 0, textRow, 3, 1);
		for (int i = 0; i < this.cards.size(); i++) {
			play.add(this.cards.get(i).model(), i, cardRow);
		}

		return play;
	}
}
