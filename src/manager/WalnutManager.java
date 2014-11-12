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
	private int spawnX, spawnY, spawnTimer, spawnTime;
	
    private static Random rng = new Random();
	
	public WalnutManager()
	{
		this(null);
	}
	
	public WalnutManager(ContentFactory contentFactory)
	{
		walnutImage = contentFactory.createContent("walnut.png", 4);
		walnuts = new ArrayList<Walnut>();
		
		running = false;
		
		//to pass in
		spawnX = 450;
		spawnY = 300;
		spawnTime = 45;
		spawnTimer = spawnTime;
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
		
		while(i.hasNext())
		{
			Walnut tempNut;
			
			tempNut = i.next();
			tempNut.handleTick(0);
			tempNut.render(g2);
			
			/*
			if(tempNut.toDelete() ) 
			{
				this.remove(tempNut);
			}
			*/
		}
	}
}