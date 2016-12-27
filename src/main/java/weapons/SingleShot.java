package weapons;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environment.GameMap;
import environment.Region;
import explosion.ExplosionNames;
import objects.Tank;

public class SingleShot extends Weapon{

	public	static final int 	WEAPON_IMAGE_WIDTH = 16;
	public 	static final int 	WEAPON_IMAGE_HEIGHT = 16;
	private 	   final int 	WEAPON_IMAGE_INDEX = 51;
	
	public SingleShot(GameMap gameMap, Tank tank){
		type = WeaponNames.SINGLE_SHOT;
		
		velocityUpdatePeriod = 2;
		
		//set damage
			damage = 1;
			
		// set parent tank
			parentTank = tank;
		
		// set gameMap
		this.gameMap = gameMap;
		
		
		// set velocities	
		x_velocity = 0;
		y_velocity = 0;	
		
		//set explosion type
		explosionType = ExplosionNames.SMALL_EXPLOSION;
		
		// related to region
				region = new Region(0,0,WEAPON_IMAGE_WIDTH, WEAPON_IMAGE_HEIGHT);
				region.x = parentTank.getWeaponFiringPointX();
				region.y = parentTank.getWeaponFiringPointY();
				
				//related to image
				folderFilesPath = "src/main/resources/weapons/single_shot/single_shot_1.png";
				fileNames = new StringBuffer(folderFilesPath);
				
	}

	

	@Override
	public void render(Graphics2D graphics) {
		//get image first
				fileNames.replace(WEAPON_IMAGE_INDEX, WEAPON_IMAGE_INDEX + 1, getWeaponImageNumber()+"");
				try {
					image = ImageIO.read(new File(fileNames.toString()));
				} catch (IOException e) {
				System.err.println("Error loading single_shot image.");
					e.printStackTrace();
				}
				
				graphics.drawImage(image, region.x - region.width/2, region.y - region.height/2, null);
				
		
	}
	
	


	@Override
	public int getWeaponImageNumber() {
		return (region.x/10)%4 + 1;
	}

}
