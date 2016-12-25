package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environment.Region;

public class Brick extends GameObject{

	public static final int BRICK_IMAGE_WIDTH = 80;
	public static final int BRICK_IMAGE_HEIGHT = 40;
	
	public Brick(Region region){
		folderFilesPath = "src/main/resources/bricks/brick1.png";
		try {
			image = ImageIO.read(new File(folderFilesPath));
		} catch (IOException e) {
			System.err.println("Error loading brick image.");
			e.printStackTrace();
		}
	// pixel size of brick	
		this.region = region;
		
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	
	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(image, region.x - region.width/2, region.y - region.height/2, null);
		
	}

	@Override
	public void update(ObjectHandler handler) {
		// TODO Auto-generated method stub
		
	}

}
