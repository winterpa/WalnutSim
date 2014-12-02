import io.ResourceFinder;

import java.awt.Image;
import java.awt.event.*;

import java.util.*;

import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;

import manager.WalnutManager;
import manager.LevelManager;
import content.TransitionPage;

import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;
import app.*;
import event.*;
import content.*;

public class WalnutSimApp extends AbstractMultimediaApp
implements ActionListener, MetronomeListener
{
	// Button names
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
	
	//Frustration Meter Constants
	private static final int FRUSTRATION_METER_WIDTH = 200;
	private static final int FRUSTRATION_METER_HEIGHT = 50;
	
	private boolean				hasSound, hasMusic;
	int                 		height, width;	
	JButton		        		back, exit, music, options, select, sound, start, logo, next;
	JButton						level_one, level_two, level_three;
	JPanel		        		contentPane, selectScreen, optionsScreen;
	private Content     		background, imgStart, imgSelect, imgOptions, imgExit;
	private ContentFactory  	contentFactory;
	private FrustrationMeter	frustrationMeter;
	private ResourceFinder  	finder;
	private ScoreMeter			scoreMeter;
	private Stage  				stage;
	private VisualizationView 	stageView;
	private WalnutManager		walnutManager;
	
	/**
	* The button click handler
	*
	* @param event The event to parse
	*/
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand;
		
		actionCommand = event.getActionCommand();
		if(actionCommand.equals(START))
		{			
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
			
			walnutManager.changeLevel(1.5, 1.5, 9999, 2);
			walnutManager.reset();
			walnutManager.start();

			stage.add(scoreMeter);
			stage.add(frustrationMeter);
			contentPane.add(stageView);
		}
		else if(actionCommand.equals(LEVEL_TWO))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			walnutManager.changeLevel(0.75, 1.25, 9999, 5.5);
			walnutManager.reset();
			walnutManager.start();

			stage.add(scoreMeter);
			stage.add(frustrationMeter);
			contentPane.add(stageView);
		}
		else if(actionCommand.equals(LEVEL_THREE))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			walnutManager.changeLevel(0.50, 1, 9999, 7.5);
			walnutManager.reset();
			walnutManager.start();

			stage.add(scoreMeter);
			stage.add(frustrationMeter);
			contentPane.add(stageView);
		}
	}

	/**
	* Creates the app
	*/
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
		
		scoreMeter = new ScoreMeter(48);
		frustrationMeter = new FrustrationMeter(width-FRUSTRATION_METER_WIDTH, 0, FRUSTRATION_METER_WIDTH, FRUSTRATION_METER_HEIGHT);

		walnutManager = new WalnutManager(width, height, contentFactory);
		
		walnutManager.changeLevel(5, 1, 9999, 2);
		walnutManager.setFrustrationMeter(frustrationMeter);
		walnutManager.setScoreMeter(scoreMeter);
		walnutManager.start();
		
		stage.add(walnutManager);
	    	stage.addMouseListener(walnutManager);
		
		createMainMenu();

		contentPane.add(stageView);
		
		stage.start();
	}
	
	/**
	* Creates the main menu
	*/
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
	
	/**
	* Creates the options menu
	*/
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
	
	/**
	* Creates the level menu
	*/
	public void createLevelMenu()
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
		
		level_one = new JButton(LEVEL_ONE);
		level_one.setBounds(120, 250, 300, 50);
		level_one.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("level1.png"));
		    level_one.setIcon(new ImageIcon(img));
		    level_one.setOpaque(false);
		    level_one.setContentAreaFilled(false);
		    level_one.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(level_one);
		
		level_two = new JButton(LEVEL_TWO);
		level_two.setBounds(120, 350, 300, 50);
		level_two.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("level2.png"));
		    level_two.setIcon(new ImageIcon(img));
		    level_two.setOpaque(false);
		    level_two.setContentAreaFilled(false);
		    level_two.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(level_two);
		
		level_three = new JButton(LEVEL_TWO);
		level_three.setBounds(120, 450, 300, 50);
		level_three.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("level3.png"));
		    level_three.setIcon(new ImageIcon(img));
		    level_three.setOpaque(false);
		    level_three.setContentAreaFilled(false);
		    level_three.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(level_three);
	}
	
	/**
	* Creates the back button
	*/
	public void createBackButton()
	{		
		back = new JButton(BACK);
		back.setBounds(170, 580, 200, 50);
		back.addActionListener(this);
		  try {
		    Image img = ImageIO.read(getClass().getResource("back.png"));
		    back.setIcon(new ImageIcon(img));
		    back.setOpaque(false);
		    back.setContentAreaFilled(false);
		    back.setBorderPainted(false);
		  } catch (IOException ex) {
		  } catch (IllegalArgumentException ex) {}
		contentPane.add(back);
	}
	
	/**
	* Creates the next button
	*/
	public void createNextButton()
	{
		next = new JButton(NEXT);
		next.setBounds(375, 525, 100, 50);
		next.addActionListener(this);
		contentPane.add(next);
	}
	
	/**
	* Unused method
	* 
	* @param arg0
	*/
	@Override
	public void handleTick(int arg0) 
	{
	}
}
