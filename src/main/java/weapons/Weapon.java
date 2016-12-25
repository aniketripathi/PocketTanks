
package weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import environment.GameMap;
import environment.Region;
import objects.Explosion;
import objects.GameObject;
import objects.ObjectHandler;
import objects.Tank;
import utility.Signals;

public abstract class Weapon extends GameObject{


protected int maxScore;
protected int maxScoreRadius;
protected int type;
protected boolean isMoving;
protected Tank	parentTank;
protected int unitSecondCount = 0;
protected final int MAX_UNIT_SECOND_COUNT = 5;

public int getType(){
	return type;
}


public void update(ObjectHandler handler){
		
if(isMoving){
	Region gameRegion = gameMap.gameRegion;
		
	// inside game region , left right and below. Going above game region is allowed.
		if(region.x - region.width/2 > gameRegion.x - gameRegion.width/2	&& region.x + region.width/2 < gameRegion.x + gameRegion.width/2
			&& region.y + region.height/2 < gameRegion.y + gameRegion.height/2){
	
	for(GameObject gameObject: GameMap.collisionObjects){
			if(region.isColliding(gameObject.getRegion())){
				if((gameObject instanceof Tank && gameObject.equals(parentTank)))		continue;
				isMoving = false;
				handler.deleteGameRenderObject(this);
				handler.deleteGameUpdateObject(this);
				Explosion explosion = new Explosion(region.x, region.y);
				handler.addGameUpdateObject(explosion);
				handler.addGameRenderObject(explosion);
			}
	}
			
			region.x += x_velocity;
			region.y += y_velocity;
			++unitSecondCount;
		if(unitSecondCount == MAX_UNIT_SECOND_COUNT){	y_velocity += gameMap.gravity; unitSecondCount = 0;}
			}
		else {
			isMoving = false;
			handler.deleteGameUpdateObject(this);
			handler.deleteGameRenderObject(this);
			
			
		}
}
}


public abstract int getWeaponImageNumber();

public void setMoving(boolean isMoving){
	this.isMoving = isMoving;
}



}

