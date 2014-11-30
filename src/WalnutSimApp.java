import io.ResourceFinder;

import java.awt.Image;
import java.awt.event.*;
<<<<<<< HEAD
import java.util.*;
=======
import java.io.IOException;
>>>>>>> 8804769be4f0b597ae53e2053aa0fc93f11b7697

import javax.imageio.ImageIO;
import javax.swing.*;

import manager.WalnutManager;
import manager.LevelManager;

import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;
import app.*;
import event.*;

public class WalnutSimApp extends AbstractMultimediaApp
implements ActionListener, MetronomeListener
{
	// button names
	private static final String START = "Start";
	private static final String SELECT = "Select Stage";
	private static final String OPTIONS = "Options";
	private static final String EXIT = "Exit Game";
	private static final String BACK = "Back";
	private static final String MUSIC = "Music";
	private static final String NEXT = "Next Level";
	private static final String SOUND = "Sound";
	private static final String LOGO = "Logo";
	private static final String LEVEL_ONE = "Level 1";
	private static final String LEVEL_TWO = "Level 2";
	private static final String LEVEL_THREE = "Level 3";
	
	private static final int FRUSTRATION_METER_WIDTH = 40;
	private static final int FRUSTRATION_METER_HEIGHT = 100;
	
	private boolean				hasSound, hasMusic;
	int                 		height, width;	
	JButton		        		back, exit, music, options, select, sound, start, logo;
	JButton						level_one, level_two, level_three;
	JPanel		        		contentPane, selectScreen, optionsScreen;
	private Content     		background, imgStart, imgSelect, imgOptions, imgExit;
	private ResourceFinder  	finder;
	private ContentFactory  	contentFactory;
	private Stage  				stage;
	private VisualizationView 	stageView;
	private WalnutManager		walnutManager;
	
	private double[]            level;
	private int                 levelId;
	private LevelManager        levels;
	
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand;
		
		actionCommand = event.getActionCommand();
		if(actionCommand.equals(START))
		{
			//Start 1st level
			contentPane.removeAll();
			contentPane.repaint();
			
			levels.changeLevel(1);
			
			contentPane.add(stageView);
			
			walnutManager.clearWalnuts();
			walnutManager.start();
			
			
		}
		else if(actionCommand.equals(SELECT))
		{
			//Select the stage
			//New menu with numbers of stages
         
			contentPane.removeAll();
			contentPane.repaint();
			
			createLevelMenu();
            createBackButton();

			contentPane.add(stageView);
		}
		else if(actionCommand.equals(OPTIONS))
		{
			//set Content Pane layout to null
			contentPane.removeAll();
			contentPane.repaint();
			
			createOptionsMenu();
         
			contentPane.add(stageView);
		}
		else if(actionCommand.equals(EXIT))
		{
			//Exit the game
			System.exit(0);
		}
		else if(actionCommand.equals(BACK))
		{
			//return the Content Pane to main Menu
		    contentPane.removeAll();
		    contentPane.repaint();
		 
			createMainMenu();

			contentPane.add(stageView);
		}
		else if(actionCommand.equals(MUSIC))
		{
			//highlight when sound is true and turn sound on
			if(!hasMusic)
			{
				hasMusic = true;
				music.setText(MUSIC + ": On");
			}
			else
			{
				hasMusic = false;
				music.setText(MUSIC + ": Off");
			}
		}
		else if(actionCommand.equals(SOUND))
		{
			//highlight when sound is true and turn sound on
			if(!hasSound)
			{
				hasSound = true;
				sound.setText(SOUND + ": On");
			}
			else
			{
				hasSound = false;
				sound.setText(SOUND + ": Off");
			}
		}
		else if(actionCommand.equals(LEVEL_ONE))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
			
<<<<<<< HEAD
			levels.changeLevel(1);
=======
			walnutManager.clearWalnuts();
			walnutManager.changeLevel(0.75, 1, 10, 1.5);
>>>>>>> 8804769be4f0b597ae53e2053aa0fc93f11b7697
			walnutManager.start();
		}
		else if(actionCommand.equals(LEVEL_TWO))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
			
<<<<<<< HEAD
			levels.changeLevel(2);
=======
			walnutManager.clearWalnuts();
			walnutManager.changeLevel(0.35, 1.5, 50, 1);
>>>>>>> 8804769be4f0b597ae53e2053aa0fc93f11b7697
			walnutManager.start();
		}
		else if(actionCommand.equals(LEVEL_THREE))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
			
<<<<<<< HEAD
			levels.changeLevel(3);
=======
			walnutManager.clearWalnuts();
			walnutManager.changeLevel(0.25, 2, 90000, 5);
>>>>>>> 8804769be4f0b597ae53e2053aa0fc93f11b7697
			walnutManager.start();
		}
		else if(actionCommand.equals(NEXT))
		{
		    contentPane.removeAll();
		    contentPane.repaint();
		    levelId = levels.getLevelId();
		    walnutManager.nextLevel(levels.changeLevel(levelId));
			//Change level to the next if able (last level should return to main page
		    //and start the next level
		}
	}
	
	/*
	 * Check if WalnutManager has finished the level
	 *  - Add 'bool levelFinished' variable to WalnutManager?
	 *  
	 *  If the level is finished, display the stats in a 'level complete' screen
	 *   - Add 'getLevelStats' function that returns walnutsCollected, walnutsMissed
	 *  
	 *  Add buttons to replay level, go to next level, or go back to main menu
	 *   - Replay level: clear buttons, and start() the walnutManager
	 *   - Next level: changeLevel(), clear buttons, and start() the walnutManager
	 *   - Main Menu: clear buttons, create Main Menu buttons
	 */		
	
	/*
	 * How to handle pause menu
	 *  - don't call walnut's handleTick function in walnutManager's handleTick
	 */
	
	public void handleTick(int millis)
	{
		return;
	}
	
	public void init()
	{
		height = 700;
        width  = 500;
        hasMusic = false;
        hasSound = false;
     
		contentPane = (JPanel)rootPaneContainer.getContentPane();
		contentPane.setLayout(null);
		
		stage = new Stage(15);
		
		stageView = stage.getView();
		stageView.setBounds(0, 0, width, height);
		
		finder = ResourceFinder.createInstance(this);
		
		contentFactory = new ContentFactory(finder);
		
		background = contentFactory.createContent("background.png", 3);
		stage.add(background);
		
		walnutManager = new WalnutManager(width, height, contentFactory);
		walnutManager.changeLevel(5, 1, 9999, 2);
		walnutManager.start();
		stage.add(walnutManager);
	    stage.addMouseListener(walnutManager);
	    
	    levels = walnutManager.getLevelManager();
		
		createMainMenu();

		contentPane.add(stageView);
//level adding
//
//
		stage.start();
	}
	
	public void createMainMenu()
	{
		logo = new JButton(LOGO);
		logo.setBounds(50, 50, 425, 125);
		logo.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("logo.png"));
		    logo.setIcon(new ImageIcon(img));
		    logo.setOpaque(false);
		    logo.setContentAreaFilled(false);
		    logo.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(logo);
		
		start = new JButton(START);
		start.setBounds(150, 300, 225, 50);
		start.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("start.png"));
		    start.setIcon(new ImageIcon(img));
		    start.setOpaque(false);
		    start.setContentAreaFilled(false);
		    start.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(start);
	
        select = new JButton(SELECT);
		select.setBounds(130, 380, 250, 50);
		select.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("select.png"));
		    select.setIcon(new ImageIcon(img));
			select.setOpaque(false);
			select.setContentAreaFilled(false);
			select.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(select);
		
		options = new JButton(OPTIONS);
		options.setBounds(90, 460, 325, 50);
		options.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("options.png"));
		    options.setIcon(new ImageIcon(img));
		    options.setOpaque(false);
		    options.setContentAreaFilled(false);
		    options.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(options);
		
		exit = new JButton(EXIT);
		exit.setBounds(170, 580, 175, 50);
		exit.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("exit.png"));
		    exit.setIcon(new ImageIcon(img));
		    exit.setOpaque(false);
		    exit.setContentAreaFilled(false);
		    exit.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(exit);
	}
	
	public void createOptionsMenu()
	{		
        music = new JButton(MUSIC + ": Off");
        music.setBounds(375, 375, 100, 50);
        music.addActionListener(this);
        music.setActionCommand(MUSIC);
		contentPane.add(music);
		
        sound = new JButton(SOUND + ": Off");
		sound.setBounds(375, 450, 100, 50);
		sound.addActionListener(this);
		sound.setActionCommand(SOUND);
		contentPane.add(sound);
		
		createBackButton();
	}
	
	public void createLevelMenu()
	{
		level_one = new JButton(LEVEL_ONE);
        level_one.setBounds(375, 300, 100, 50);
        level_one.addActionListener(this);
		contentPane.add(level_one);
		
		level_two = new JButton(LEVEL_TWO);
		level_two.setBounds(375, 375, 100, 50);
		level_two.addActionListener(this);
		contentPane.add(level_two);
		
		level_three = new JButton(LEVEL_THREE);
		level_three.setBounds(375, 450, 100, 50);
		level_three.addActionListener(this);
		contentPane.add(level_three);
	}
	
	public void createBackButton()
	{
		back = new JButton(BACK);
		back.setBounds(375, 525, 100, 50);
		back.addActionListener(this);
		contentPane.add(back);
	}
}