package com.vk.native_code.main.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.vk.native_code.main.view.OverviewController;

public class JsoupParser extends Parser {

	public JsoupParser(OverviewController controller) {
		super(controller);
	}

	@Override
	public void run() {
		try {
			Document htmlPage = Jsoup.connect(getURL()).get();
			Elements links = htmlPage.select("a.desktop");
			for (int i = 1; i < links.size(); i++) {
				if (thread.isInterrupted())
					break;

				URL absURL = new URL(links.eq(i).attr("abs:href"));
				fileName = links.eq(i).text();
				File file = new File(getPath() + fileName);

				if (!file.exists()) {
					FileUtils.copyURLToFile(absURL, file);
					controller.setTextToArea(fileName + "+\n");
				}
			}
			controller.setTextToArea("Success!\n");
			stop();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
