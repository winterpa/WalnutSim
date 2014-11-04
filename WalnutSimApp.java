import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import app.*;
import event.*;

public class WalnutSimApp extends AbstractMultimediaApp
implements ActionListener, MetronomeListener
{
    
	private boolean		music, running, sound;
	int                 height, width;	
	JButton		        back, exit, musicOff, musicOn, options, select, soundOff, soundOn, start;
	private JLabel		label;
	JPanel		        contentPane, selectScreen, optionsScreen;
	private Metronome	metronome;
	
	private static final String START = "Start";
	private static final String SELECT = "Select Stage";
	private static final String OPTIONS = "Options";
	private static final String EXIT = "Exit Game";
	private static final String BACK = "Back";
	private static final String MUSICON = "Music On";
	private static final String MUSICOFF = "Music Off";
	private static final String SOUNDON = "Sound On";
	private static final String SOUNDOFF = "Sound Off";
	
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand;
		
		actionCommand = event.getActionCommand();
		if(actionCommand.equals(START))
		{
			//Start 1st level
			metronome.reset();
		}
		else if(actionCommand.equals(SELECT))
		{
			//Select the stage
			//New menu with numbers of stages
         
			contentPane.removeAll();
			contentPane.repaint();

            back = new JButton(BACK);
			back.setBounds(300, 525, 100, 50);
			back.addActionListener(this);
			contentPane.add(back);
         
		}
		else if(actionCommand.equals(OPTIONS))
		{
			//set Content Pane layout to null
			contentPane.removeAll();
			contentPane.repaint();
         
			label = new JLabel("Music:");
			label.setBounds(100, 375, 100, 50);
			contentPane.add(label);
			
            musicOn = new JButton(MUSICON);
            musicOn.setBounds(225, 375, 100, 50);
            musicOn.addActionListener(this);
            contentPane.add(musicOn);
			
            musicOff = new JButton(MUSICOFF);
            musicOff.setBounds(375, 375, 100, 50);
            musicOff.addActionListener(this);
			contentPane.add(musicOff);
			
			label = new JLabel("Sound:");
			label.setBounds(100, 450, 100, 50);
			contentPane.add(label);
			
            soundOn = new JButton(SOUNDON);
			soundOn.setBounds(225, 450, 100, 50);
			soundOn.addActionListener(this);
			contentPane.add(soundOn);
			
            soundOff = new JButton(SOUNDOFF);
			soundOff.setBounds(375, 450, 100, 50);
			soundOff.addActionListener(this);
			contentPane.add(soundOff);
			
            back = new JButton(BACK);
			back.setBounds(300, 525, 100, 50);
			back.addActionListener(this);
			contentPane.add(back);
         
         
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
		 
			start = new JButton(START);
			start.setBounds(300, 300, 100, 50);
			start.addActionListener(this);
			contentPane.add(start);
			
            select = new JButton(SELECT);
			select.setBounds(300, 375, 100, 50);
			select.addActionListener(this);
			contentPane.add(select);
      
            options = new JButton(OPTIONS);
			options.setBounds(300, 450, 100, 50);
			options.addActionListener(this);
			contentPane.add(options);
      
            exit = new JButton(EXIT);
			exit.setBounds(300, 525, 100, 50);
			exit.addActionListener(this);
			contentPane.add(exit);
         
		}
		else if(actionCommand.equals(MUSICON))
		{
			//highlight when music is true and turn music on
			if(!music)
				music = true;
		}
		else if(actionCommand.equals(MUSICOFF))
		{
			//highlight when music is false and turn music off
			if(music)
				music = false;
		}
		else if(actionCommand.equals(SOUNDON))
		{
			//highlight when sound is true and turn sound on
			if(!sound)
				sound = true;
		}
		else if(actionCommand.equals(SOUNDOFF))
		{
			//highlight when sound is false and turn sound off
			if(sound)
				sound = false;
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
            width  = 700;
            music = false;
            sound = false;
         
			contentPane = (JPanel)rootPaneContainer.getContentPane();
			contentPane.setLayout(null);
			
			start = new JButton(START);
			start.setBounds(300, 300, 100, 50);
			start.addActionListener(this);
			contentPane.add(start);
			
            select = new JButton(SELECT);
			select.setBounds(300, 375, 100, 50);
			select.addActionListener(this);
			contentPane.add(select);
         
            options = new JButton(OPTIONS);
			options.setBounds(300, 450, 100, 50);
			options.addActionListener(this);
			contentPane.add(options);
         
            exit = new JButton(EXIT);
			exit.setBounds(300, 525, 100, 50);
			exit.addActionListener(this);
			contentPane.add(exit);
         
         
		}
		
}
