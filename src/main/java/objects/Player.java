package objects;

import environment.GameMap;

public class Player {
		public 				Tank 	tank;					// Tank of player
		protected 			String 	name;					// name of player
		protected 			float 	score;					// score of player
		public 	 	static 	int  	numberOfPlayers = 0;	//By default
		protected 			int 	playerNumber;			// number of the player
	
	public Player(GameMap gameMap){	
								
		++numberOfPlayers;
		playerNumber = numberOfPlayers;
		name = "Player" + playerNumber;			// By default name of the player is "Player" followed by his player number
		score = 0;
		tank = new Tank(this, gameMap);			//see comments of other constructor	
	}
	
	public Player(String name, GameMap gameMap){
		this.name = name;						// set name
		++numberOfPlayers;						// increase number of players
		playerNumber = numberOfPlayers;			// set player number
		score = 0;								// default score is zero
		
		tank = new Tank(this, gameMap);			//create new Tank
	}
	
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	public void increaseScore(float score){
		this.score += score;
	}
	
	public int getScore(){
		return Math.round(score);
	}

	public void decreaseScore(float score) {
		this.score -= score;
		
	}
	
}
