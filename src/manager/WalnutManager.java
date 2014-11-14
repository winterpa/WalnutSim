package manager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import visual.dynamic.described.DescribedSprite;
import visual.statik.TransformableContent;
import visual.statik.sampled.ContentFactory;
import content.Walnut;

public class WalnutManager extends DescribedSprite
{
	private ArrayList<Walnut> walnuts;
	private TransformableContent walnutImage;
	private Boolean running;
	private int height, maxWalnuts, spawnX, spawnY, spawnTimer, spawnTime, width;
	
    private static Random rng = new Random();
	
	public WalnutManager()
	{
		this(0, 0, null);
	}
	
	public WalnutManager(int width, int height, ContentFactory contentFactory)
	{
		walnutImage = contentFactory.createContent("walnut.png", 4);
		walnuts = new ArrayList<Walnut>();
		
		running = false;
		
		//to pass in
		spawnX = 450;
		spawnY = 300;
		spawnTime = 45;
		spawnTimer = spawnTime;
		this.height = height;
		this.width = width;
		maxWalnuts = 5;
	}
	
	public void add(double x, double y, int time)
	{
		walnuts.add(new Walnut(walnutImage, x, y, time));
	}
	
	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
	}
	
	public void start()
	{
		running = true;
	}
	
	public void setSpawnTime(int spawnTime)
	{
		this.spawnTime = spawnTime;
	}
	
	@Override
	public void handleTick(int arg0)
	{		
		if(running && spawnTimer <= 0)
		{
			walnuts.add(new Walnut(walnutImage, rng.nextInt(spawnX) + 10, rng.nextInt(spawnY), 50));
			spawnTimer = spawnTime; //to pass in
		}

		spawnTimer--;
	}
	
	@Override
	public void render(Graphics g)
	{
		Graphics2D g2;		
		g2 = (Graphics2D) g;
		
		Iterator<Walnut> i;
		i = walnuts.iterator();
		
		ArrayList<Walnut> nutsToRemove = new ArrayList<Walnut>();
		
		while(i.hasNext())
		{
			Walnut tempNut;
			
			tempNut = i.next();
			tempNut.handleTick(0);
			
			if((int)tempNut.getY() >= height)
			{				
				nutsToRemove.add(tempNut);
			}
			else
				tempNut.render(g2);
		}
		
		i = nutsToRemove.iterator();
		while(i.hasNext())
		{
			Walnut tempNut;
			
			tempNut = i.next();
			
			remove(tempNut);
		}
	}
}