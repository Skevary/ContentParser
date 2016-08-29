package com.vk.native_code.main.view;

import java.io.File;
import java.io.IOException;

import com.vk.native_code.main.model.GsonParser;
import com.vk.native_code.main.model.JsoupParser;
import com.vk.native_code.main.model.NativeParser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.DirectoryChooser;

public class OverviewController {

	@FXML
	volatile private TextArea textArea;
	@FXML
	private TextField nativeFieldURL;
	@FXML
	private TextField nativeFieldPath;
	@FXML
	private TextField jsoupFieldURL;
	@FXML
	private TextField jsoupFieldPath;
	@FXML
	private TextField gsonFieldURL;
	@FXML
	private TextField gsonFieldPath;
	@FXML
	private ToggleButton nativeToggleButton;
	@FXML
	private ToggleButton jsoupToggleButton;
	@FXML
	private ToggleButton gsonToggleButton;
	@FXML
	private Button nativeButton;
	@FXML
	private Button jsoupButton;
	@FXML
	private Button gsonButton;
	@FXML
	private Tab nativeTab;
	@FXML
	private Tab jsoupTab;
	@FXML
	private Tab gsonTab;

	private JsoupParser jsoupParser;
	private NativeParser nativeParser;
	private GsonParser gsonParser;

	@FXML
	private void initialize() {
		nativeParser = new NativeParser(this);
		jsoupParser = new JsoupParser(this);
		gsonParser = new GsonParser(this);
	}

	@FXML
	private void handleNativeToggleButton() {

		nativeToggleButton.setOnMouseClicked((event) -> {
			if (nativeToggleButton.isSelected()) {
				nativeToggleButton.setText("Stop");

				nativeParser.setURL(nativeFieldURL.getText());
				nativeParser.setPath(nativeFieldPath.getText());
				jsoupTab.setDisable(true);
				gsonTab.setDisable(true);
				nativeFieldURL.setDisable(true);
				nativeFieldPath.setDisable(true);
				nativeButton.setDisable(true);
				nativeParser.start();
			} else {
				nativeToggleButton.setText("Start");
				nativeParser.stop();
				nativeFieldURL.clear();
				jsoupTab.setDisable(false);
				gsonTab.setDisable(false);
				nativeFieldURL.setDisable(false);
				nativeFieldPath.setDisable(false);
				nativeButton.setDisable(false);
			}
		});
	}

	@FXML
	private void handleNativeGetPath() {
		String currentDir;
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			currentDir = new File(".").getCanonicalPath() + "/resources/native/";

			directoryChooser.setTitle("Open directory");
			directoryChooser.setInitialDirectory(new File(currentDir));
			File selectedDirectory = directoryChooser.showDialog(null);
			if (selectedDirectory != null)
				nativeFieldPath.setText(selectedDirectory.getAbsolutePath() + File.separator);
			else
				selectedDirectory = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleJsoupToggleButton() {
		jsoupToggleButton.setOnMouseClicked((event) -> {
			if (jsoupToggleButton.isSelected()) {
				jsoupToggleButton.setText("Stop");

				jsoupParser.setURL(jsoupFieldURL.getText());
				jsoupParser.setPath(jsoupFieldPath.getText());
				nativeTab.setDisable(true);
				gsonTab.setDisable(true);
				jsoupFieldURL.setDisable(true);
				jsoupFieldPath.setDisable(true);
				jsoupButton.setDisable(true);
				jsoupParser.start();
			} else {
				jsoupToggleButton.setText("Start");
				jsoupParser.stop();
				jsoupFieldURL.clear();
				nativeTab.setDisable(false);
				gsonTab.setDisable(false);
				jsoupFieldURL.setDisable(false);
				jsoupFieldPath.setDisable(false);
				jsoupButton.setDisable(false);
			}
		});
	}

	@FXML
	private void handleJsoupGetPath() {
		String currentDir;
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			currentDir = new File(".").getCanonicalPath() + "/resources/jsoup/";

			directoryChooser.setTitle("Open directory");
			directoryChooser.setInitialDirectory(new File(currentDir));
			File selectedDirectory = directoryChooser.showDialog(null);
			if (selectedDirectory != null)
				jsoupFieldPath.setText(selectedDirectory.getAbsolutePath() + File.separator);
			else
				selectedDirectory = null;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleGsonToggleButton() {
		gsonToggleButton.setOnMouseClicked((event) -> {
			if (gsonToggleButton.isSelected()) {
				gsonToggleButton.setText("Stop");

				gsonParser.setURL(gsonFieldURL.getText());
				gsonParser.setPath(gsonFieldPath.getText());
				nativeTab.setDisable(true);
				jsoupTab.setDisable(true);
				gsonFieldURL.setDisable(true);
				gsonFieldPath.setDisable(true);
				gsonButton.setDisable(true);
				gsonParser.start();
			} else {
				gsonToggleButton.setText("Start");
				gsonParser.stop();
				gsonFieldURL.clear();
				nativeTab.setDisable(false);
				jsoupTab.setDisable(false);
				gsonFieldURL.setDisable(false);
				gsonFieldPath.setDisable(false);
				gsonButton.setDisable(false);
			}
		});
	}

	@FXML
	private void handleGsonGetPath() {
		String currentDir;
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			currentDir = new File(".").getCanonicalPath() + "/resources/gson/";

			directoryChooser.setTitle("Open directory");
			directoryChooser.setInitialDirectory(new File(currentDir));
			File selectedDirectory = directoryChooser.showDialog(null);
			if (selectedDirectory != null)
				gsonFieldPath.setText(selectedDirectory.getAbsolutePath() + File.separator);
			else
				selectedDirectory = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleExit() {
		System.exit(0);
	}

	@FXML
	private void handleClear() {
		textArea.clear();
	}

	public void setTextToArea(String fileName) {
		this.textArea.appendText(fileName);
	}

	public TextField getNativeFieldURL() {
		return nativeFieldURL;
	}

	public TextField getJsoupFieldURL() {
		return jsoupFieldURL;
	}

	public TextField getGsonFieldURL() {
		return gsonFieldURL;
	}
}
