package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class Box extends GameObject {
    private boolean full;
    public Box() { super(); full = true; }
    public Box(GameWorld game, Position pos) { this(game, pos, true); }
    public Box(GameWorld game, Position pos, boolean full) { super(game, pos); this.full = full; }
    @Override public boolean isSolid() { return true; }
    @Override public String getIcon() { return full ? Messages.BOX : Messages.EMPTY_BOX; }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Mario mario) {
        boolean hit = alive && full && mario.hitsFromBelow(pos);
        if (hit) {
            full = false;
            game.addPoints(50);
            Position mushroomPosition = pos.move(Action.UP);
            if (game.isInBoard(mushroomPosition) && !game.isSolid(mushroomPosition)) game.addObject(new Mushroom(game, mushroomPosition));
        }
        return hit;
    }
    @Override public String toString() { return pos.toString() + " Box " + (full ? "Full" : "Empty"); }
}
