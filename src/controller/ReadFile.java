package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

	public static String readFile(String path) {
		String content = "";
		try {
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				content += line + "\n";
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("error "+path);
		}
		return content;
	}
}
