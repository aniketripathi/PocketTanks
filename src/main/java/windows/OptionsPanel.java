package windows;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1410817900706931913L;

	private JLabel name1;
	private JLabel name2;
	private JTextField playerName1;
	private JTextField playerName2;
	private JButton cancelButton;
	private JButton okButton;
	
	public OptionsPanel(final CardLayout cardLayout){
		
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
		
		
		playerName1 = new JTextField("Player1");
		playerName1.setLocation(name1.getX(), name1.getY() + 75);
		playerName1.setSize(200, 50);
		playerName1.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(playerName1);
		
		
		playerName2 = new JTextField("Player2");
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
				if(playerName1.getText().toString().length() >= 15 || playerName2.getText().toString().length() >= 15 ){
					JDialog errorDialog = new JDialog();
					errorDialog.setTitle("Error");
					errorDialog.add(new JLabel("Player name larger than 15 letters"));
					errorDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					errorDialog.setSize(200, 100);
					errorDialog.setVisible(true);
				}
				
				else {
						
			}
			}
			
		});
	}
	
}
