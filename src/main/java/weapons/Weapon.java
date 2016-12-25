package Weapons;

import objects.GameObject;

public abstract class Weapons extends GameObject{
	
protected int maxScore;
protected int maxScoreRadius;

public abstract void fire(float angle, int power);

}


