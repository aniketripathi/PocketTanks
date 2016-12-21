package windows;

import java.awt.CardLayout;
import javax.swing.JPanel;



public class Game {
	
	
	

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