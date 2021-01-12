package eu.mytthew;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperation {
	String path = "D:\\Programming\\Java\\challangemanager\\src\\main\\resources/".replace("\\", "/");

	public JSONObject openFile(String filename) {
		if (!filename.contains(".json")) {
			filename = filename + ".json";
		}
		File temp = new File(filename);
		try (FileInputStream fileInputStream = new FileInputStream(temp)) {
			return new JSONObject(new JSONTokener(fileInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createFile(String filename, JSONObject jsonObject) {
		File temp = new File(path + filename.toLowerCase() + ".json");
		if (temp.exists()) {
			return;
		}
		try (FileWriter fileWriter = new FileWriter(temp)) {
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String filename) {
		try {
			Files.delete(Paths.get(path + filename + ".json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean fileExist(String filename) {
		File file = new File(path + filename + ".json");
		return file.exists();
	}


}
