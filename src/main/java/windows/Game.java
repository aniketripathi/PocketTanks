package windows;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JWindow;



public class Game {
	
	private static final long serialVersionUID = 1L;
	

	public static void createWindow(){
		Frames frame = new Frames();
		JPanel containerPanel = new JPanel();
		
		CardLayout cardLayout = new CardLayout();
		MenuPanel menuPanel = new MenuPanel();
		
		GamePanel gamePanel = new GamePanel();
		
		containerPanel.setLayout(cardLayout);
		containerPanel.add("menuPanel", menuPanel);
		containerPanel.add("gamePanel", gamePanel);
		frame.add(containerPanel);
		frame.setVisible(true);
		containerPanel.setVisible(true);

	}
	
	public static void main(String[] args){
		createWindow();
		
		}
	
}