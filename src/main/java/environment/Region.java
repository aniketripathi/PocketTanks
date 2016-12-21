package environment;

import java.awt.Graphics;



public class Region {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Region(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public Region(){
		this.x = 0;
		this.y = 0;
	// unit region
		this.width = 1;
		this.height = 1;
	}
	
	// copy constructor
	public Region(Region region){
		this.x = region.x;
		this.y = region.y;
		this.width = region.width;
		this.height = region.height;
	}
	
	public int getArea(){
		return (this.width * this.height);
	}
	
	
	public int getArea(int percentage){
		return (percentage /100) * this.getArea();
	}
	
	public Region getRegion(int width_percentage, int height_percentage){
		return new Region(this.x, this.y, this.width * (width_percentage/100), this.height * (height_percentage /100));
	}
	
	
	public int getUnitX(int blocks){
		return (this.width/blocks);
	}
	
	public int getUnitY(int blocks){
		return (this.height/blocks);
	}
	
	
	public boolean isCoinciding(Region region2){
		return (this.x == region2.x && this.y == region2.y);
	}
	
	
	public void drawGrid(Graphics graphics, int x_blocks, int y_blocks){
		
		int unitX = this.getUnitX(x_blocks), unitY = this.getUnitY(y_blocks);
		
		for(int i = 1; i < x_blocks; i++) 
			graphics.drawLine(this.x - this.width/2, unitY * i, this.width, unitY * i);
		
		for(int i = 1; i < y_blocks; i++) 
			graphics.drawLine(unitX * i, this.y - this.height/2, unitX * i, this.y + this.height/2);
		
	}
	
	public void draw(Graphics graphics){
		graphics.drawLine(this.x - this.width/2, this.y - this.height/2, this.x + this.width/2, this.y - this.height/2);
		graphics.drawLine(this.x + this.width/2, this.y - this.height/2, this.x + this.width/2, this.y + this.height/2);
		graphics.drawLine(this.x + this.width/2, this.y + this.height/2, this.x - this.width/2, this.y + this.height/2);
		graphics.drawLine(this.x - this.width/2, this.y + this.height/2, this.x - this.width/2, this.y - this.height/2);
	}
	
	
	public void clearRegion(Graphics graphics){
		graphics.clearRect(this.x, this.y, this.width, this.height);
	}
	
	
	public boolean isColliding(Region region2){
		return ( Math.abs(this.x - region2.x) <= (this.width/2 + region2.width/2)  &&
	 			 Math.abs(this.y - region2.y) <= (this.height/2 + region2.height/2));
	}
	
	
	public boolean isInside(Region region){
		return (this.x + this.width/2 <= region.x + region.width/2 && this.y + this.height/2 <= region.y + region.height/2
			&&  this.x - this.width/2 >= region.x - region.width/2 && this.y - this.height/2 >= region.y - region.height/2);
	}
	
	public boolean isOutside(Region region){
		return (!isInside(region));
	}
}
