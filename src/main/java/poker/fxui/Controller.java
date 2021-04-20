package poker.fxui;

// Native java imports
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

// JavaFX imports
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

// Model import
import poker.model.Card;
import poker.model.PokerGame;

public class Controller {
	// Initializing all FXML components
	@FXML
	private HBox pokerView;

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
	private Text saveInfo;

	@FXML
	private Button saveButton;

	@FXML
	private Button loadButton;

	// A variable to keep handsize synced in case it is ever changed
	private final static int handSize = 5;
	// Storing winner text in a map to display it without if-statements
	private final static Map<String, String> winnerText = Map.of("player", "You Win!", "computer", "Computer Wins!", "tie", "Its a Tie!");
	// Initalizing fileSupport object
	private final FileSupport fileSupport = new FileSupport();

	// Linked list to keep track of selected cards in playerHand
	private LinkedList<Card> selectedCards;
	// The game data object
	private PokerGame game;

	@FXML
	private void initialize() {
		this.selectedCards = new LinkedList<Card>();
		this.game = new PokerGame(handSize);
		this.game.refillHands();

		updateBoard();
	}

	private void updateBoard() {
		this.roundCounter.setText("Round: " + String.valueOf(this.game.getRound()));
		this.playerWonCount.setText("Player Won: " + String.valueOf(this.game.getPlayerWon().size()));
		this.computerWonCount.setText("Computer Won: " + String.valueOf(this.game.getComputerWon().size()));
		this.warCardsCount.setText("War Cards: " + String.valueOf(this.game.getWarCards().size()));
		this.infoText.setText("Play 3 Cards");
		this.saveInfo.setText("");
		this.playButton.setVisible(true);
		this.playButton.setDisable(true);
		this.playButton.setText("Commit Play");
		
		this.board.setTop(null);
		this.board.setBottom(null);

		this.hand.getChildren().clear();
		for (int i = 0; i < this.game.getPlayerHand().size(); i++) {
			Card card = this.game.getPlayerHand().getCard(i);

			VBox cardModel = card.getModel();
			cardModel.getStyleClass().add("inhand");
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

	@FXML
	private void commitPlay() {
		// Not really a good way to determine button function
		if (this.selectedCards.size() == 3) {
		
			for (Node card : hand.getChildren()) {
				card.getStyleClass().remove("inhand");
				card.setOnMouseClicked(null);
			}

			this.game.makePlays(selectedCards);

			this.game.getPlayerPlay().updateModel();
			this.game.getComputerPlay().updateModel();

			for (int i = 0; i < this.game.getPlayerPlay().size(); i++) {
				this.hand.getChildren().remove(this.game.getPlayerPlay().getCard(i).getModel());				
			}
			selectedCards.clear();

			this.board.setTop(this.game.getComputerPlay().getModel());
			this.board.setBottom(this.game.getPlayerPlay().getModel());

			this.game.calculateWinner();
			infoText.setText(winnerText.get(this.game.getWinner()));
			this.playButton.setText("Continue");
		} else {
			if (this.game.getRound() <= 8) {
				updateBoard();
			} else {
				gameOver();
			}
		}
	}

	private void gameOver() {
		this.playerWonCount.setText("Player Won: " + String.valueOf(this.game.getPlayerWon().size()));
		this.computerWonCount.setText("Computer Won: " + String.valueOf(this.game.getComputerWon().size()));
		this.warCardsCount.setText("War Cards: " + String.valueOf(this.game.getWarCards().size()));

		this.board.setTop(null);
		this.board.setBottom(null);
		this.playButton.setVisible(false);
		this.infoText.setText(this.game.getPlayerWon().size() > this.game.getComputerWon().size() ? "Game Over. You Win!" : "Game Over. Computer Wins!");

		Button newGameButton = new Button();
		newGameButton.setText("New Game");
		newGameButton.setFont(new Font(18));
		newGameButton.setPrefWidth(115.0);
		newGameButton.onActionProperty().set(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				initialize();
			};
		});
		
		Button exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setFont(new Font(18));
		exitButton.setPrefWidth(115.0);
		exitButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		HBox box = new HBox(newGameButton, exitButton);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(15.0);

		this.board.setBottom(box);
	}

	@FXML
	private void showRules() {
		Stage rootStage, rulesStage;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rules.fxml"));
		try {
			rulesStage = fxmlLoader.load();
			rootStage = (Stage) pokerView.getScene().getWindow();
			rulesStage.initOwner(rootStage);
			rulesStage.initModality(Modality.APPLICATION_MODAL);
			rulesStage.showAndWait();

		} catch(final IOException e) {
			System.out.println(e);
		}
	}

	private FileChooser fileChooser;

	/**
	 * Function for browsing to save/load location
	 * @param isSave are we saving or loading?
	 * @return String representation of path to file
	 */
	private String browseFileLocation(final boolean isSave) {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileSupport.ensureUserFolder();
			fileChooser.setInitialDirectory(fileSupport.getUserFolderPath().toFile());
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WarPoker save files (*." + FileSupport.EXTENSION + ")", "*." + FileSupport.EXTENSION));
		}
		final Window window = pokerView.getScene().getWindow();
		File file = null;
		if (isSave) {
			fileChooser.setInitialFileName(fileSupport.getSaveFileName());
			file = fileChooser.showSaveDialog(window);
		} else {
			file = fileChooser.showOpenDialog(window);
		}
		
		if (file != null) {
			return file.getPath();
		}
		return null;
	}

	@FXML
	private void saveGame() {
		String name = browseFileLocation(true);
		if (! (name == null || name.isBlank())) {
			try {
				fileSupport.writeSaveFile(this.game, name);
				this.saveInfo.setText("Saved game to file: " + name.substring(name.lastIndexOf("\\") + 1));
			} catch (final IOException e) {
				System.out.println(e);
			}
		}
	}

	@FXML
	private void loadGame() {
		String name = browseFileLocation(false);
		PokerGame loadedGame = null;
		if (! (name == null || name.isBlank())) {
			try {
				loadedGame = fileSupport.readSaveFile(name);
				this.game = loadedGame;
				updateBoard();
				this.saveInfo.setText("Loaded save file: " + name.substring(name.lastIndexOf("\\") + 1));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
