package windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import utility.Config;

public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1410817900706931913L;

	private JLabel name1;
	private JLabel name2;
	private JTextField playerName1;
	private JTextField playerName2;
	private JButton cancelButton;
	private JButton okButton;
	
	
	
	
	public OptionsPanel(final CardLayout cardLayout){
		
		final Config config = new Config();
		this.setLayout(null);
			
		
		name1 = new JLabel("Player 1 Name");
		name1.setHorizontalAlignment(SwingConstants.CENTER);
		name1.setLocation(50, 50);
		name1.setSize(100, 50);
		name1.setFont(new Font("Tahoma", Font.BOLD, 12));
		name1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(name1);
		
		
		
		name2 = new JLabel("Player 2 Name");
		name2.setHorizontalAlignment(SwingConstants.CENTER);
		name2.setLocation(name1.getX() + 550, 50);
		name2.setSize(100, 50);
		name2.setFont(new Font("Tahoma", Font.BOLD, 12));
		name2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(name2);
		
		
		playerName1 = new JTextField(config.getPlayerName(Config.PLAYER_NAME1));
		playerName1.setLocation(name1.getX(), name1.getY() + 75);
		playerName1.setSize(200, 50);
		playerName1.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(playerName1);
		
		
		playerName2 = new JTextField(config.getPlayerName(Config.PLAYER_NAME2));
		playerName2.setLocation(name2.getX(), name2.getY() + 75);
		playerName2.setSize(200, 50);
		playerName2.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(playerName2);
		
		
		cancelButton = new JButton("Cancel");
		cancelButton.setLocation(playerName2.getX(), playerName2.getY() + 400);
		cancelButton.setSize(100, 50);
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(cancelButton);
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				cardLayout.show(((JButton)actionEvent.getSource()).getParent().getParent(), "menuPanel");
				
			}
			
		});
		
		
		okButton = new JButton("ok");
		okButton.setLocation(playerName1.getX(), playerName1.getY()+ 400);
		okButton.setSize(60, 50);
		okButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(okButton);
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(playerName1.getText().toString().length() <= 15 || playerName2.getText().toString().length() <= 15 ){
						
						config.putPlayerName(Config.PLAYER_NAME1, playerName1.getText().toString());
						config.putPlayerName(Config.PLAYER_NAME2, playerName2.getText().toString());
						GamePanel gamePanel = (GamePanel)((JButton)actionEvent.getSource()).getParent().getParent().getComponent(2);
						gamePanel.updatePlayerNames(playerName1.getText().toString(), playerName2.getText().toString());
						
						cardLayout.show(((JButton)actionEvent.getSource()).getParent().getParent(), "menuPanel");
						
				}
				
				else {
				//show error
			}
			}
			
		});
	}
	
	
	
	
}
