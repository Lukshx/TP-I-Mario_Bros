package tp1.logic.configuration;

import tp1.exceptions.GameModelException;
import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.Mushroom;
import tp1.view.Messages;

public class LevelGameConfiguration implements GameConfiguration {
    private int level;
    public LevelGameConfiguration(int level) throws GameModelException { if (level < -1 || level > 2) throw new GameModelException(Messages.INVALID_LEVEL_NUMBER); this.level = level; }
    @Override public int remainingTime() { return 100; }
    @Override public int points() { return 0; }
    @Override public int numLives() { return 3; }
    @Override public GameObjectContainer createContainer(GameWorld game) throws GameModelException {
        GameObjectContainer container = new GameObjectContainer();
        if (level >= 0) { addBaseMap(container, game); addCharacters(container, game, level); }
        return container;
    }
    private void addBaseMap(GameObjectContainer container, GameWorld game) {
        for (int col = 0; col < 15; col++) { container.add(new Land(game, new Position(col, 13))); container.add(new Land(game, new Position(col, 14))); }
        container.add(new Land(game, new Position(9, 12)));
        container.add(new Land(game, new Position(12, 12)));
        for (int col = 17; col < Game.DIM_X; col++) { container.add(new Land(game, new Position(col, 13))); container.add(new Land(game, new Position(col, 14))); }
        container.add(new Land(game, new Position(2, 9)));
        container.add(new Land(game, new Position(5, 9)));
        container.add(new Land(game, new Position(6, 9)));
        container.add(new Land(game, new Position(7, 9)));
        container.add(new Land(game, new Position(6, 5)));
        int tamX = 8, posIniX = Game.DIM_X - 3 - tamX, posIniY = Game.DIM_Y - 3;
        for (int col = 0; col < tamX; col++) for (int row = 0; row < col + 1; row++) container.add(new Land(game, new Position(posIniX + col, posIniY - row)));
        container.add(new ExitDoor(game, new Position(Game.DIM_X - 1, Game.DIM_Y - 3)));
    }
    private void addCharacters(GameObjectContainer container, GameWorld game, int level) {
        container.add(new Mario(game, new Position(0, Game.DIM_Y - 3), Action.RIGHT, true));
        container.add(new Goomba(game, new Position(19, 0), Action.LEFT));
        if (level >= 1) {
            container.add(new Goomba(game, new Position(6, 4), Action.LEFT));
            container.add(new Goomba(game, new Position(10, 10), Action.LEFT));
            container.add(new Goomba(game, new Position(6, 12), Action.LEFT));
            container.add(new Goomba(game, new Position(8, 12), Action.LEFT));
            container.add(new Goomba(game, new Position(11, 12), Action.LEFT));
            container.add(new Goomba(game, new Position(14, 12), Action.LEFT));
        }
        if (level >= 2) {
            container.add(new Box(game, new Position(4, 9), true));
            container.add(new Mushroom(game, new Position(8, 12), Action.RIGHT));
            container.add(new Mushroom(game, new Position(20, 2), Action.RIGHT));
        }
    }
}
