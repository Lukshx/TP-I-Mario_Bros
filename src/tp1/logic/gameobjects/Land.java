package tp1.logic.gameobjects;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class Land extends GameObject {
    public Land() { super(); }
    public Land(GameWorld game, Position pos) { super(game, pos); }
    @Override public boolean isSolid() { return true; }
    @Override public String getIcon() { return Messages.LAND; }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public String toString() { return pos.toString() + " Land"; }
}
