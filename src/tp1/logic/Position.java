package tp1.logic;

import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

public final class Position {
    private final int col;
    private final int row;
    public Position(int col, int row) { this.col = col; this.row = row; }
    public Position move(Action action) {
        Position next = this;
        if (action == Action.LEFT) next = new Position(col - 1, row);
        else if (action == Action.RIGHT) next = new Position(col + 1, row);
        else if (action == Action.UP) next = new Position(col, row - 1);
        else if (action == Action.DOWN) next = new Position(col, row + 1);
        return next;
    }
    public boolean isInBoard(int width, int height) { return 0 <= col && col < width && 0 <= row && row < height; }
    public boolean isBelow(Position other) { return this.equals(other.move(Action.DOWN)); }
    public static Position parse(String text) throws PositionParseException {
        try {
            if (text == null || !text.startsWith("(") || !text.endsWith(")")) throw new PositionParseException(Messages.INVALID_POSITION.formatted(text));
            String inner = text.substring(1, text.length() - 1);
            String[] parts = inner.split(",");
            if (parts.length != 2) throw new PositionParseException(Messages.INVALID_POSITION.formatted(text));
            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());
            return new Position(col, row);
        }
        catch (NumberFormatException e) { throw new PositionParseException(Messages.INVALID_POSITION.formatted(text), e); }
    }
    @Override
    public boolean equals(Object obj) {
        boolean same = false;
        if (this == obj) same = true;
        else if (obj != null && getClass() == obj.getClass()) {
            Position other = (Position) obj;
            same = col == other.col && row == other.row;
        }
        return same;
    }
    @Override
    public int hashCode() { return 31 * row + col; }
    @Override
    public String toString() { return Messages.POSITION.formatted(row, col); }
}
