package com.vk.native_code.main.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.native_code.main.view.OverviewController;

public class GsonParser extends Parser {

	public GsonParser(OverviewController controller) {
		super(controller);
	}

	private String connect(URL url) throws IOException {
		StringBuilder htmlPage = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		String s;
		while ((s = br.readLine()) != null) {
			htmlPage.append(s);
		}
		br.close();
		return htmlPage.toString();
	}

	@Override
	public void run() {
		try {

			String jsonURL = getURL().replaceAll("html", "json");
			URL url = new URL(jsonURL);
			String htmlPage = connect(url);
			String filePath;
			File file;
			URL urlFiles;

			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(htmlPage.toString()).getAsJsonObject(); 
			JsonArray jsonArray = jsonObject.getAsJsonArray("threads"); 
			jsonObject = jsonArray.get(0).getAsJsonObject(); 
			jsonArray = jsonObject.getAsJsonArray("posts"); 
			for (int i = 0; i < jsonArray.size(); i++) {
				if (thread.isInterrupted())
					break;
				JsonObject filesObject = jsonArray.get(i).getAsJsonObject(); 
				JsonArray filesArray;

				if (filesObject.get("files").toString().equals("[]") == false) { 
					filesArray = filesObject.getAsJsonArray("files"); 
					filesObject = filesArray.get(0).getAsJsonObject(); 
					fileName = filesObject.get("name").getAsString();
					filePath = filesObject.get("path").getAsString();
					file = new File(getPath() + fileName);
					urlFiles = new URL("https://2ch.hk/b/" + filePath);

					if (!file.exists()) {
						FileUtils.copyURLToFile(urlFiles, file);
						controller.setTextToArea(fileName + "+\n");
					}
				}
			}
			controller.setTextToArea("Success!\n");
			stop();
		} catch (MalformedURLException e) {
			controller.getGsonFieldURL().setText("Set URL field!");
		} catch (IOException ex) {
			ex.printStackTrace();}

	}
}
