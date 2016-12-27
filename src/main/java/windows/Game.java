package windows;

import java.awt.CardLayout;
import javax.swing.JPanel;



public class Game {
	
	
	

	public static void createWindow(){
		Frames frame = new Frames();
		CardLayout cardLayout = new CardLayout();
		
		JPanel containerPanel = new JPanel();
		MenuPanel menuPanel = new MenuPanel(cardLayout);
		GamePanel gamePanel = new GamePanel(cardLayout);
		OptionsPanel optionsPanel = new OptionsPanel(cardLayout);
		
		containerPanel.setLayout(cardLayout);
		containerPanel.add("menuPanel", menuPanel);
		containerPanel.add("optionsPanel", optionsPanel);
		containerPanel.add("gamePanel", gamePanel);
		frame.add(containerPanel);
		frame.setVisible(true);
		containerPanel.setVisible(true);
		cardLayout.show(containerPanel, "menuPanel");

	}
	
	public static void main(String[] args){
		createWindow();
		
		}
	
}