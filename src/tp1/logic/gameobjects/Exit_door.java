package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class Exit_door {
	
	public Position pos;
	
	public Exit_door (Position pos)
	{
		this.pos = pos;
	}
	
	public String getIcon()
	{
		return Messages.EXIT_DOOR;
	}
	
	public Position getPosition()
	{
		return pos;
	}

}
