package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Stack;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Play implements Serializable {
	private Stack<Card> cards;
	private String label;
	private Boolean textOnTop;
	private GridPane model;

	public Play(String label, boolean textOnTop) {
		this.cards = new Stack<Card>();
		this.label = label;
		this.textOnTop = textOnTop;

		updateModel();
	}

	public Card getCard(int i) {
		return this.cards.get(i);
	}

	public int size() {
		return this.cards.size();
	}

	public GridPane getModel() {
		return this.model;
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

	public void updateModel() {
		this.model = new GridPane();
		this.model.setMinSize(360.0, 200.0);
		this.model.setVgap(5.0);
		this.model.setHgap(5.0);
		this.model.setAlignment(Pos.CENTER);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(110.0);
		col1.setHalignment(HPos.CENTER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPrefWidth(110.0);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPrefWidth(110.0);
		this.model.getColumnConstraints().addAll(col1, col2, col3);


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
			this.model.getRowConstraints().addAll(row1, row2);
		} else {
			this.model.getRowConstraints().addAll(row2, row1);
		}

		this.model.add(text, 0, textRow, 3, 1);
		for (int i = 0; i < this.cards.size(); i++) {
			this.model.add(this.cards.get(i).getModel(), i, cardRow);
		}
	}

	@Override
	public String toString() {
		return this.cards.toString();
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		cards = (Stack<Card>) input.readObject();
		label = input.readUTF();
		textOnTop = input.readBoolean();

		updateModel();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeObject(cards);
		output.writeUTF(label);
		output.writeBoolean(textOnTop);
	}
}
