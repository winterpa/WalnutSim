import io.ResourceFinder;

import java.awt.event.*;

import javax.swing.*;

import manager.WalnutManager;

import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;
import app.*;
import event.*;

public class WalnutSimApp extends AbstractMultimediaApp
implements ActionListener, MetronomeListener
{
    
	private boolean				running, hasSound, hasMusic;
	int                 		height, width;	
	JButton		        		back, exit, music, options, select, sound, start, more;
	private JLabel				label;
	JPanel		        		contentPane, selectScreen, optionsScreen;
	private Metronome			metronome;
	private Content     		background;
	private ResourceFinder  	finder;
	private ContentFactory  	contentFactory;
	private Stage  				stage;
	private VisualizationView 	stageView;
	private WalnutManager		walnutManager;
	
	private static final String START = "Start";
	private static final String SELECT = "Select Stage";
	private static final String OPTIONS = "Options";
	private static final String EXIT = "Exit Game";
	private static final String BACK = "Back";
	private static final String MUSIC = "Music";
	private static final String SOUND = "Sound";
	private static final String MORE = "AT LEAST I'M NOT BERNSTEIN!";
	
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand;
		
		actionCommand = event.getActionCommand();
		if(actionCommand.equals(START))
		{
			//Start 1st level
			contentPane.removeAll();
			contentPane.repaint();
			
			more = new JButton(MORE);
			more.setBounds(125, 625, 250, 50);
			more.addActionListener(this);
			contentPane.add(more);
			
			contentPane.add(stageView);
			walnutManager.start();
			
						
			//metronome.reset();
		}
		else if(actionCommand.equals(SELECT))
		{
			//Select the stage
			//New menu with numbers of stages
         
			contentPane.removeAll();
			contentPane.repaint();

            back = new JButton(BACK);
			back.setBounds(375, 525, 100, 50);
			back.addActionListener(this);
			contentPane.add(back);

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
		else if(actionCommand.equals(MORE))
		{
			walnutManager.setSpawnTime(3);
		}
	}
		
	public void handleTick(int millis)
	{
		return;
	}
	
	public void init()
	{
		
		running = false;
		height = 700;
        width  = 500;
        hasMusic = false;
        hasSound = false;
     
		contentPane = (JPanel)rootPaneContainer.getContentPane();
		contentPane.setLayout(null);
		
		stage = new Stage(30);
		
		stageView = stage.getView();
		stageView.setBounds(0, 0, width, height);
		
		finder = ResourceFinder.createInstance(this);
		
		contentFactory = new ContentFactory(finder);
		
		background = contentFactory.createContent("background.png", 3);
		stage.add(background);
		
		walnutManager = new WalnutManager(width, height, contentFactory);
		stage.add(walnutManager);
		
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
		contentPane.add(music);
		
        sound = new JButton(SOUND + ": Off");
		sound.setBounds(375, 450, 100, 50);
		sound.addActionListener(this);
		sound.setActionCommand(SOUND);
		contentPane.add(sound);
		
        back = new JButton(BACK);
		back.setBounds(375, 525, 100, 50);
		back.addActionListener(this);
		contentPane.add(back);
	}
}