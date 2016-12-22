package objects;

import java.util.ArrayList;

import Weapons.Weapons;
import environment.Region;

public class Tank extends GameObject{
	
	private ArrayList<Weapons> weapons;
	protected float angle;
	protected int power ;
	private static int i = 1;
	public short tankNumber;
	
	public void addWeapon(Weapons weapon){
		weapons.add(weapon);
	}
	
	public void deleteWeapon(Weapons weapon){
		weapons.remove(weapon);
	}
	
	public void fireWeapon(Weapons weapon){
		weapon.fire(angle, power);
	}
	
	
	
	public void moveLeft(){
		
	}
	
	public void moveRight(){
		
	}
	
	public static int getTankImageNumber(Region region){
		return (region.x/10)%7 + 1;
		
	}
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
