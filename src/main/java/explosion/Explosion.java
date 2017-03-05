package explosion;

import environment.GameMap;
import objects.GameObject;
import weapons.Weapon;

public abstract class Explosion extends GameObject{
	
	protected ExplosionType type;
	protected int EXPLOSION_IMAGE_HEIGHT;
	protected int EXPLOSION_IMAGE_WIDTH;
	protected int EXPLOSION_IMAGE_INDEX;
	protected Weapon parentWeapon;
	
	
	
	public static Explosion getExplosionInstance(Weapon parentWeapon, ExplosionType explosionType, GameMap gameMap, float x, float y){
		switch(explosionType){
		case SMALL_EXPLOSION :
			return new SmallExplosion(parentWeapon, gameMap, x, y);
			
		case NUKE_EXPLOSION :
			return new NukeExplosion(parentWeapon, gameMap, x, y);
			
		default: return null;	
		}
		
	}
	
	
	
	
}
