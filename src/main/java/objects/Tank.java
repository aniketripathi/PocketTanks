package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import environment.GameMap;
import environment.Region;
import weapons.Weapon;
import weapons.WeaponTypes;

public class Tank extends GameObject{
	
	//basic properties
	private 		ArrayList<Weapon> 	weapons;
	protected 		float 				angle;
	protected 		int 				power ;
	protected 		Player 				parentPlayer;			// parent of this tank
			
	
	//related to image
	public	static final int 	TANK_IMAGE_WIDTH = 64;
	public 	static final int 	TANK_IMAGE_HEIGHT = 32;
	private 	   final int 	TANK_IMAGE_INDEX2 = 29;
	private 	   final int 	TANK_IMAGE_INDEX1 = 23;
	public 	static final int 	tankGunLength = TANK_IMAGE_HEIGHT/2 - 1;		// 15 pixels
	
	//related to movement
	private static final int 	MOVE_LENGTH = 1;
	public int 					moves;
	private static final int 	SINGLE_MOVE_LIMIT = 100;
	private boolean 			isMovingLeft;
	private boolean 			isMovingRight;
	private int 				singleLeftMoves;
	private int 				singleRightMoves;
	
	//related to power
	public static final float 	POWER_TO_VELOCITY_FACTOR = 5.3f; 
	
	public Tank(Player player, GameMap gameMap){
		// set gameMap	
		this.gameMap = gameMap;
			
		//set properties related to moving
		moves = 5;
		singleLeftMoves = 0;
		singleRightMoves = 0;
		isMovingLeft = false;
		isMovingRight = false;
		
		
	
	// set default power and angle	
		angle = 60;
		power = 50;
	
	// set parent player	
		parentPlayer  = player;
		
		
		// set spawning region of tanks	
		if(parentPlayer.getPlayerNumber() == 1)
				this.region = gameMap.tank1Region;
		else 	this.region = gameMap.tank2Region;		
					
		
	// For tank image file path and tank image	
		folderFilesPath = "src/main/resources/tank1/tank1.png";
		fileNames = new StringBuffer();
		fileNames.append(folderFilesPath);
		
		
		fileNames.replace(TANK_IMAGE_INDEX1, TANK_IMAGE_INDEX1+1, parentPlayer.getPlayerNumber()+ ""); // folder of tank	
		
		// create weapons list
		weapons = new ArrayList<Weapon>();		// create new weapons list
		WeaponTypes.addWeaponsToTank(gameMap, this);
	
	}
	
	
	public Player getParentPlayer(){
		return parentPlayer;
	}
	

	
	
	
	public void setProperties(int angle, int power){
		this.angle = angle;
		this.power = power;
	}
	
	
	
	
	
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}
	
	
	
	
	public void deleteWeapon(Weapon weapon){
		weapons.remove(weapon);
	}
	
	
	
	
	public void fireWeapon(Weapon weapon, ObjectHandler handler){
		//update weapons initial co-ordinates
		weapon.region.x = getWeaponFiringPointX();
		weapon.region.y = getWeaponFiringPointY();
	
		//set weapon's initial velocity determined by power of the tank
		weapon.x_velocity = (float) ( power * Math.cos(Math.toRadians(angle))/POWER_TO_VELOCITY_FACTOR );
		weapon.y_velocity = (float) -( power * Math.sin(Math.toRadians(angle))/POWER_TO_VELOCITY_FACTOR );
		
		
		// weapon movement enabled
		weapon.setMoving(true);
	//add to game handlers update and render list	
		handler.addGameUpdateObject(weapon);
		handler.addGameRenderObject(weapon);	
		deleteWeapon(weapon);		// delete from weapons list
		
	}
	
	
	
	
	
	public ArrayList<Weapon> getWeaponsList(){
		return weapons;
	}
	
	
	
	
	private static int getTankImageNumber(Region region){
		return (region.getX()/10)%8 + 1;			// to determine image number based on the position of the tank
		
	}
	
	
	
	
	public void moveLeft(){
	if(moves > 0){
		singleLeftMoves += SINGLE_MOVE_LIMIT; 	//add unit moves 
		isMovingLeft = true;					// set left moving to true
		--moves;							// reduce no of moves
		}	
	}
	
	
	
	public void moveRight(){
		if(moves > 0){
		singleRightMoves += SINGLE_MOVE_LIMIT;	// add unit moves
		isMovingRight = true;					// set right moving to true;
		--moves;								// reduce no of moves;
		}
	}
	
	
	
	
	@Override
	public void render(Graphics2D graphics) {
		
		// get image 
		fileNames.replace(TANK_IMAGE_INDEX2, TANK_IMAGE_INDEX2+1, getTankImageNumber(region)+"");
		try {
			image = ImageIO.read(new File(fileNames.toString()));
		} catch (IOException e) {
			System.err.print(fileNames.toString());
			System.err.println("Error loading tank" + parentPlayer.getPlayerNumber()+" images.");
			e.printStackTrace();
		}
		
		// draw tank
		graphics.drawImage(image, region.getX() - region.width/2, region.getY() - region.height/2, null);
		
		//draw tank gun, change the color of graphics and then set the color back.
		Color color = graphics.getColor();
		graphics.setColor(Color.WHITE);
		graphics.drawLine(region.getX(), region.getY(), getWeaponFiringPointX(), getWeaponFiringPointY());
		graphics.setColor(color);	// set back the original color
		}

	

	public int getWeaponFiringPointX(){
		return (int) Math.round(this.region.x + Tank.tankGunLength * Math.cos(Math.toRadians(angle)));		// gives the x co-ordinate of the end of tank's gun
	}
	
	public int getWeaponFiringPointY(){
		return (int) (this.region.y  - Tank.tankGunLength * Math.sin(Math.toRadians(angle)));		// gives the y co-ordinate of the end of tank's gun
	}
	
	
	@Override
	public void update(ObjectHandler handler) {
			if(isMovingLeft){		// moving left enabled
			
			if(singleLeftMoves > 0){
			   //moves a single unit of MOVE_LENGTH only if inside game Region
				if(region.getX() - region.width/2 >= gameMap.gameRegion.getX() - gameMap.gameRegion.width/2){	
					region.x -= MOVE_LENGTH;
					--singleLeftMoves;
			   }
				//if outside region
			   else singleLeftMoves = 0;
		   }
		   else {	// no more single moves left
			   
			   handler.deleteGameUpdateObject(this);		// delete tank from object handler's update list 
			   isMovingLeft = false;						// moving disabled
		   }
		}
		

	if(isMovingRight){										// moving right enabled
		   if(singleRightMoves > 0){						
			   	//move a single unit of MOVE_LENGTH only if inside game Region
			   if(region.getX() + region.width/2 <= gameMap.gameRegion.getX() + gameMap.gameRegion.width/2){
				region.x += MOVE_LENGTH;
				--singleRightMoves;
				}
			   //outside region
			else singleRightMoves = 0;
		}
		   // no more single moves left
		else {
			handler.deleteGameUpdateObject(this);		// delete from handler's update list
			isMovingRight = false;						// moving disabled
		}
	}
		
	}
	
}
