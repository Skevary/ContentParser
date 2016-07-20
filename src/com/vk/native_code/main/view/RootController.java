package com.vk.native_code.main.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootController {

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Tesis Parser");
		alert.setHeaderText("About");
		alert.setContentText(
				"Author: native_code\nFeedback: vk.com/native_code");
		alert.showAndWait();
	}

	@FXML
	private void handleExit() {
		System.exit(0);
	}

}
