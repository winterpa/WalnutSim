package manager;

import io.ResourceFinder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import visual.dynamic.described.DescribedSprite;
import visual.statik.TransformableContent;
import visual.statik.sampled.ContentFactory;
import content.Walnut;

public class WalnutManager extends DescribedSprite
{
	private ArrayList<Walnut> walnuts;
	private ContentFactory contentFactory;
	private TransformableContent walnutImage;
	
	public WalnutManager()
	{
		this(null);
	}
	
	public WalnutManager(ContentFactory contentFactory)
	{
		this.contentFactory = contentFactory;
		walnutImage = contentFactory.createContent("walnut.png", 4);
		walnuts = new ArrayList<Walnut>();
	}
	
	public void add(double x, double y, int time)
	{
		walnuts.add(new Walnut(walnutImage, x, y, time));
	}
	
	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
	}
	
	@Override
	public void render(Graphics g)
	{
		Graphics2D g2;		
		g2 = (Graphics2D)g;
		
		Iterator<Walnut> i;
		i = walnuts.iterator();
		
		while(i.hasNext())
		{
			Walnut tempNut = i.next();
			tempNut.handleTick(0);
			tempNut.render(g2);
		}
	}
}