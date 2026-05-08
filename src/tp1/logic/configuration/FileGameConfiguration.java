package tp1.logic.configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import tp1.exceptions.FileGameException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Game;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.util.MyStringUtils;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
    private String fileName;
    private int time;
    private int points;
    private int lives;
    private String[] objectLines;
    private int objectCount;
    public FileGameConfiguration(String fileName) throws GameModelException { this.fileName = fileName; objectLines = new String[200]; readFile(); }
    private void readFile() throws GameModelException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String status = reader.readLine();
            if (status == null) throw new FileGameException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName));
            parseStatus(status);
            String line = reader.readLine();
            while (line != null) { if (!line.trim().isEmpty()) { ensureCapacity(); objectLines[objectCount] = line.trim(); objectCount++; } line = reader.readLine(); }
        }
        catch (FileNotFoundException e) { throw new FileGameException(Messages.FILE_NOT_FOUND.formatted(fileName), new FileGameException(fileName + " (El sistema no puede encontrar el archivo especificado)")); }
        catch (IOException e) { throw new FileGameException(Messages.UNABLE_TO_LOAD.formatted(fileName), e); }
    }
    private void parseStatus(String status) throws GameModelException {
        try {
            String[] words = MyStringUtils.splitWords(status);
            if (words.length != 3) throw new FileGameException(Messages.INCORRECT_GAME_STATUS.formatted(status));
            time = Integer.parseInt(words[0]); points = Integer.parseInt(words[1]); lives = Integer.parseInt(words[2]);
        }
        catch (NumberFormatException e) { throw new FileGameException(Messages.INCORRECT_GAME_STATUS.formatted(status), e); }
    }
    private void ensureCapacity() {
        if (objectCount == objectLines.length) { String[] copy = new String[objectLines.length * 2]; for (int i = 0; i < objectLines.length; i++) copy[i] = objectLines[i]; objectLines = copy; }
    }
    @Override public int remainingTime() { return time; }
    @Override public int points() { return points; }
    @Override public int numLives() { return lives; }
    @Override public GameObjectContainer createContainer(GameWorld game) throws GameModelException {
        GameObjectContainer container = new GameObjectContainer();
        try { for (int i = 0; i < objectCount; i++) { GameObject object = GameObjectFactory.parse(MyStringUtils.splitWords(objectLines[i]), game); if (!object.isInsideBoard(Game.DIM_X, Game.DIM_Y)) throw new OffBoardException(Messages.OBJECT_POSITION_OFF_BOARD.formatted(objectLines[i])); container.add(object); } }
        catch (ObjectParseException | OffBoardException e) { throw new FileGameException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName), e); }
        return container;
    }
}
