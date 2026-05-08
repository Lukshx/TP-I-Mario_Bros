package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
public class Mario extends MovingObject {
    private static final int MAX_HORIZONTAL_ACTIONS = 4;
    private static final int MAX_VERTICAL_ACTIONS = 4;
    private boolean big;
    private Action[] pendingActions;
    private int numActions;
    private boolean falling;
    private boolean horizontalStep;
    private boolean enemyMovedCollision;
    private int fallingKills;
    private boolean recentlyFell;
    public Mario() { super(); big = true; pendingActions = new Action[8]; horizontalStep = false; enemyMovedCollision = false; }
    public Mario(GameWorld game, Position pos) { this(game, pos, Action.RIGHT, true); }
    public Mario(GameWorld game, Position pos, Action dir, boolean big) {
        super(game, pos, dir); this.big = big; pendingActions = new Action[8]; numActions = 0; falling = false; horizontalStep = false; enemyMovedCollision = false; fallingKills = 0; recentlyFell = false;
    }
    @Override public void addToContainer(GameObjectContainer container) { container.addMario(this); }
    public void addAction(Action action) { if (numActions < pendingActions.length) { pendingActions[numActions] = action; numActions++; } }
    @Override public boolean isSolid() { return false; }
    @Override public boolean isInPosition(Position position) { return alive && (pos.equals(position) || (big && pos.move(Action.UP).equals(position))); }
    @Override public String getIcon() { String icon = Messages.MARIO_STOP; if (dir == Action.RIGHT) icon = Messages.MARIO_RIGHT; else if (dir == Action.LEFT) icon = Messages.MARIO_LEFT; return icon; }
    @Override public void update() {
        falling = false;
        horizontalStep = false;
        enemyMovedCollision = false;
        fallingKills = 0;
        recentlyFell = false;
        boolean moved = executeActions();
        numActions = 0;
        if (!moved && alive) automaticMarioMove();
    }
    private boolean executeActions() {
        boolean moved = false;
        Action horizontal = null, vertical = null;
        boolean jumpCanStart = false;
        int nh = 0, nv = 0;
        for (int i = 0; i < numActions && alive; i++) {
            Action a = pendingActions[i];
            if (a == Action.LEFT || a == Action.RIGHT) {
                if ((horizontal == null || horizontal == a) && nh < MAX_HORIZONTAL_ACTIONS) { horizontal = a; nh++; moved = performAction(a) || moved; }
            }
            else if (a == Action.UP || a == Action.DOWN) {
                if ((vertical == null || vertical == a) && nv < MAX_VERTICAL_ACTIONS) {
                    if (vertical == null && a == Action.UP) jumpCanStart = !game.isInAir(pos);
                    vertical = a;
                    if (a != Action.UP || jumpCanStart) { nv++; moved = performAction(a) || moved; }
                }
            }
            else if (a == Action.STOP) dir = Action.STOP;
        }
        return moved;
    }
    private boolean performAction(Action action) {
        boolean moved = false;
        if (action == Action.LEFT || action == Action.RIGHT) {
            dir = action;
            moved = tryStep(action, false);
            if (!moved) dir = dir.opposite();
        }
        else if (action == Action.UP) moved = tryStep(action, false);
        else if (action == Action.DOWN) { if (game.isInAir(pos)) moved = fallUntilFloor(); else dir = Action.STOP; }
        return moved;
    }
    private void automaticMarioMove() {
        if (game.isInAir(pos)) tryStep(Action.DOWN, true);
        else if (dir != Action.STOP) { boolean moved = tryStep(dir, false); if (!moved) dir = dir.opposite(); }
    }
    private boolean fallUntilFloor() {
        boolean moved = false;
        while (alive && game.isInAir(pos)) moved = tryStep(Action.DOWN, true) || moved;
        return moved;
    }
    private boolean tryStep(Action action, boolean fall) {
        boolean moved = false;
        Position next = pos.move(action);
        falling = fall || action == Action.DOWN;
        horizontalStep = action == Action.LEFT || action == Action.RIGHT;
        if (!game.isInBoard(next)) { if (action == Action.DOWN) { alive = false; game.marioDied(); } }
        else if (canMoveTo(next)) { pos = next; moved = true; if (falling) recentlyFell = true; game.doInteractionsFrom(this); }
        else {
            Position target = next;
            if (action == Action.UP && big && !game.isSolid(next)) target = next.move(Action.UP);
            game.doInteractionsFrom(this, target);
        }
        falling = false;
        return moved;
    }
    private boolean canMoveTo(Position next) { return !game.isSolid(next) && (!big || game.isBigClear(next)); }
    public boolean isFalling() { return falling; }
    public boolean hasJustFallen() { return recentlyFell; }
    public boolean isInLowerPosition(Position position) { return alive && pos.equals(position); }
    public boolean canKillWhileFalling() { return fallingKills < (big ? 3 : 2); }
    public void registerFallingKill() { fallingKills++; }
    public boolean canDefeatEnemySafely() { return falling || big; }
    public void afterSideEnemyDefeated() { if (big) big = false; }
    public void grow() { big = true; }
    public boolean isBelow(Position position) { return pos.isBelow(position); }
    public void setEnemyMovedCollision(boolean enemyMovedCollision) { this.enemyMovedCollision = enemyMovedCollision; }
    public boolean isEnemyMovedCollision() { return enemyMovedCollision; }
    public boolean hitsFromBelow(Position position) { return !horizontalStep && (pos.isBelow(position) || (big && pos.move(Action.UP).isBelow(position))); }
    @Override public boolean interactWith(GameItem other) { return other != this && isAlive() && other.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Goomba obj) { return obj.receiveInteraction(this); }
    @Override public boolean receiveInteraction(ExitDoor obj) { return obj.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Mushroom obj) { return obj.receiveInteraction(this); }
    @Override public boolean receiveInteraction(Box obj) { return obj.receiveInteraction(this); }
    @Override public String toString() { return pos.toString() + " Mario " + dirString() + " " + (big ? "Big" : "Small"); }
}
