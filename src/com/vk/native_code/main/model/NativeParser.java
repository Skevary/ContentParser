package com.vk.native_code.main.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vk.native_code.main.view.OverviewController;

public class NativeParser extends Parser {

	public NativeParser(OverviewController controller) {
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
			URL url = new URL(getURL());
			int itr=0;
			long timeout = System.currentTimeMillis();
			long start = Runtime.getRuntime().freeMemory();
			String htmlPage = connect(url);

			String re1 = ".*?"; // Non-greedy match on filler
			String re2 = "(\\/src\\/\\d{9}\\/\\d{14}\\.(?:[a-z][a-z]+))"; // Src
			String re3 = "(\\d{14}\\.(?:[a-z][a-z]+))";// Name

			Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(htmlPage);
			while (m.find()) {
				if (thread.isInterrupted())
					break;
				String pathSrc = m.group(1);
				Pattern d = Pattern.compile(re1 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher n = d.matcher(pathSrc);
				if (n.find())
					fileName = n.group(1);

				URL fileUrl = new URL("https://2ch.hk/b" + pathSrc);
				HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

				File file = new File(getPath() + fileName);
				if (!file.exists()) {
					FileOutputStream fw = new FileOutputStream(file);
					itr++;
					byte[] b = new byte[1024];
					int count = 0;
					while ((count = bis.read(b)) != -1) {
						fw.write(b, 0, count);
					}
					controller.setTextToArea(fileName + "+\n");
					fw.close();
//					Thread.sleep(2000);
				}
			}
			controller.setTextToArea("Success!\n");
			System.out.println("Количество файлов: "+itr);
			System.out.println("Время выполнения: " + (System.currentTimeMillis() - timeout) + " ms.");
			System.out.println("Ресурсы: " + ((start-Runtime.getRuntime().freeMemory())/1000)+" kb.");
			stop();
		} catch (MalformedURLException e) {
			controller.getNativeFieldURL().setText("Set URL field!");
		} catch (IOException ex) {
			ex.printStackTrace();
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
		}

	}

}
