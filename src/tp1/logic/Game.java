package tp1.logic;

import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Exit_door;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class Game {

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private int time;
	private int points;
	private int lives;
	private int nLevel;
	
	//Nuevo
	
	private GameObjectContainer gameObjects;

	//TODO fill your code
	
	public Game(int nLevel) {
		this.nLevel = nLevel;
		this.time = 100;
		this.points = 0;
		this.lives = 3;
		this.gameObjects = new GameObjectContainer();
		initLevel0();
		// TODO Auto-generated constructor stub
	}
	
	public String positionToString(int col, int row) {
		// TODO Auto-generated method stub
		Position pos = new Position(row, col);
		Land land = gameObjects.getLandAt(pos);
		Goomba goomba = gameObjects.getGoombaAt(pos);
		Exit_door door = gameObjects.getDoorAt(pos);
		Mario mario = gameObjects.getMarioAt(pos);
		if(land != null)
		{
			return land.getIcon();
		}
		if(goomba != null)
		{
			return goomba.getIcon();
		}
		if(door != null)
		{
			return door.getIcon();
		}		
		if(mario!= null)
		{
			return mario.getIcon();
		}
		 return Messages.EMPTY;
	}

	public boolean playerWins() {
		// TODO Auto-generated method stub
		return false;
	}

	public int remainingTime() {
		// TODO Auto-generated method stub
		return 100;
	}

	public int points() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numLives() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String toString() {
		// TODO returns a textual representation of the object
		return "TODO: Hola soy el game";
	}

	public boolean playerLoses() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private void initLevel0() {
		this.nLevel = 0;
		this.time = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(new Position(13,col)));
			gameObjects.add(new Land(new Position(14,col)));		
		}

		gameObjects.add(new Land(new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(new Position(9,2)));
		gameObjects.add(new Land(new Position(9,5)));
		gameObjects.add(new Land(new Position(9,6)));
		gameObjects.add(new Land(new Position(9,7)));
		gameObjects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}
//
		gameObjects.add(new Exit_door(new Position(Game.DIM_Y-3, Game.DIM_X-1)));
//
//		// 3. Personajes
		gameObjects.add(new Mario(new Position(Game.DIM_Y-3, 0)));
//
		gameObjects.add(new Goomba(new Position(0, 19)));
	}

}
