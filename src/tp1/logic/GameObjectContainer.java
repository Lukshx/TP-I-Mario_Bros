package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class GameObjectContainer {
    private List<GameObject> objects;
    private Mario mario;
    public GameObjectContainer() { objects = new ArrayList<>(); mario = null; }
    public void add(GameObject object) { object.addToContainer(this); }
    public void addObject(GameObject object) { objects.add(object); }
    public void addMario(Mario mario) { if (this.mario != null) objects.remove(this.mario); this.mario = mario; objects.add(mario); }
    public void setGame(GameWorld game) { for (GameObject object : objects) object.setGame(game); }
    public void addAction(Action action) { if (mario != null) mario.addAction(action); }
    public String positionToString(Position position) {
        String result = Messages.EMPTY;
        for (GameObject object : objects) if (object.isAlive() && object.isInPosition(position)) result = result + object.getIcon();
        return result;
    }
    public boolean isSolid(Position position) {
        boolean solid = false;
        int i = 0;
        while (i < objects.size() && !solid) {
            GameObject object = objects.get(i);
            if (object.isAlive() && object.isInPosition(position) && object.isSolid()) solid = true;
            i++;
        }
        return solid;
    }
    public void update() {
        int size = objects.size();
        if (mario != null && mario.isAlive()) {
            mario.update();
            if (!mario.hasJustFallen()) doInteractionsFrom(mario);
        }
        for (int i = 0; i < size; i++) {
            GameObject object = objects.get(i);
            if (object != mario && object.isAlive()) object.update();
        }
        if (mario != null && mario.isAlive()) {
            mario.setEnemyMovedCollision(true);
            doInteractionsFrom(mario);
            mario.setEnemyMovedCollision(false);
        }
        clean();
    }
    public void doInteractionsFrom(GameItem source) {
        int size = objects.size();
        int i = 0;
        while (i < size && source.isAlive()) {
            source.interactWith(objects.get(i));
            i++;
        }
    }
    public void doInteractionsFrom(Mario mario, Position target) {
        int size = objects.size();
        int i = 0;
        while (i < size && mario.isAlive()) {
            GameObject object = objects.get(i);
            if (object.isAlive() && object.isInPosition(target)) object.receiveInteraction(mario);
            i++;
        }
    }
    private void clean() {
        int i = 0;
        while (i < objects.size()) {
            if (!objects.get(i).isAlive()) objects.remove(i);
            else i++;
        }
    }
    @Override public String toString() {
        StringBuilder b = new StringBuilder();
        for (GameObject object : objects) if (object.isAlive()) b.append(object.toString()).append(Messages.LINE_SEPARATOR);
        return b.toString();
    }
}
