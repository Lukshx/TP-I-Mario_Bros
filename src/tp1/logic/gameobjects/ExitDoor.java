package tp1.logic.gameobjects;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class ExitDoor extends GameObject {
    public ExitDoor() { super(); }
    public ExitDoor(GameWorld game, Position pos) { super(game, pos); }
    @Override public boolean isSolid() { return false; }
    @Override public String getIcon() { return Messages.EXIT_DOOR; }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Mario obj) { boolean hit = isAlive() && obj.isInPosition(pos); if (hit) game.marioArrived(); return hit; }
    @Override public String toString() { return pos.toString() + " ExitDoor"; }
}
