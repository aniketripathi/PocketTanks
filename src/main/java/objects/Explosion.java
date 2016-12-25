package objects;

import environment.Region;
import utility.Signals;

public class Explosion extends GameObject{

	private final int EXPLOSION_IMAGE_HEIGHT = 0;
	private final int EXPLOSION_IMAGE_WIDTH = 0;
	
	public Explosion(int x, int y){
		
		//set region
		this.region = new Region(x, y, EXPLOSION_IMAGE_WIDTH, EXPLOSION_IMAGE_HEIGHT);
		
		
		
	}
	

	@Override
	public void update(ObjectHandler handler) {
	//update region
		
	//check if region collides with the tank	
		for(GameObject object: handler.getGameUpdateObjects()){
			if(object instanceof Tank && object.region.isColliding(this.region)){
				Tank tank = (Tank) object;
				Signals.updateScoreSignal = tank.numberOfTanks;
				Signals.updateScoreValue = 10;
			}
		}
		
	}

	@Override
	public void render(java.awt.Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}

}
