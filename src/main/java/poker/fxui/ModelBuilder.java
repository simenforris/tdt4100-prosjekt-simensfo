package poker.fxui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import poker.model.Card;
import poker.model.Play;

public class ModelBuilder {
	public VBox buildModel(Card card) {
		VBox model = new VBox();
		model.getStyleClass().add("card");
		model.setAlignment(Pos.CENTER);
		model.minHeight(140.0);
		model.minWidth(110.0);
		
		DropShadow shadow = new DropShadow();
		shadow.setHeight(8.0);
		shadow.setRadius(3.5);
		shadow.setWidth(8.0);
		model.setEffect(shadow);
		
		Label cardText = new Label(card.toString());
		cardText.setFont(new Font(36.0));
		cardText.setTextAlignment(TextAlignment.CENTER);
		if (card.getSuit() == 'H' || card.getSuit() == 'D') {
			cardText.setTextFill(Color.RED);
		}

		model.getChildren().add(cardText);
		return model;
	}

	public GridPane buildModel(Play play) {
		GridPane model = new GridPane();
		model.setMinSize(360.0, 200.0);
		model.setVgap(5.0);
		model.setHgap(5.0);
		model.setAlignment(Pos.CENTER);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(110.0);
		col1.setHalignment(HPos.CENTER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPrefWidth(110.0);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPrefWidth(110.0);
		model.getColumnConstraints().addAll(col1, col2, col3);


		Label text = new Label(play.getLabel());
		text.setFont(new Font(24.0));
		text.setTextAlignment(TextAlignment.CENTER);;

		RowConstraints row1 = new RowConstraints();
		row1.setPrefHeight(25.0);
		RowConstraints row2 = new RowConstraints();
		row2.setPrefHeight(172.0);

		int textRow = play.getTextOnTop() ? 0 : 1;
		int cardRow = play.getTextOnTop() ? 1 : 0;

		if (play.getTextOnTop()) {
			model.getRowConstraints().addAll(row1, row2);
		} else {
			model.getRowConstraints().addAll(row2, row1);
		}

		model.add(text, 0, textRow, 3, 1);
		for (int i = 0; i < play.size(); i++) {
			model.add(play.getCard(i).getModel(this), i, cardRow);
		}

		return model;
	}
}
