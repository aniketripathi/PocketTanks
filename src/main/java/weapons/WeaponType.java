package weapons;

import environment.GameMap;
import objects.Tank;

public enum WeaponType {

/**
 * Declare the types of weapons here
 */
	SINGLE_SHOT, CUTTER;
	
	public static String getName(WeaponType type){
		
		switch(type){
			case SINGLE_SHOT:		return "Single Shot";
			case CUTTER		:		return "Cutter";
			 
			default: {
				System.err.println("Weapon not registered in WeaponType.");
				return "Not-Registered-Weapon";
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
