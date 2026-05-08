package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
public abstract class MovingObject extends GameObject {
    protected Action dir;
    protected MovingObject() { super(); dir = Action.LEFT; }
    protected MovingObject(GameWorld game, Position pos, Action dir) { super(game, pos); this.dir = dir; }
    protected void automaticMove() {
        if (alive) {
            Position below = pos.move(Action.DOWN);
            if (!game.isInBoard(below)) alive = false;
            else if (game.isInAir(pos)) pos = below;
            else if (dir != Action.STOP) {
                Position next = pos.move(dir);
                if (!game.isInBoard(next) || game.isSolid(next)) dir = dir.opposite();
                else pos = next;
            }
        }
    }
    protected String dirString() {
        String text = "LEFT";
        if (dir == Action.RIGHT) text = "RIGHT";
        else if (dir == Action.STOP) text = "STOP";
        return text;
    }
}
