package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba {
	
	private Position pos;
	
	public Goomba(Position pos){
		this.pos = pos;
	}
	
	public String getIcon()
	{
		return Messages.GOOMBA;
	}
	
	public Position getPosition()
	{
		return pos;
	}

}
