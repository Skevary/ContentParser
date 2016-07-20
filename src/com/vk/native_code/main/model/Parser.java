package com.vk.native_code.main.model;

import com.vk.native_code.main.view.OverviewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Parser implements Runnable {

	protected final StringProperty url;
	protected final StringProperty path;
	protected OverviewController controller;
	protected Thread thread;
	protected String fileName;

	public Parser(OverviewController controller) {
		this("Empty url", "Empty path");
		this.controller = controller;

	}

	public Parser(String url, String path) {
		this.url = new SimpleStringProperty(url);
		this.path = new SimpleStringProperty(path);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		try {
			thread.interrupt();
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public String getURL() {
		return url.get();
	}

	public void setURL(String url) {
		this.url.set(url);
	}

	public StringProperty urlProperty() {
		return url;
	}

	public String getPath() {
		return path.get();
	}

	public void setPath(String path) {
		this.path.set(path);
	}

	public StringProperty pathProperty() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}
}
