package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class Mushroom extends MovingObject {
    public Mushroom() { super(); dir = Action.RIGHT; }
    public Mushroom(GameWorld game, Position pos) { this(game, pos, Action.RIGHT); }
    public Mushroom(GameWorld game, Position pos, Action dir) { super(game, pos, dir); }
    @Override public boolean isSolid() { return false; }
    @Override public String getIcon() { return Messages.MUSHROOM; }
    @Override public void update() { automaticMove(); }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Mario mario) { boolean hit = alive && mario.isInPosition(pos); if (hit) { mario.grow(); alive = false; } return hit; }
    @Override public String toString() { return pos.toString() + " Mushroom " + dirString(); }
}
