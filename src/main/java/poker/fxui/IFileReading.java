package poker.fxui;

import java.io.IOException;
import java.text.ParseException;

import poker.model.PokerGame;

public interface IFileReading {
	
	/**
	 * Read game state from a file of the given name
	 * @param name
	 * @return game object based on data
	 * @throws ParseException
	 * @throws IOException
	 */
	PokerGame readSaveFile(String name) throws IOException, ParseException, ClassNotFoundException;
	
	/**
	 * Create a save file of the given name from game data
	 * @param game
	 * @param name
	 * @throws IOException
	 */
	void writeSaveFile(PokerGame game, String name) throws IOException;
}
