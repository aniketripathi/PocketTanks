package environment;

import java.util.ArrayList;

import objects.Brick;
import objects.GameObject;
import objects.Tank;

public class GameMap {

	public  int gravity;
	public  int leftWind;
	public  int rightWind;
	private int numberOfBricks;
	private  ArrayList<Region> bricksRegions;
	
	
	public Region gameRegion;
	public Region tank1Region;
	public Region tank2Region;
	
	public static ArrayList<GameObject> collisionObjects;
	public static final int GAME_MAP_TEMPLATE1 = 1;
	
	
	
	public GameMap(Region gameRegion, int game_map_template){
		this.gameRegion = gameRegion;
		
		bricksRegions = new ArrayList<Region>();
		collisionObjects = new ArrayList<GameObject>();
		
		switch(game_map_template){
		case  GAME_MAP_TEMPLATE1:{
		// set number of bricks	
			numberOfBricks = 16;
			
		// set bricks regions	
			for(int i = 0; i < numberOfBricks; i++)
				bricksRegions.add(new Region( Brick.BRICK_IMAGE_WIDTH/2 + i* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT/2, Brick.BRICK_IMAGE_WIDTH, Brick.BRICK_IMAGE_HEIGHT)); 	
		
		//set tank regions first tank begins from 3rd region and second tank begins from 12th region
			tank1Region = new Region(Brick.BRICK_IMAGE_WIDTH/2 + 3* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT - Tank.TANK_IMAGE_HEIGHT/2, Tank.TANK_IMAGE_WIDTH, Tank.TANK_IMAGE_HEIGHT);
			tank2Region = new Region(Brick.BRICK_IMAGE_WIDTH/2 + 12* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT - Tank.TANK_IMAGE_HEIGHT/2, Tank.TANK_IMAGE_WIDTH, Tank.TANK_IMAGE_HEIGHT);
		// set gravity , left and right wind
			gravity = 1; leftWind = 0; rightWind = 0;
		
		}
		
	}
	}		
		
		public ArrayList<Region> getBricksRegions(){
			return bricksRegions;
		}
		
	
	

}