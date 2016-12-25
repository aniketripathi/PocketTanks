package objects;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import environment.GameMap;
import environment.Region;

public abstract class GameObject {

	protected Region region;
	protected int x_velocity;
	protected int y_velocity;
	protected StringBuffer fileNames;
	protected BufferedImage image;
	protected String folderFilesPath = null;	
	protected GameMap gameMap;
	
	
	
	public Region getRegion(){
		return region;
	}
	
	public abstract void render(Graphics2D graphics);
	
	public abstract void update(ObjectHandler handler);


	





}
