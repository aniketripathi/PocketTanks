package weapons;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environment.GameMap;
import environment.Region;
import explosion.ExplosionTypes;
import objects.Tank;

public class Cutter extends Weapon{
	
	
	public	static final int 	WEAPON_IMAGE_WIDTH = 24;
	public 	static final int 	WEAPON_IMAGE_HEIGHT = 24;
	private 	   final int 	WEAPON_IMAGE_INDEX = 40;
	
	public Cutter(GameMap gameMap, Tank tank){
		type = WeaponTypes.CUTTER;

		//set explosion type
		explosionType = ExplosionTypes.NUKE_EXPLOSION;
		
		//set damage
		damage = 2.3f;
		
		// set parent tank
			parentTank = tank;
		
		// set gameMap
		this.gameMap = gameMap;
		
		// set velocities	
		x_velocity = 0;
		y_velocity = 0;	
		
		// related to region
	region = new Region(0,0,WEAPON_IMAGE_WIDTH, WEAPON_IMAGE_HEIGHT);
	region.x = parentTank.getWeaponFiringPointX();
	region.y = parentTank.getWeaponFiringPointY();
	
	//related to image
			folderFilesPath = "src/main/resources/weapons/cutter/cutter1.png";
			fileNames = new StringBuffer(folderFilesPath);
			
	}

	@Override
	public int getWeaponImageNumber() {
		return (region.getX()/10)%5 + 1;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		//get image first
		fileNames.replace(WEAPON_IMAGE_INDEX, WEAPON_IMAGE_INDEX+1, getWeaponImageNumber()+"");
		
		try {
			image = ImageIO.read(new File(fileNames.toString()));
		} catch (IOException e) {
			System.err.println("Error loading cutter image");
			e.printStackTrace();
		}
		
		graphics.drawImage(image, region.getX() - region.width/2, region.getY() - region.height/2, null);
	}



}
