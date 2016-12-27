
package weapons;

import environment.GameMap;
import environment.Region;
import explosion.Explosion;
import explosion.ExplosionNames;
import objects.GameObject;
import objects.ObjectHandler;
import objects.Tank;
import utility.Signals;

public abstract class Weapon extends GameObject{


protected int type;
protected boolean isMoving;
protected Tank	parentTank;
protected int unitSecondCount = 0;
protected final int MAX_UNIT_SECOND_COUNT = 5;
protected int explosionType;
protected float damage;
protected int velocityUpdatePeriod;

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
				Explosion explosion = Explosion.getExplosionInstance(this, ExplosionNames.SMALL_EXPLOSION, this.gameMap, region.x, region.y);
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

