
package weapons;

import environment.GameMap;
import environment.Region;
import explosion.Explosion;
import explosion.ExplosionTypes;
import objects.GameObject;
import objects.ObjectHandler;
import objects.Tank;
import utility.Signals;

@SuppressWarnings("unused")
public abstract class Weapon extends GameObject{


protected int type;
protected boolean isMoving;
protected Tank	parentTank;
protected int unitSecondCount = 0;
protected final int MAX_UNIT_SECOND_COUNT = 4;
protected int explosionType;
protected float damage;

public int getType(){
	return type;
}


public void update(ObjectHandler handler){
		
if(isMoving){
	Region gameRegion = gameMap.gameRegion;
			
	// inside game region , left right and below. Going above game region is allowed.
		if(region.getX() - region.width/2 > gameRegion.getX() - gameRegion.width/2	&& region.getX() + region.width/2 < gameRegion.getX() + gameRegion.width/2
			&& region.getY() + region.height/2 < gameRegion.getY() + gameRegion.height/2){
			
	for(GameObject gameObject: GameMap.collisionObjects){					// scan through collision objects
			if(region.isColliding(gameObject.getRegion())){							
				if((gameObject instanceof Tank && gameObject.equals(parentTank)))		continue;			// weapon collided through parent tank then ignore
				isMoving = false;																				
				handler.deleteGameRenderObject(this);
				handler.deleteGameUpdateObject(this);
				Explosion explosion = Explosion.getExplosionInstance(this, explosionType, this.gameMap, region.x, region.y);
				handler.addGameUpdateObject(explosion);
				handler.addGameRenderObject(explosion);
				return;
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
			Signals.changeTurn = true;
		}
}
}

public float getDamage(Region tankRegion, Region explosionRegion, int explosionRadius){		// region of tank
float factor = (float) (	Math.sqrt(tankRegion.width * tankRegion.width + tankRegion.height * tankRegion.height ) 
	+  explosionRadius - 	Math.sqrt(Math.pow(tankRegion.x - explosionRegion.x, 2) + Math.pow(tankRegion.y - explosionRegion.y, 2)));
	return (factor * damage / 100);
}


public Tank getParentTank(){
	return this.parentTank;
}

public abstract int getWeaponImageNumber();

public void setMoving(boolean isMoving){
	this.isMoving = isMoving;
}



}

