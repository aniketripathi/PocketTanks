package objects;

import environment.GameMap;
import environment.Region;

public class Player {
	public Tank tank;
	protected String name;
	protected int score;
	public static int  numberOfPlayers = 0;		//By default
	private int playerNumber = 1;
	
	public Player(GameMap gameMap){
		tank = new Tank(this, gameMap);
		name = null;
		numberOfPlayers++;
		playerNumber = numberOfPlayers;
		score = 0;
	}
	
	public Player(String name, GameMap gameMap){
		tank = new Tank(this, gameMap);
		this.name = name;
		numberOfPlayers++;
		playerNumber = numberOfPlayers;
		score = 0;
	}
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
}
