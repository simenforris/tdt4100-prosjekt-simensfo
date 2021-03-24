package poker.fxui;

import java.util.LinkedList;
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
	private LinkedList<Card> selectedCards;

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
		this.playerPlay = new Play("Player Play:", true);
		this.computerPlay = new Play("Computer Play:", false);
		this.selectedCards = new LinkedList<Card>();

		this.playButton.setDisable(true);
		this.infoText.setText("");

		refillHands();
	
		updateBoard();
	}

	private void updateBoard() {
		this.roundCounter.setText("Round: " + String.valueOf(this.round));
		this.playerWonCount.setText("Player Won: " + String.valueOf(this.playerWon.size()));
		this.computerWonCount.setText("Computer Won: " + String.valueOf(this.computerWon.size()));
		this.warCardsCount.setText("War Cards: " + String.valueOf(this.warCards.size()));


		this.hand.getChildren().clear();
		for (int i = 0; i < playerHand.size(); i++) {
			Card card = this.playerHand.getCard(i);

			VBox cardModel = card.getModel();
			cardModel.getStyleClass().add("inhand");
			cardModel.setId("cardInHand-" + i);
			cardModel.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					if (cardModel.getStyleClass().contains("selected")) {
						cardModel.getStyleClass().remove("selected");
						selectedCards.remove(card);
					} else {
						cardModel.getStyleClass().add("selected");
						if (selectedCards.size() == 3) {
							VBox firstCard = selectedCards.getFirst().getModel();
							firstCard.getStyleClass().remove("selected");
							selectedCards.removeFirst();
						}
						selectedCards.add(card);
					}
					playButton.setDisable(! (selectedCards.size() == 3));
				}
			});
			this.hand.add(cardModel, i, 0);
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
	private void commitPlay() {
		this.computerPlay.clear();
		this.playerPlay.clear();
		
		// Computer makes its play
		for (Card card : selectedCards) {
			Card cardToPlay = playerHand.playCard(card);
			VBox cardModel = cardToPlay.getModel();
			cardModel.getStyleClass().remove("inhand");
			cardModel.setOnMouseClicked(null);

			this.hand.getChildren().remove(cardModel);
			playerPlay.push(cardToPlay);

			computerPlay.push(deck.draw());
		}

		this.computerPlay.updateModel();
		this.playerPlay.updateModel();

		this.board.setTop(computerPlay.getModel());
		this.board.setBottom(playerPlay.getModel());
		this.infoText.setText("Someone Wins");

		if (this.playButton.getText() == "Continue") {
			this.playButton.setText("Commit Play");
		} else {
			this.playButton.setText("Continue");		
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
}
