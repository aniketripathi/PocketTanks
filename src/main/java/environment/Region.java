package environment;

import java.awt.Graphics;



public class Region {
	
	public float x;				//x- coordinate of center
	public float y;				//y-coordinate of center
	public int width;			//width of region
	public int height;			// height of region
	
	
	public Region(float x, float y, int width, int height){
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
	
	
	public int getX(){
		return Math.round(this.x);
	}
	
	public int getY(){
		return Math.round(this.y);
	}
	
	public int getArea(){
		return (this.width * this.height);
	}
	
	
	public int getArea(int percentage){
		return (percentage /100) * this.getArea();
	}
	
	public Region getRegion(int width_percentage, int height_percentage){
		return new Region(this.x, this.y, this.width * width_percentage/100, this.height * height_percentage /100);
	}
	
	
	public int getUnitX(int blocks){
		return Math.round((this.width/blocks));
	}
	
	public int getUnitY(int blocks){
		return Math.round((this.height/blocks));
	}
	
	public int getBlocksX(int unit_x){
		return Math.round(this.width/unit_x);
	}
	
	public int getBlocksY(int unit_y){
		return Math.round(this.height/unit_y);
	}
	
	
	public boolean isCoinciding(Region region2){
		return (Math.round(this.x) == Math.round(region2.x) && Math.round(this.y) == Math.round(region2.y));
	}
	
	
	public void drawGrid(Graphics graphics, int x_blocks, int y_blocks){
		
		int unitX = this.getUnitX(x_blocks), unitY = this.getUnitY(y_blocks);
		
		for(int i = 0; i <= x_blocks; i++) 
			graphics.drawLine(this.getX() - this.width/2, this.getY() - this.height/2 + unitY * i, this.getX() + this.width/2, this.getY() - this.height/2 + unitY * i);
		
		for(int i = 0; i <= y_blocks; i++) 
			graphics.drawLine(this.getX() - this.width/2 +unitX * i, this.getY() - this.height/2, this.getX() - this.width/2 +unitX * i, this.getY() + this.height/2);
		
	}
	
	
		public void drawGridWithUnit(Graphics graphics, int unitX, int unitY){
		
		for(int i = 0; i <= this.height/unitX; i++) 
			graphics.drawLine(this.getX() - this.width/2, this.getY() - this.height/2 + unitY * i, this.getX() + this.width/2, this.getY() - this.height/2 + unitY * i);
		
		for(int i = 0; i <= this.width/unitY; i++) 
			graphics.drawLine(this.getX() - this.width/2 +unitX * i, this.getY() - this.height/2, this.getX() - this.width/2 +unitX * i, this.getY() + this.height/2);
		
	}
	
	public void draw(Graphics graphics){
		graphics.drawLine(this.getX() - this.width/2, this.getY() - this.height/2, this.getX() + this.width/2, this.getY() - this.height/2);
		graphics.drawLine(this.getX() + this.width/2, this.getY() - this.height/2, this.getX() + this.width/2, this.getY() + this.height/2);
		graphics.drawLine(this.getX() + this.width/2, this.getY() + this.height/2, this.getX() - this.width/2, this.getY() + this.height/2);
		graphics.drawLine(this.getX() - this.width/2, this.getY() + this.height/2, this.getX() - this.width/2, this.getY() - this.height/2);
	}
	
	
	public void clearRegion(Graphics graphics){
		graphics.clearRect(this.getX(), this.getY(), this.width, this.height);
	}
	
	
	public boolean isColliding(Region region2){
		return ( Math.abs(this.getX() - region2.x) <= (this.width/2 + region2.width/2)  &&
	 			 Math.abs(this.getY() - region2.y) <= (this.height/2 + region2.height/2));
	}
	
	
	public boolean isInsideOf(Region region){
		return (this.getX() + this.width/2 <= region.x + region.width/2 && this.getY() + this.height/2 <= region.y + region.height/2
			&&  this.getX() - this.width/2 >= region.x - region.width/2 && this.getY() - this.height/2 >= region.y - region.height/2);
	}
	
	public boolean isOutsideOf(Region region){
		return (!isInsideOf(region));
	}
	
	public boolean isInside(int x, int y){
		return (x <= this.getX() + this.width/2  && x >= this.getX() - this.width/2		&&
				y <= this.getY() + this.height/2 && y >= this.getY() - this.height/2);
	}
	
	public boolean isOutside(int x, int y){
		return (!isInside(x, y));
	}
}
