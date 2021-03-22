package poker.fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("Poker.fxml"));
		
		Scene scene = new Scene(parent);

		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		stage.setTitle("War Poker");
		stage.setScene(scene);
		stage.show();
	}
    public static void main(String[] args) {
        launch(App.class, args);
    }
}
