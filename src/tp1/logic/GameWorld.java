package tp1.logic;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;
public interface GameWorld {
    public boolean isSolid(Position pos);
    public boolean isInAir(Position pos);
    public boolean isInBoard(Position pos);
    public boolean isBigClear(Position pos);
    public void addPoints(int points);
    public void marioArrived();
    public void marioDied();
    public void addObject(GameObject object);
    public void doInteractionsFrom(Mario mario);
    public void doInteractionsFrom(Mario mario, Position target);
}
