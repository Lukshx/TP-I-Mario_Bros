package tp1.logic;

/**
 * 
 * TODO: Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	private int col;
	private int row;

	public Position (int col, int row)
	{
		this.col = col;
		this.row = row;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	//TODO fill your code

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return col == position.col && row == position.row;
    }
    
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
