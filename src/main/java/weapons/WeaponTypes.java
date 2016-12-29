package weapons;

public abstract class WeaponNames {
	
	public static final int SINGLE_SHOT = 0;
	public static final int CUTTER		= 1;
	
	
	
	public static String getName(int type){
		
		switch(type){
			case SINGLE_SHOT:		return "Single Shot";
			case CUTTER		:		return "Cutter";
			 
			default: {
				System.err.println("Weapon not registered in WeaponNames.");
				return null;
			}
	}
	
}
	
}
