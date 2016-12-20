package objects;

import java.util.ArrayList;

import Weapons.Weapons;

public class Tank extends GameObject{
	
	public String name;
	private ArrayList<Weapons> weapons;
	protected int score;
	protected float angle;
	protected int power ;
	private static int i = 1;
	
	public Tank(String name){
		this.name = name;
	}
	
	public Tank(){
		this.name = "Player" + i++; 
	}
	
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
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
