package weapons;

import environment.GameMap;
import objects.Tank;

public abstract class WeaponTypes {
	
	public static final int SINGLE_SHOT = 0;
	public static final int CUTTER		= 1;
	
	
	
	public static String getName(int type){
		
		switch(type){
			case SINGLE_SHOT:		return "Single Shot";
			case CUTTER		:		return "Cutter";
			 
			default: {
				System.err.println("Weapon not registered in WeaponTypes.");
				return null;
			}
	}
	
}
	
	
	public static void addWeaponsToTank(GameMap gameMap, Tank tank){
	
	for(int i = 0; i < 3; i++){	
		SingleShot singleShot = new SingleShot(gameMap,	tank);
		tank.addWeapon(singleShot);
		
		Cutter	cutter = new Cutter(gameMap, tank);
		tank.addWeapon(cutter);
	}
		
	}
	
}
