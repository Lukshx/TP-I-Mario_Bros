package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class Goomba extends MovingObject {
    public Goomba() { super(); }
    public Goomba(GameWorld game, Position pos) { this(game, pos, Action.LEFT); }
    public Goomba(GameWorld game, Position pos, Action dir) { super(game, pos, dir); }
    @Override public boolean isSolid() { return false; }
    @Override public String getIcon() { return Messages.GOOMBA; }
    @Override public void update() { automaticMove(); }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Mario mario) {
        boolean hit = alive && (mario.isFalling() ? mario.isInLowerPosition(pos) : mario.isInPosition(pos));
        if (hit) {
            if (!mario.isFalling() || mario.canKillWhileFalling()) {
                alive = false;
                game.addPoints(100);
                if (mario.isFalling()) mario.registerFallingKill();
                if (!mario.canDefeatEnemySafely()) {
                    if (!(mario.isEnemyMovedCollision() && mario.hasJustFallen())) {
                        mario.alive = false;
                        game.marioDied();
                    }
                }
                else if (!mario.isFalling()) mario.afterSideEnemyDefeated();
            }
            else hit = false;
        }
        return hit;
    }
    @Override public String toString() { return pos.toString() + " Goomba " + dirString(); }
}
