import io.ResourceFinder;

import java.awt.event.*;

import javax.swing.*;

import content.FrustrationMeter;
import content.ScoreMeter;
import manager.WalnutManager;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;
import app.*;
import event.*;

public class WalnutSimApp extends AbstractMultimediaApp
implements ActionListener
{
	// button names
	private static final String START = "Start";
	private static final String SELECT = "Select Stage";
	private static final String OPTIONS = "Options";
	private static final String EXIT = "Exit Game";
	private static final String BACK = "Back";
	private static final String MUSIC = "Music";
	private static final String SOUND = "Sound";
	private static final String LEVEL_ONE = "Level 1";
	private static final String LEVEL_TWO = "Level 2";
	private static final String LEVEL_THREE = "Level 3";
	
	private static final int FRUSTRATION_METER_WIDTH = 100;
	private static final int FRUSTRATION_METER_HEIGHT = 40;
	
	private boolean				hasSound, hasMusic;
	int                 		height, width;	
	JButton		        		back, exit, music, options, select, sound, start;
	JButton						level_one, level_two, level_three;
	JPanel		        		contentPane, selectScreen, optionsScreen;
	private Content     		background;
	private FrustrationMeter	frustrationMeter;
	private ResourceFinder  	finder;
	private ContentFactory  	contentFactory;
	private ScoreMeter			scoreMeter;
	private Stage  				stage;
	private VisualizationView 	stageView;
	private WalnutManager		walnutManager;
	
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand;
		
		actionCommand = event.getActionCommand();
		if(actionCommand.equals(START))
		{
			//Start 1st level
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
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
			
			walnutManager.changeLevel(0.75, 1, 10, 1.5);
			walnutManager.start();
		}
		else if(actionCommand.equals(LEVEL_TWO))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
			
			walnutManager.changeLevel(0.35, 1.5, 50, 1);
			walnutManager.start();
		}
		else if(actionCommand.equals(LEVEL_THREE))
		{
			contentPane.removeAll();
			contentPane.repaint();
			
			contentPane.add(stageView);
			
			walnutManager.changeLevel(0.25, 2, 90000, 5);
			walnutManager.start();
		}
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
		
		scoreMeter = new ScoreMeter(48);
		frustrationMeter = new FrustrationMeter(width-FRUSTRATION_METER_WIDTH, 0, FRUSTRATION_METER_WIDTH, FRUSTRATION_METER_HEIGHT);
		
		walnutManager = new WalnutManager(width, height, contentFactory);
		walnutManager.setFrustrationMeter(frustrationMeter);
		walnutManager.setScoreMeter(scoreMeter);
		stage.add(scoreMeter);
		stage.add(frustrationMeter);
		stage.add(walnutManager);
	    stage.addMouseListener(walnutManager);
	    stage.addKeyListener(walnutManager);
		
		createMainMenu();

		contentPane.add(stageView);
		
		stage.start();
	}
	
	public void createMainMenu()
	{
		start = new JButton(START);
		start.setBounds(375, 300, 100, 50);
		start.addActionListener(this);
		contentPane.add(start);
		
        select = new JButton(SELECT);
		select.setBounds(375, 375, 100, 50);
		select.addActionListener(this);
		contentPane.add(select);
     
        options = new JButton(OPTIONS);
		options.setBounds(375, 450, 100, 50);
		options.addActionListener(this);
		contentPane.add(options);
     
        exit = new JButton(EXIT);
		exit.setBounds(375, 600, 100, 50);
		exit.addActionListener(this);
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
