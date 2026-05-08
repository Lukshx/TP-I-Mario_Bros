package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class GameObjectFactory {
    public static GameObject parse(String[] objWords, GameWorld game) throws ObjectParseException {
        String description = String.join(" ", objWords);
        if (objWords.length < 2) throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(description));
        Position pos = parsePosition(objWords[0], description);
        String type = objWords[1].toUpperCase();
        GameObject object;
        if ("LAND".equals(type) || "L".equals(type)) object = parseLand(objWords, game, pos, description);
        else if ("EXITDOOR".equals(type) || "ED".equals(type)) object = parseExitDoor(objWords, game, pos, description);
        else if ("GOOMBA".equals(type) || "G".equals(type)) object = parseGoomba(objWords, game, pos, description);
        else if ("MARIO".equals(type) || "M".equals(type)) object = parseMario(objWords, game, pos, description);
        else if ("MUSHROOM".equals(type) || "MU".equals(type)) object = parseMushroom(objWords, game, pos, description);
        else if ("BOX".equals(type) || "B".equals(type)) object = parseBox(objWords, game, pos, description);
        else throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(description));
        return object;
    }
    private static Position parsePosition(String word, String description) throws ObjectParseException {
        try { return Position.parse(word); }
        catch (PositionParseException e) { throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(description), e); }
    }
    private static GameObject parseLand(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException { checkMax(words, 2, description); return new Land(game, pos); }
    private static GameObject parseExitDoor(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException { checkMax(words, 2, description); return new ExitDoor(game, pos); }
    private static GameObject parseGoomba(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException { checkMax(words, 3, description); Action dir = Action.LEFT; if (words.length == 3) dir = parseMovingDirection(words[2], description); return new Goomba(game, pos, dir); }
    private static GameObject parseMushroom(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException { checkMax(words, 3, description); Action dir = Action.RIGHT; if (words.length == 3) dir = parseMovingDirection(words[2], description); return new Mushroom(game, pos, dir); }
    private static GameObject parseMario(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException {
        checkMax(words, 4, description);
        Action dir = Action.RIGHT;
        boolean big = true;
        if (words.length >= 3) dir = parseMarioDirection(words[2], description);
        if (words.length == 4) big = parseMarioSize(words[3], description);
        return new Mario(game, pos, dir, big);
    }
    private static GameObject parseBox(String[] words, GameWorld game, Position pos, String description) throws ObjectParseException {
        checkMax(words, 3, description);
        boolean full = true;
        if (words.length == 3) {
            String status = words[2].toUpperCase();
            if ("FULL".equals(status) || "F".equals(status)) full = true;
            else if ("EMPTY".equals(status) || "E".equals(status)) full = false;
            else throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(description));
        }
        return new Box(game, pos, full);
    }
    private static Action parseMovingDirection(String text, String description) throws ObjectParseException {
        Action action;
        try { action = Action.parse(text); }
        catch (ActionParseException e) { throw new ObjectParseException(Messages.UNKNOWN_MOVING_OBJECT_DIRECTION.formatted(description), e); }
        if (action != Action.LEFT && action != Action.RIGHT) throw new ObjectParseException(Messages.INVALID_MOVING_OBJECT_DIRECTION.formatted(description));
        return action;
    }
    private static Action parseMarioDirection(String text, String description) throws ObjectParseException {
        Action action;
        try { action = Action.parse(text); }
        catch (ActionParseException e) { throw new ObjectParseException(Messages.UNKNOWN_MOVING_OBJECT_DIRECTION.formatted(description), e); }
        if (action != Action.LEFT && action != Action.RIGHT && action != Action.STOP) throw new ObjectParseException(Messages.INVALID_MOVING_OBJECT_DIRECTION.formatted(description));
        return action;
    }
    private static boolean parseMarioSize(String text, String description) throws ObjectParseException {
        String size = text.toUpperCase();
        if ("BIG".equals(size) || "B".equals(size)) return true;
        if ("SMALL".equals(size) || "S".equals(size)) return false;
        throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(description));
    }
    private static void checkMax(String[] words, int max, String description) throws ObjectParseException { if (words.length > max) throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MUCH_ARGS.formatted(description)); }
}
