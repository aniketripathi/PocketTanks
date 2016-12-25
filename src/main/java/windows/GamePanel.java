package windows;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale.Category;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import utility.Signals;
import environment.GameMap;
import environment.Region;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import objects.Brick;
import objects.ObjectHandler;
import objects.Player;
import objects.Tank;
import weapons.*;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.JProgressBar;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;

public class GamePanel extends JPanel implements MouseListener, Runnable{

	private static final long 	serialVersionUID = 1L;
	
	private  Region 			wholeRegion;
	private  Region 			gameRegion;
	private  Region 			propertyRegion;
	private  final int 			UNIT_X = 8;
	private  final int 			UNIT_Y = 8;
	private  final int 			BLOCKS_X = Frames.WIDTH/UNIT_X;
	private  final int 			BLOCKS_Y = Frames.WIDTH/UNIT_Y;
	private  JSlider 			powerSlider1;
	private  JSlider 			powerSlider2;
	private  JComboBox<Object> 	weaponsListComboBox1;
	private  JComboBox<Object> 	weaponsListComboBox2;
	private  JSpinner			angleSpinner1;
	private  JSpinner 			angleSpinner2;
	private  JPanel				animationPanel;
	private  Player 			player1;
	private  Player 			player2;
	private  JButton			fireButton;
	private  JLabel				movesLabel1;
	private  JLabel				movesLabel2;
	private  int				turn;
	private boolean 			isRunning;
	private boolean				isPaused;
	private  int 				fps = 30;
	private  int 				frameCount = 0;
	private  ObjectHandler		objectHandler;
	private  ArrayList<Brick>	brickList;
	private GameMap				gameMap;
	private Thread				paintThread;
	
	
	
	
	
	
	public GamePanel(CardLayout cardLayout){
	
		super();
	
	// set double buffered
		this.setDoubleBuffered(true);
		
	// create regions	
		createRegions();
		
	// set layout for adding components	
		setLayout(null);
		
	//create gameMap
		gameMap = new GameMap(gameRegion, GameMap.GAME_MAP_TEMPLATE1);
		
		
	// create player		
		player1 = new Player(gameMap);
		player2 = new Player(gameMap);	
		
	// add player's tanks to collision objects
		GameMap.collisionObjects.add(player1.tank);
		GameMap.collisionObjects.add(player2.tank);
		
	//set animationPanel where animation takes place
		animationPanel = new JPanel();
		animationPanel.setSize(gameRegion.width, gameRegion.height);
		animationPanel.setLocation(gameRegion.x - gameRegion.width/2, gameRegion.y- gameRegion.height/2);
		animationPanel.setFocusable(true);
		animationPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(animationPanel);
		
	
	/** adding property components and setting their location, size and other  properties **/
		powerSlider1 = new JSlider();
		add(powerSlider1);
		powerSlider1.setSize(340, 45);
		powerSlider1.setLocation(UNIT_X * 10, UNIT_Y * 75);
		powerSlider1.setMinimum(0);
		powerSlider1.setMaximum(100);
		powerSlider1.setPaintLabels(true);
		powerSlider1.setPaintTicks(true);
		powerSlider1.setMajorTickSpacing(20);
		powerSlider1.setMinorTickSpacing(5);
		
		
		powerSlider2 = new JSlider();
		add(powerSlider2);
		powerSlider2.setSize(340, 45);
		powerSlider2.setLocation(Frames.WIDTH/2 + UNIT_X * 20, UNIT_Y * 75);
		powerSlider2.setMinimum(0);
		powerSlider2.setMaximum(100);
		powerSlider2.setPaintLabels(true);
		powerSlider2.setPaintTicks(true);
		powerSlider2.setMajorTickSpacing(20);
		powerSlider2.setMinorTickSpacing(5);
		
	/** Inner class for custom rendering of comboBox **/	
		class ComboBoxRenderer extends  DefaultListCellRenderer{
			private static final long serialVersionUID = 3437375278270028022L;

			@Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		        if (value instanceof Weapon) {
		            value = WeaponNames.getName( ((Weapon)value).getType());
		        }

		        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.

		    }
			
		}
		
	/*********************/
	/** create property components and set their configuration **/	
		weaponsListComboBox1 = new JComboBox<Object>();
		add(weaponsListComboBox1);
		weaponsListComboBox1.setSize(150, 20);
		weaponsListComboBox1.setLocation(powerSlider1.getX(), powerSlider1.getY() - 35);
		weaponsListComboBox1.setRenderer(new ComboBoxRenderer());
		
		weaponsListComboBox2 = new JComboBox<Object>();
		add(weaponsListComboBox2);
		weaponsListComboBox2.setSize(150, 20);
		weaponsListComboBox2.setLocation(powerSlider2.getX(), powerSlider2.getY() - 35);
		weaponsListComboBox2.setRenderer(new ComboBoxRenderer());
		
		
		angleSpinner1 = new JSpinner();
		add(angleSpinner1);
		angleSpinner1.setSize(75, 20);
		angleSpinner1.setLocation(weaponsListComboBox1.getX() + 250, weaponsListComboBox1.getY() );
		angleSpinner1.setModel(new SpinnerNumberModel(new Integer(60), new Integer(0), new Integer(180), new Integer(1)));
		angleSpinner1.setValue(new Integer(60));
		
		
		angleSpinner2 = new JSpinner();
		add(angleSpinner2);
		angleSpinner2.setSize(75, 20);
		angleSpinner2.setLocation(weaponsListComboBox2.getX() + 250, weaponsListComboBox2.getY() );
		angleSpinner2.setModel(new SpinnerNumberModel(new Integer(60), new Integer(0), new Integer(180), new Integer(1)));
		angleSpinner2.setValue(new Integer(120));
	
		fireButton = new JButton();
		add(fireButton);
		fireButton.setSize(75, 40);
		fireButton.setLocation(angleSpinner1.getX() + 250, angleSpinner1.getY());
		fireButton.setText("Fire");
	
	
		movesLabel1 = new JLabel(new Integer(player1.tank.moves).toString());
		movesLabel1.setFont(new Font("Tahoma", Font.BOLD, 25));
		add(movesLabel1);
		movesLabel1.setSize(30, 30);
		movesLabel1.setLocation(angleSpinner1.getX() + 120, angleSpinner1.getY());
		
		movesLabel2 = new JLabel(new Integer(player2.tank.moves).toString());
		movesLabel2.setFont(new Font("Tahoma", Font.BOLD, 25));
		add(movesLabel2);
		movesLabel2.setSize(30, 30);
		movesLabel2.setLocation(angleSpinner2.getX() + 120, angleSpinner2.getY());
		
	
		
	//create new bricks, add their region and add them to collision object	
		brickList = new ArrayList<Brick>();	
		for(Region region: gameMap.getBricksRegions()){
			Brick brick = new Brick(region);	
			brickList.add(brick);
			GameMap.collisionObjects.add(brick);	
		}	
		
	
		
	//give properties to tanks	
		player1.tank.setProperties((int)angleSpinner1.getValue(), powerSlider1.getValue());
	// set turn default first player turn
		player2.tank.setProperties((int)angleSpinner2.getValue(), powerSlider2.getValue());
	
		//set turn
			turn = player1.getPlayerNumber();
		
	// add those weapons to comboBox i.e. their weapons list	
		addWeaponsToComboBox();
		updatePropertyBoxesByTurn();
		
	//set running and paused status	
		isRunning = true;
		isPaused = false;
		
	// create object handler and add inital objects to render, nothing in tank to update
		// bricks will be rendered, no need to update them hence don't add them to update game object list
			objectHandler = new ObjectHandler();
			objectHandler.addGameRenderObject(player1.tank);
			objectHandler.addGameRenderObject(player2.tank);
		for(Brick brick: brickList)		objectHandler.addGameRenderObject(brick);
			
			
	/** Inner class for key binding **/	
		class ArrowAction extends AbstractAction{
			private static final long serialVersionUID = 1826043925785388479L;
			int key; 
			public ArrowAction(int key){
				this.key = key;
			}
			
			@Override
			public void actionPerformed(ActionEvent keyEvent) {
				if(turn == player1.getPlayerNumber()){
					if(key == KeyEvent.VK_LEFT)		player1.tank.moveLeft();
					if(key == KeyEvent.VK_RIGHT)	player1.tank.moveRight();
					if(!objectHandler.getGameUpdateObjects().contains(player1.tank))
						objectHandler.addGameUpdateObject(player1.tank);
				}
				else if(turn == player2.getPlayerNumber()){	
					if(key == KeyEvent.VK_LEFT)		player2.tank.moveLeft();
					if(key == KeyEvent.VK_RIGHT)	player2.tank.moveRight();
					if(!objectHandler.getGameUpdateObjects().contains(player2.tank))	
						objectHandler.addGameUpdateObject(player2.tank);
				}
				
			}
			
		}

	/***********************/	
	// add and remove event listeners and key bindings
		fireButton.addMouseListener(this);
		animationPanel.addMouseListener(this);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		
		this.getActionMap().put("LeftArrow", new ArrowAction(KeyEvent.VK_LEFT));
		this.getActionMap().put("RightArrow", new ArrowAction(KeyEvent.VK_RIGHT));
		
		
		//create thread;
				paintThread = new Thread(this, "paintThread");
				paintThread.start();
		
		
	}
	
	
	
	
	
	private void renderGame(Graphics2D graphics){
		//draw grid
		//gameRegion.drawGridWithUnit(graphics, UNIT_X, UNIT_Y);
		
		//draw game objects
		objectHandler.renderObjects(graphics);
		
	}
	
	
	
	@Override
	public void paint(Graphics graphics){
		super.paint(graphics);
		renderGame((Graphics2D)graphics);
		
	}
	
	
	
	private void addWeaponsToComboBox(){
		for(Weapon weapon: player1.tank.getWeaponsList())	weaponsListComboBox1.addItem(weapon);
		for(Weapon weapon: player2.tank.getWeaponsList())	weaponsListComboBox2.addItem(weapon);
	}
	
	private void updatePropertyBoxesByTurn(){
		if(turn == player1.getPlayerNumber()){
			powerSlider1.setEnabled(true);
			weaponsListComboBox1.setEnabled(true);
			angleSpinner1.setEnabled(true);
			
			powerSlider2.setEnabled(false);
			weaponsListComboBox2.setEnabled(false);
			angleSpinner2.setEnabled(false);
		}
		
		else {
			powerSlider2.setEnabled(true);
			weaponsListComboBox2.setEnabled(true);
			angleSpinner2.setEnabled(true);
			
			powerSlider1.setEnabled(false);
			weaponsListComboBox1.setEnabled(false);
			angleSpinner1.setEnabled(false);
		}
	}
	
	
	private void updatePropertyBoxes(int playerNumber, boolean status){
		if(playerNumber == player1.getPlayerNumber()){
			powerSlider1.setEnabled(status);
			weaponsListComboBox1.setEnabled(status);
			angleSpinner1.setEnabled(status);
		}
		
		else {
			powerSlider2.setEnabled(status);
			weaponsListComboBox2.setEnabled(status);
			angleSpinner2.setEnabled(status);
		}
	}
	
	
	
	private void createRegions(){
		wholeRegion = new Region(Frames.WIDTH/2, Frames.HEIGHT/2, Frames.WIDTH, Frames.HEIGHT);
		
		gameRegion = wholeRegion.getRegion(100, 80);		// 80 % of original region in width
		gameRegion.y = (8 * wholeRegion.height/ 20); 
		
		propertyRegion = wholeRegion.getRegion(100, 20);	//20 % of original region in width
		propertyRegion.y = Frames.HEIGHT -  (2 * wholeRegion.height / 20); 
		
	}
	
	
	
	private void updateGame(){
	// update tanks gun	separately
		player1.tank.setProperties((int)angleSpinner1.getValue(), powerSlider1.getValue());
		player2.tank.setProperties((int)angleSpinner2.getValue(), powerSlider2.getValue());
	// update moves
		movesLabel1.setText(new Integer(player1.tank.moves).toString());
		movesLabel2.setText(new Integer(player2.tank.moves).toString());
	//  
		objectHandler.updateObjects();
	
	}
	
	
	
	
	
	
	
	
	private void gameLoop()
	   {
	      //This value would probably be stored elsewhere.
	      final double GAME_HERTZ = 30.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      //If you're worried about visual hitches more than perfect timing, set this to 1.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //Simple way of finding FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (isRunning)
	      {
	    	  try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         double now = System.nanoTime();
	         int updateCount = 0;
	         
	         if (!isPaused)
	         {
	             //Do as many game updates as we need to, potentially playing catchup.
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
	            {
	               updateGame();
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	   
	            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	         
	            //repaint only required area
	            this.repaint(new Rectangle(gameRegion.x -gameRegion.width/2, gameRegion.y - gameRegion.height/2, gameRegion.width, gameRegion.height));
	         
	            //Update the frames we got.
	            int thisSecond = (int) (lastUpdateTime / 1000000000);
	            if (thisSecond > lastSecondTime)
	            {
	              // System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	            
	               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
	               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
	               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	   }


	


	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		
	// mouse event comes from fire button	
	if(mouseEvent.getSource().equals(fireButton)){	
		
	if(turn == player1.getPlayerNumber()){			// remove the weapon from comboBox	and fire the weapon				
		Weapon weapon = (Weapon)weaponsListComboBox1.getSelectedItem();
		weaponsListComboBox1.removeItem(weapon);
		player1.tank.fireWeapon(weapon, objectHandler);
	}
	else {
		Weapon weapon = (Weapon)weaponsListComboBox2.getSelectedItem();
		weaponsListComboBox2.removeItem(weapon);
		player2.tank.fireWeapon(weapon, objectHandler);
	}
	fireButton.setEnabled(false);		//disable button first
	updatePropertyBoxes(turn, false);	//disable all his property boxes of the player whose turn it was
	}
	// if source is animation panel then give it focus
	else if(mouseEvent.getSource().equals(animationPanel))		animationPanel.requestFocusInWindow();
	
	}
	
	

	@Override
	public void run() {
		gameLoop();
		
	}

	
	
	
	//These methods are not required


	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}


}
