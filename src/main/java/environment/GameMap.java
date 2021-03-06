package environment;

import java.util.ArrayList;

import objects.Brick;
import objects.GameObject;
import objects.Tank;
import windows.Frames;

public class GameMap {

// environmental factors
	public  int gravity;
	public  int leftWind;			// not in use 
	public  int rightWind;			// not in use
	
// bricks	
	private int numberOfBricks;
	private  ArrayList<Region> bricksRegions;
	
// spawning points of tanks	
	public Region gameRegion;
	public Region tank1Region;
	public Region tank2Region;
	
// objects which don't die and are responsible for collisions	
	public static ArrayList<GameObject> collisionObjects;		/******* TODO make it non - static ********/
		
// game template is a unique id for a map	
	public static final int GAME_MAP_TEMPLATE1 = 1;
	
	
	
	public GameMap(Region gameRegion, int game_map_template){
	
		//set game region
		this.gameRegion = gameRegion;
		
		// create array lists
		bricksRegions = new ArrayList<Region>();
		collisionObjects = new ArrayList<GameObject>();
		
		switch(game_map_template){
		case  GAME_MAP_TEMPLATE1:{
		// set number of bricks	
			numberOfBricks = Frames.WIDTH/Brick.BRICK_IMAGE_WIDTH + 1;
			
		// set bricks regions	
			for(int i = 0; i < numberOfBricks; i++)
				bricksRegions.add(new Region( Brick.BRICK_IMAGE_WIDTH/2 + i* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT/2, Brick.BRICK_IMAGE_WIDTH, Brick.BRICK_IMAGE_HEIGHT)); 	
		
		//set tank regions first tank begins from 3rd region and second tank begins from 12th region
			tank1Region = new Region(Brick.BRICK_IMAGE_WIDTH/2 + 3* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT - Tank.TANK_IMAGE_HEIGHT/2, Tank.TANK_IMAGE_WIDTH, Tank.TANK_IMAGE_HEIGHT);
			tank2Region = new Region(Brick.BRICK_IMAGE_WIDTH/2 + (numberOfBricks - 1 - 3)* Brick.BRICK_IMAGE_WIDTH, gameRegion.height - Brick.BRICK_IMAGE_HEIGHT - Tank.TANK_IMAGE_HEIGHT/2, Tank.TANK_IMAGE_WIDTH, Tank.TANK_IMAGE_HEIGHT);
		// set gravity , left and right wind
			gravity = 1; leftWind = 0; rightWind = 0;
		
		}
		
	}
	}		
		
		public ArrayList<Region> getBricksRegions(){
			return bricksRegions;
		}
		
	
	

}
