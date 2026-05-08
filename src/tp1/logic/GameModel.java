package tp1.logic;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
public interface GameModel extends GameStatus {
    public void update();
    public void reset() throws GameModelException;
    public void reset(int nLevel) throws GameModelException;
    public void exit();
    public void addAction(Action action);
    public void addObject(String[] objWords) throws OffBoardException, ObjectParseException;
    public void save(String fileName) throws GameModelException;
    public void load(String fileName) throws GameModelException;
}
