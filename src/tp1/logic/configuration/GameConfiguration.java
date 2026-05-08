package tp1.logic.configuration;
import tp1.exceptions.GameModelException;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
public interface GameConfiguration {
    public int remainingTime();
    public int points();
    public int numLives();
    public GameObjectContainer createContainer(GameWorld game) throws GameModelException;
}
