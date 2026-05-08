package tp1.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import tp1.exceptions.FileGameException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.configuration.FileGameConfiguration;
import tp1.logic.configuration.GameConfiguration;
import tp1.logic.configuration.LevelGameConfiguration;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class Game implements GameModel, GameWorld {
    public static final int DIM_X = 30;
    public static final int DIM_Y = 15;
    private int nLevel;
    private int remainingTime;
    private int points;
    private int lives;
    private boolean playerWins;
    private boolean playerQuits;
    private GameObjectContainer gameObjects;
    private GameConfiguration initialConfiguration;
    private boolean initialConfigurationIsLevel;

    public Game(int nLevel) {
        try {
            this.nLevel = nLevel;
            initialConfiguration = new LevelGameConfiguration(nLevel);
            initialConfigurationIsLevel = true;
            loadConfiguration(initialConfiguration);
        }
        catch (GameModelException e) {
            try {
                this.nLevel = 1;
                initialConfiguration = new LevelGameConfiguration(1);
                initialConfigurationIsLevel = true;
                loadConfiguration(initialConfiguration);
            }
            catch (GameModelException ignored) {
                remainingTime = 100;
                points = 0;
                lives = 3;
                gameObjects = new GameObjectContainer();
            }
        }
    }

    private void loadConfiguration(GameConfiguration configuration) throws GameModelException {
        remainingTime = configuration.remainingTime();
        points = configuration.points();
        lives = configuration.numLives();
        gameObjects = configuration.createContainer(this);
        playerWins = false;
        playerQuits = false;
    }

    private void reloadAfterDeath(int currentPoints, int currentLives) {
        try {
            gameObjects = initialConfiguration.createContainer(this);
            remainingTime = initialConfiguration.remainingTime();
            points = currentPoints;
            lives = currentLives;
            playerWins = false;
        }
        catch (GameModelException e) { playerQuits = true; }
    }

    @Override public String positionToString(int col, int row) { return gameObjects.positionToString(new Position(col, row)); }
    @Override public boolean playerWins() { return playerWins; }
    @Override public boolean playerLoses() { return lives <= 0; }
    @Override public int remainingTime() { return remainingTime; }
    @Override public int points() { return points; }
    @Override public int numLives() { return lives; }
    @Override public boolean isFinished() { return playerWins || playerQuits || playerLoses() || remainingTime <= 0; }
    @Override public void update() {
        if (!isFinished()) {
            remainingTime--;
            gameObjects.update();
            if (remainingTime <= 0 && !playerWins) lives = 0;
        }
    }
    @Override public void reset() throws GameModelException {
        int currentPoints = points;
        int currentLives = lives;
        boolean resetStatus = initialConfigurationIsLevel && nLevel == -1;
        loadConfiguration(initialConfiguration);
        if (!resetStatus) {
            points = currentPoints;
            lives = currentLives;
        }
    }
    @Override public void reset(int nLevel) throws GameModelException {
        int currentPoints = points;
        int currentLives = lives;
        this.nLevel = nLevel;
        initialConfiguration = new LevelGameConfiguration(nLevel);
        initialConfigurationIsLevel = true;
        boolean resetStatus = nLevel == -1;
        loadConfiguration(initialConfiguration);
        if (!resetStatus) {
            points = currentPoints;
            lives = currentLives;
        }
    }
    @Override public void exit() { playerQuits = true; }
    @Override public void addAction(Action action) { gameObjects.addAction(action); }
    @Override public void addObject(String[] objWords) throws OffBoardException, ObjectParseException {
        GameObject object = GameObjectFactory.parse(objWords, this);
        if (!object.isInsideBoard(DIM_X, DIM_Y)) throw new OffBoardException(Messages.OBJECT_POSITION_OFF_BOARD.formatted(String.join(" ", objWords)));
        gameObjects.add(object);
    }
    @Override public void save(String fileName) throws GameModelException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { writer.write(toString()); }
        catch (IOException e) { throw new FileGameException(Messages.UNABLE_TO_SAVE.formatted(fileName), e); }
    }
    @Override public void load(String fileName) throws GameModelException {
        try {
            GameConfiguration configuration = new FileGameConfiguration(fileName);
            loadConfiguration(configuration);
            initialConfiguration = configuration;
            initialConfigurationIsLevel = false;
        }
        catch (GameModelException e) { throw new FileGameException(Messages.UNABLE_TO_LOAD.formatted(fileName), e); }
    }
    @Override public boolean isSolid(Position pos) { return gameObjects.isSolid(pos); }
    @Override public boolean isInAir(Position pos) { Position below = pos.move(Action.DOWN); return !isInBoard(below) || !isSolid(below); }
    @Override public boolean isInBoard(Position pos) { return pos.isInBoard(DIM_X, DIM_Y); }
    @Override public boolean isBigClear(Position pos) { Position above = pos.move(Action.UP); return isInBoard(above) && !isSolid(above); }
    @Override public void addPoints(int points) { this.points += points; }
    @Override public void marioArrived() { if (!playerWins) { points += remainingTime * 10; remainingTime = 0; playerWins = true; } }
    @Override public void marioDied() {
        if (!playerWins && lives > 0) {
            if (initialConfigurationIsLevel && nLevel == -1) {
                try { loadConfiguration(initialConfiguration); }
                catch (GameModelException e) { playerQuits = true; }
            }
            else {
                int currentPoints = points;
                lives--;
                if (lives > 0) reloadAfterDeath(currentPoints, lives);
            }
        }
    }
    @Override public void addObject(GameObject object) { gameObjects.add(object); }
    @Override public void doInteractionsFrom(Mario mario) { gameObjects.doInteractionsFrom(mario); }
    @Override public void doInteractionsFrom(Mario mario, Position target) { gameObjects.doInteractionsFrom(mario, target); }
    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(remainingTime).append(Messages.SPACE).append(points).append(Messages.SPACE).append(lives).append(Messages.LINE_SEPARATOR);
        builder.append(gameObjects.toString());
        return builder.toString();
    }
}
