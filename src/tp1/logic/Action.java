package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

public enum Action {
    LEFT, RIGHT, DOWN, UP, STOP;
    public static Action parse(String text) throws ActionParseException {
        Action action = find(text);
        if (action == null) throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(text));
        return action;
    }
    public static Action find(String text) {
        Action action = null;
        if (text != null) {
            String upper = text.toUpperCase();
            if ("LEFT".equals(upper) || "L".equals(upper)) action = LEFT;
            else if ("RIGHT".equals(upper) || "R".equals(upper)) action = RIGHT;
            else if ("DOWN".equals(upper) || "D".equals(upper)) action = DOWN;
            else if ("UP".equals(upper) || "U".equals(upper)) action = UP;
            else if ("STOP".equals(upper) || "S".equals(upper)) action = STOP;
        }
        return action;
    }
    public Action opposite() {
        Action opposite = this;
        if (this == LEFT) opposite = RIGHT;
        else if (this == RIGHT) opposite = LEFT;
        return opposite;
    }
}
