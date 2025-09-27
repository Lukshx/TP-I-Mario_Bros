package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Exit_door;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class GameObjectContainer {

	private List<Land> lands;	
	private List<Goomba> Goombas;
	private Exit_door Exit;
	private Mario mario;
	
	public GameObjectContainer() {
	    this.lands = new ArrayList<>();
	    this.Goombas = new ArrayList<>();
	    this.Exit = null;
	    this.mario = null;
	}
	
	public void add(Land land){
		lands.add(land);
	}
	public void add(Goomba goomba){
		Goombas.add(goomba);
	}
	public void add(Exit_door exit){
		this.Exit = exit;
	}
	public void add(Mario mario){
		
	}
	public Land getLandAt(Position pos)
	{
		for(Land land : lands)
		{
			if (land.getPosition().equals(pos)) {
	            return land;
	        }
		}
		return null;
	}
	
	public Goomba getGoombaAt(Position pos)
	{
		for(Goomba goomba : Goombas)
		{
			if (goomba.getPosition().equals(pos)) {
				return goomba;
			}
		}
		return null;
	}
	
	public Exit_door getDoorAt(Position pos)
	{
		Exit_door door = Exit;
		if(Exit != null && door.getPosition().equals(pos))
		{
			return Exit;
		}
		return null;
	}
	public Mario getMarioAt(Position pos)
	{
		Mario Mario = mario;
		if(mario != null && Mario.getPosition().equals(pos))
		{
			return mario;
		}
		return null;
	}
}
