package poker.fxui;

import java.util.Stack;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import poker.model.Card;
import poker.model.Deck;
import poker.model.Hand;
import poker.model.Play;


public class Controller {
	@FXML
	private BorderPane board;

	@FXML
	private Text infoText;
	
	@FXML
	private Button playButton;
	
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
	private Button newGameButton;

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
	private Play playerPlay;
	private Play computerPlay;

	@FXML
	private void newGame() {
		this.handSize = 5;
		this.round = 1;
		this.deck = new Deck();
		this.deck.shuffle();
		this.playerHand = new Hand(handSize);
		this.computerHand = new Hand(handSize);
		this.playerWon = new Stack<Card>();
		this.computerWon = new Stack<Card>();
		this.warCards = new Stack<Card>();
		this.playerPlay = new Play();
		this.computerPlay = new Play();

		refillHands();
	
		updateBoard();
	}

	private void updateBoard() {
		this.roundCounter.setText("Round: " + String.valueOf(this.round));
		this.playerWonCount.setText("Player Won: " + String.valueOf(this.playerWon.size()));
		this.computerWonCount.setText("Computer Won: " + String.valueOf(this.computerWon.size()));
		this.warCardsCount.setText("War Cards: " + String.valueOf(this.warCards.size()));

		this.infoText.setText("");

		this.hand.getChildren().clear();
		for (int i = 0; i < playerHand.size(); i++) {
			VBox card = this.playerHand.getCard(i).model();
			card.getStyleClass().add("inhand");
			card.setId("cardInHand-" + i);
			card.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					if (card.getStyleClass().contains("selected")) {
						card.getStyleClass().remove("selected");
					} else {
						card.getStyleClass().add("selected");
					}
				}
			});
			this.hand.add(card, i, 0);
		}
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

		this.infoText.setText("Play 3 Cards");
	}

	@FXML
	private void showRules() {
		// Show popup-window explaining game rules
		System.out.println("Show rules");
	}

	@FXML
	private void saveGame() {
		// Save gamestate to file
		System.out.println("Save game");
	}

	@FXML
	private void loadGame() {
		// Load gamestate from file
		System.out.println("Load game");
	}

	@FXML
	private void commitPlay() {
		this.computerPlay.clear();
		this.playerPlay.clear();
		for (int i = 0; i < 3; i++) {
			this.computerPlay.push(deck.draw());
			this.playerPlay.push(deck.draw());
		}

		this.board.setTop(computerPlay.model("Computer Play:", false));
		this.board.setBottom(playerPlay.model("Player Play:", true));
		this.infoText.setText("Someone Wins");;

		if (this.playButton.getText() == "Continue") {
			this.playButton.setText("Commit Play");
		} else {
			this.playButton.setText("Continue");		
		}
	}
}
