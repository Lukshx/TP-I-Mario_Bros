package tp1.logic.gameobjects;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
import tp1.logic.Position;
public abstract class GameObject implements GameItem {
    protected Position pos;
    protected GameWorld game;
    protected boolean alive;
    protected GameObject() { this(null, null); }
    protected GameObject(GameWorld game, Position pos) { this.game = game; this.pos = pos; this.alive = true; }
    public void setGame(GameWorld game) { this.game = game; }
    public void addToContainer(GameObjectContainer container) { container.addObject(this); }
    public void update() { }
    public abstract String getIcon();
    public boolean isInsideBoard(int width, int height) { return pos != null && pos.isInBoard(width, height); }
    @Override public boolean isAlive() { return alive; }
    @Override public boolean isInPosition(Position position) { return alive && pos.equals(position); }
    @Override public boolean interactWith(GameItem item) { return false; }
    @Override public boolean receiveInteraction(Land obj) { return false; }
    @Override public boolean receiveInteraction(Mario obj) { return false; }
    @Override public boolean receiveInteraction(ExitDoor obj) { return false; }
    @Override public boolean receiveInteraction(Goomba obj) { return false; }
    @Override public boolean receiveInteraction(Box obj) { return false; }
    @Override public boolean receiveInteraction(Mushroom obj) { return false; }
}
