package poker.fxui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import poker.model.PokerGame;

public class FileSupport implements IFileReading {

	public final static String EXTENSION = "wp";

	public Path getUserFolderPath() {
		return Path.of(System.getProperty("user.home"), "WarPoker");
	}

	public boolean ensureUserFolder() {
		try {
			Files.createDirectories(getUserFolderPath());
			return true;
		} catch(IOException e) {
			return false;
		}
	}

	public Path getSaveFilePath(String name) {
		return getUserFolderPath().resolve(name);
	}

	public String getSaveFileName() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-HH-mm");
		return "save-game-" + sdf.format(date);
	}

	public PokerGame readSaveFile(String name) throws IOException, ParseException, ClassNotFoundException {
		Path saveFilePath = getSaveFilePath(name);
		PokerGame game = null;
		try (FileInputStream fis = new FileInputStream(saveFilePath.toFile())) {
			ObjectInputStream ois = new ObjectInputStream(fis);
			game = (PokerGame) ois.readObject();
			fis.close();
			ois.close();
		}
		return game;
	}

	public void writeSaveFile(PokerGame game, String name) throws IOException {
		Path saveFilePath = getSaveFilePath(name);
		ensureUserFolder();
		try (FileOutputStream fos = new FileOutputStream(saveFilePath.toFile())) {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
			oos.close();
			fos.close();
		}
	}
}
