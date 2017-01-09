package windows;

import java.awt.CardLayout;
import javax.swing.JPanel;



public class Game {
	
	public static final String MENU_PANEL = "menuPanel";
	public static final String OPTIONS_PANEL = "optionsPanel";
	public static final String GAME_PANEL = "gamePanel";

	public static void createWindow(){
		Frames frame = new Frames();
		CardLayout cardLayout = new CardLayout();
		
		JPanel containerPanel = new JPanel();
		MenuPanel menuPanel = new MenuPanel(cardLayout);
		GamePanel gamePanel = new GamePanel(cardLayout);
		OptionsPanel optionsPanel = new OptionsPanel(cardLayout);
		
		containerPanel.setLayout(cardLayout);
		containerPanel.add(Game.MENU_PANEL, menuPanel);
		containerPanel.add(Game.OPTIONS_PANEL, optionsPanel);
		containerPanel.add(Game.GAME_PANEL, gamePanel);
		frame.add(containerPanel);
		frame.setVisible(true);
		containerPanel.setVisible(true);
		cardLayout.show(containerPanel, Game.MENU_PANEL);

	}
	
	public static void main(String[] args){
		createWindow();
		
		}
	
}