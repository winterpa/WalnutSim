package content;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.Random;

import visual.dynamic.described.AbstractSprite;
import visual.statik.TransformableContent;

public class ScoreMeter extends AbstractSprite
{
	private String text;
	private int score;
	private Font font;
	
	/**
	* Default Constructor
	*/
	public ScoreMeter()
	{
		this(0);
	}
	
	/**
	* Explicit Value Constructor
	* 
	* @param fontSize The point-size to render the font in
	*/
	public ScoreMeter(int fontSize)
	{
		this.text = "Score: 0";
		font = new Font("Comic Sans MS", Font.PLAIN, fontSize);
	}
	
	/**
	* Paint the ScoreMeter's Text
	* 
	* @param g Graphics Engine to render with'
	*/
	public void paint(Graphics g)
	{
		Graphics2D g2;
		
		g2 = (Graphics2D)g;
		
		g2.setFont(font);
		
		// Use antialiasing for text and shapes
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Use high-quality rendering
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
			    RenderingHints.VALUE_RENDER_QUALITY);

		// Use floating point for font metrics
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
			    RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		// Render the glyphs
		if (text != null) paintGlyphs(g2, text);
	}
	
	/**
	* Draw the shape of the font
	* 
	* @param g2 Grpahics Engine to render with
	* @param text The text to write on the screen
	*/
	protected void paintGlyphs(Graphics2D g2, String text)
    	{
		FontRenderContext       frc;
		GlyphVector             glyphs;
		Shape                   shape;
		double						y;

		frc = g2.getFontRenderContext();

		glyphs = font.createGlyphVector(frc, text);
		y = glyphs.getGlyphVisualBounds(0).getBounds2D().getHeight() + 5;
		shape = glyphs.getOutline( 0.0f, (float)y);

		g2.setColor(Color.WHITE);
		g2.fill(shape);
		g2.setColor(Color.BLACK);
		g2.draw(shape);
    	}
	
	/**
	* Render the text
	* 
	* @param g Graphics Engine to render with
	*/
	public void render(Graphics g)
	{
		paint(g);
	}
	
	/**
	* Set the text to render
	* 
	* @param text The text to render
	*/
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	* Set the score to render with the text
	* 
	* @param score The score to render with the text
	*/
	public void setScore(int score)
	{
		this.score = score;
		setText("Score: " + this.score);
	}

	/**
	* Unused Method
	*/
	@Override
	protected TransformableContent getContent() 
	{
		return null;
	}

	/**
	* Unused Method
	*/
	@Override
	public void handleTick(int arg0) 
	{
		//Do nothing
	}
	
	/**
	* Add a point to the Score Meter
	*/
	public void incrementScore()
	{
		this.score++;
		setScore(this.score);
	}
}
