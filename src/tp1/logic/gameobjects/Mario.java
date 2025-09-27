package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario {
	
	private Position pos;

	public Mario (Position pos)
	{
		this.pos = pos;
	}
	
	public Position getPosition()
	{
		return pos;
	}
	
	public String getIcon()
	{
		return Messages.MARIO_LEFT;
	}
	
	//TODO fill your code
	
	/**
	 *  Implements the automatic update	
	 */
	public void update() {
		//TODO fill your code
	}
}
