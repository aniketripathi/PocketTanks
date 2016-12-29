package explosion;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environment.GameMap;
import environment.Region;
import objects.GameObject;
import objects.ObjectHandler;
import objects.Tank;
import utility.Signals;
import weapons.Weapon;

public class NukeExplosion extends Explosion {
	
	private final 	int 	EXPLOSION_SIZE_INCREASE_FACTOR = 2;
	private 		int 	updateTimes;
	private final	int 	TOTAL_IMAGES = 50;
	
	
public NukeExplosion(Weapon parentWeapon, GameMap gameMap, int x, int y){
	
		
		
		//set parent Weapon
			this.parentWeapon = parentWeapon;
		
		//set gameMap
		this.gameMap = gameMap;

		//set image height and width
		EXPLOSION_IMAGE_WIDTH = 2;
		EXPLOSION_IMAGE_HEIGHT = 2;
		
		//set region
		region = new Region(x, y, EXPLOSION_IMAGE_WIDTH, EXPLOSION_IMAGE_HEIGHT);
		
		//set image related properties

		folderFilesPath = "src/main/resources/explosions/nuke_explosion/explosion01.png";
		fileNames = new StringBuffer(folderFilesPath);
		EXPLOSION_IMAGE_INDEX = 54;
		
		//set updateTimes;
		updateTimes = 1;
		
	}

	public int getImageNumber(int factor){
	if(factor < 10)		return factor;				
	return factor;
}


	@Override
	public void render(Graphics2D graphics) {
		//get image first
		if(updateTimes < 10)		fileNames.replace(EXPLOSION_IMAGE_INDEX + 1, EXPLOSION_IMAGE_INDEX + 2, getImageNumber(updateTimes)+"");
		else 						fileNames.replace(EXPLOSION_IMAGE_INDEX, EXPLOSION_IMAGE_INDEX + 2, "" + getImageNumber(updateTimes));
		++updateTimes;
			try {
				image = ImageIO.read(new File(fileNames.toString()));
			} catch (IOException e) {
				System.err.println("Error loading image of nuke explosion.");
				e.printStackTrace();
			}
			
			graphics.drawImage(image, region.x - region.width/2, region.y - region.height/2, null);
			
		}
		
	

	@Override
	public void update(ObjectHandler handler) {
		if(updateTimes <= TOTAL_IMAGES){
			region.width = updateTimes * EXPLOSION_SIZE_INCREASE_FACTOR;
			region.height = updateTimes * EXPLOSION_SIZE_INCREASE_FACTOR;
			
			for(GameObject gameObject: GameMap.collisionObjects){
					if(gameObject instanceof Tank && region.isColliding(gameObject.getRegion()) && !gameObject.equals(this.parentWeapon.getParentTank())){
						this.parentWeapon.getParentTank().getParentPlayer().increaseScore(parentWeapon.getDamage(gameObject.getRegion(), region, region.width));
					}
					else if(gameObject instanceof Tank && region.isColliding(gameObject.getRegion()) && gameObject.equals(this.parentWeapon.getParentTank())){
						this.parentWeapon.getParentTank().getParentPlayer().decreaseScore(parentWeapon.getDamage(gameObject.getRegion(), region, region.width));
					}
			}
			
		}
		else {
			handler.deleteGameUpdateObject(this);
			handler.deleteGameRenderObject(this);
			Signals.changeTurn = true;
		}
		
	}

}
