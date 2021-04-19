package poker.fxui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RulesController {
	@FXML
	Stage rulesModal;

	@FXML
	private void closeModal() {
		rulesModal.close();
	}
}
