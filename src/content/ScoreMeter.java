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
	
	public ScoreMeter()
	{
		this(0);
	}
	
	public ScoreMeter(int fontSize)
	{
		this.text = "Score: 0";
		font = new Font("Comic Sans MS", Font.PLAIN, fontSize);
	}
	
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
	
	public void render(Graphics g)
	{
		paint(g);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setScore(int score)
	{
		this.score = score;
		setText("Score: " + this.score);
	}

	@Override
	protected TransformableContent getContent() 
	{
		return null;
	}

	@Override
	public void handleTick(int arg0) 
	{
		//Do nothing
	}
	
	public void incrementScore()
	{
		this.score++;
		setScore(this.score);
	}
}
