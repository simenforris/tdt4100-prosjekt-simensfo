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

	private final ModelBuilder modelBuilder = new ModelBuilder();

	// Linked list to keep track of selected cards in playerHand
	private LinkedList<Card> selectedCards;
	// The game data object
	private PokerGame game;

	@FXML
	private void initialize() {
		selectedCards = new LinkedList<Card>();
		game = new PokerGame(handSize);
		game.refillHands();

		updateBoard();
	}

	private void updateBoard() {
		roundCounter.setText("Round: " + String.valueOf(game.getRound()));
		playerWonCount.setText("Player Won: " + String.valueOf(game.getPlayerWon().size()));
		computerWonCount.setText("Computer Won: " + String.valueOf(game.getComputerWon().size()));
		warCardsCount.setText("War Cards: " + String.valueOf(game.getWarCards().size()));
		infoText.setText("Play 3 Cards");
		saveInfo.setText("");
		playButton.setVisible(true);
		playButton.setDisable(true);
		playButton.setText("Commit Play");
		
		board.setTop(null);
		board.setBottom(null);

		hand.getChildren().clear();
		for (int i = 0; i < game.getPlayerHand().size(); i++) {
			Card card = game.getPlayerHand().getCard(i);

			VBox cardModel = card.getModel(modelBuilder);
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
							VBox firstCard = selectedCards.getFirst().getModel(modelBuilder);
							firstCard.getStyleClass().remove("selected");
							selectedCards.removeFirst();
						}
						selectedCards.add(card);
					}
					playButton.setDisable(! (selectedCards.size() == 3));
				}
			});
			hand.add(cardModel, i, 0);
		}
	}

	@FXML
	private void commitPlay() {
		if (selectedCards.size() == 3) {
		
			for (Node card : hand.getChildren()) {
				card.getStyleClass().remove("inhand");
				card.setOnMouseClicked(null);
			}

			game.makePlays(selectedCards);

			for (int i = 0; i < game.getPlayerPlay().size(); i++) {
				hand.getChildren().remove(game.getPlayerPlay().getCard(i).getModel(modelBuilder));				
			}
			selectedCards.clear();

			board.setTop(modelBuilder.buildModel(game.getComputerPlay()));
			board.setBottom(modelBuilder.buildModel(game.getPlayerPlay()));

			game.calculateWinner();
			infoText.setText(winnerText.get(game.getWinner()));
			playButton.setText("Continue");
		} else {
			if (game.getRound() <= 8) {
				updateBoard();
			} else {
				gameOver();
			}
		}
	}

	private void gameOver() {
		playerWonCount.setText("Player Won: " + String.valueOf(game.getPlayerWon().size()));
		computerWonCount.setText("Computer Won: " + String.valueOf(game.getComputerWon().size()));
		warCardsCount.setText("War Cards: " + String.valueOf(game.getWarCards().size()));

		board.setTop(null);
		board.setBottom(null);
		playButton.setVisible(false);
		infoText.setText(game.getPlayerWon().size() > game.getComputerWon().size() ? "Game Over. You Win!" : "Game Over. Computer Wins!");

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

		board.setBottom(box);
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
				fileSupport.writeSaveFile(game, name);
				saveInfo.setText("Saved game to file: " + name.substring(name.lastIndexOf("\\") + 1));
			} catch (final IOException e) {
				saveInfo.setText(e.getMessage());
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
				game = loadedGame;
				updateBoard();
				saveInfo.setText("Loaded save file: " + name.substring(name.lastIndexOf("\\") + 1));
			} catch (Exception e) {
				saveInfo.setText(e.getMessage());;
			}
		}
	}
}
