package windows;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;
import javax.swing.JPanel;

/**
 * @author Aniket
 *
 */
public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Graphics2D graphics;
	private BufferStrategy bufferStrategy;
	
	
	public MenuPanel(){
		super();
	}


	@Override
	public void paint(Graphics graphics) {
		this.graphics = (Graphics2D)graphics;
	}
}
