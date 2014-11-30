package content;

import app.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.event.*;
import java.util.*;

import visual.statik.described.*;
import visual.statik.sampled.Content;
import manager.*;

import visual.VisualizationView;
import visual.dynamic.described.Stage;

import javax.swing.*;
public class TransitionPage{

	private int level, score;
	private boolean stageClear;
	private LevelManager levels;
	private JButton back, next;
	private JPanel contentPane;
	private String text;
	private WalnutManager walnuts;
	private static final String BACK = "Back";
	private static final String NEXT = "Next";
	
	public TransitionPage(WalnutManager walnuts, LevelManager levels, int level, int score, boolean stageClear)
	{

        this.walnuts = walnuts;
		this.level = level;
		this.levels = levels;
		this.score = score;
		this.stageClear = stageClear;
		if(stageClear)
		{
			this.text = "You have cleared Level " + level;
			next = new JButton(NEXT);
	        next.setBounds(375, 300, 100, 50);
	        next.setActionCommand(NEXT);
	       // next.addActionListener(this);
			
		}
		else
		{
			this.text = "You have failed Level" + level;
			back = new JButton(BACK);
			back.setBounds(375, 375, 100, 50);
			//back.addActionListener(this);
			contentPane.add(back);
		}
	}
	
	//Content pane adding may need to belong in WalnutSimApp
	public void render(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext       frc;
		GlyphVector             glyphs;
		Shape                   shape;
		double						y;
		
		//contentPane = (JPanel)rootPaneContainer.getContentPane();
		//contentPane.setLayout(null);

		frc = g2.getFontRenderContext();
		Font transitionFont = new Font("Rage Italic", Font.PLAIN, 40);
		
		glyphs = transitionFont.createGlyphVector(frc, text);
		y = glyphs.getGlyphVisualBounds(0).getBounds2D().getHeight() + 5;
		shape = glyphs.getOutline( 0.0f, (float)y);
		Color transitionColor = Color.WHITE;
		
		g2.fill(shape);
		g2.draw(shape);
		
	}

	/*//Come back to these tomorrow or by the end of break to
	//generate appropriate transition functionality
	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		if(action.equals(NEXT))
		{
		    contentPane.removeAll();
		    contentPane.repaint();
		    level += 1;
		    walnuts.nextLevel(levels.changeLevel(level));
			//Change level to the next if able (last level should return to main page
		    //and start the next level
		}
		else if(action.equals(BACK))
		{
			//return the Content Pane to main Menu
		    contentPane.removeAll();
		    contentPane.repaint();
		 
			//createMainMenu();

			//contentPane.add(stageView);
			//set content back to main
		}
	}*/
}
