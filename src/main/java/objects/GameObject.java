package objects;



import environment.GameMap;

public abstract class GameObject {

	protected float x;
	protected float y;
	private String type;
	protected float x_velocity;
	protected float y_velocity;
	protected GameMap map;
	
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void getX(float y){
		this.y = y;
	}
	
	
	public String getType(){
		return type;
	}
	
	
	public void setType(String type){
		this.type = type;
	}
	
	
	public void moveLeft(){
		this.x += this.x_velocity;
	}
	
	public void moveRight(){
		this.x -= this.x_velocity;
	}
	
	public void moveUp(){
		this.y += this.y_velocity;
	}
	
	public void moveDown(){
		this.y -= this.y_velocity;
	}
	
	
	public abstract void render();
	
	public abstract void update();
	
}
