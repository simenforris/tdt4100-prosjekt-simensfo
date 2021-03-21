package poker.fxui;

import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import poker.model.Card;
import poker.model.Deck;
import poker.model.Hand;


public class Controller {
	@FXML
	private GridPane playerPlay;

	@FXML
	private GridPane computerPlay;

	@FXML
	private Text winnerText;

	@FXML
	private GridPane hand;

	@FXML
	private Text roundCounter;

	@FXML
	private Text computerWonCount;

	@FXML
	private Text warCardsCount;

	@FXML
	private Text playerWonCount;

	@FXML
	private Button rulesButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button loadButton;

	// Maybe add settings to change handsize and number of decks
	private int handSize;
	private int round;
	private Deck deck;
	private Hand playerHand;
	private Hand computerHand;
	private Stack<Card> playerWon;
	private Stack<Card> computerWon;
	private Stack<Card> warCards;

	private void newGame() {
		this.handSize = 5;
		this.round = 81;
		this.deck = new Deck();
		this.deck.shuffle();
		this.playerHand = new Hand(handSize);
		this.computerHand = new Hand(handSize);
		this.playerWon = new Stack<Card>();
		this.computerWon = new Stack<Card>();
		this.warCards = new Stack<Card>();
	}

	private void refillHands() {
		for (int i = 0; i < this.handSize; i++) {
			if (this.playerHand.getCard(i) == null) {
				this.playerHand.addCard(i, this.deck.draw());
			}
			if (this.computerHand.getCard(i) == null) {
				this.computerHand.addCard(i, this.deck.draw());
			}
		}
	}

	@FXML
	void initialize() {
		newGame();
		refillHands();

		this.roundCounter.setText("Round: " + String.valueOf(this.round));
	}
}
