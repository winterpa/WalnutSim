package manager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import visual.dynamic.described.DescribedSprite;
import visual.statik.TransformableContent;
import visual.statik.sampled.ContentFactory;
import content.*;

public class WalnutManager extends DescribedSprite
						   implements MouseListener
{
    private static Random rng = new Random();
	private ArrayList<Walnut> walnuts, nutsToRemove;
	private TransformableContent walnutImage;
	private Boolean running;
	private int height, spawnX, spawnY;
	private double[] currentLevel;
	private double spawnTimer;
	private ScoreMeter scoreMeter;
	private FrustrationMeter frustrationMeter;
	
	// current num of walnuts being rendered
	private int currentWalnuts;
	
	// max num of walnuts on-screen
	private int maxWalnuts;
	
	// num of walnuts spawned
	private int walnutsSpawned;
	
	// num of walnuts removed (clicked or falls below screen)
	private int walnutsRemoved;
	
	// num of walnuts collected (clicked)
	/* to be used by score */
	private int walnutsCollected;
	
	// num of walnuts missed (falls below screen)
	/* to be used by frustration */
	private int walnutsMissed;
	
	// X-coordinate of mouse on mousePressed action
	private int mouseX;
	
	// Y-coordinate of mouse on mousePressed action
	private int mouseY;
	/*
	 *  Level variables
	 *  
	 *  totalWalnuts - total num of Walnuts to spawn
	 *  spawnTime - time between each spawn of a Walnut
	 *  growTime - time for walnut to 'grow' before falling
	 *  walnutSpeed - speed the walnut falls
	 */
	private int totalWalnuts;
	private double spawnTime, growTime, walnutSpeed;
	
	//Records if we have cleared the stage when we go to a transition page
	private boolean stageClear;
	
	//Number of walnuts we can miss in a level
	private final int maxCanMiss = 20;
	
	//Number of walnuts to collect to win the level
	private final int walnutsToWin = 9999;
	
	
	public WalnutManager()
	{
		this(0, 0, null);
	}
	
	public WalnutManager(int width, int height, ContentFactory contentFactory)
	{		
		walnutImage = contentFactory.createContent("walnut.png", 4);
		walnuts = new ArrayList<Walnut>();
		nutsToRemove = new ArrayList<Walnut>();
		
		running = false;
		
		spawnX = 450;
		spawnY = 300;
		
		//init values
		currentWalnuts = 0;
		walnutsSpawned = 0;
		walnutsRemoved = 0;
		walnutsCollected = 0;
		walnutsMissed = 0;
			
		//default level values
		spawnTime = 0.75 * 60;
		growTime = 1;
		totalWalnuts = 10;
		walnutSpeed = 3;
		maxWalnuts = 30;
		
		stageClear = false;
		
		spawnTimer = spawnTime;
		this.height = height;
	}
	
	public void add(int x, int y, double growTime, double walnutSpeed)
	{
		walnuts.add(new Walnut(walnutImage, x, y, growTime, walnutSpeed));
	}
	
	//add Mode for flat rate, additive rate, multiplicative rate
	public void changeLevel(double spawnTime, double growTime, int totalWalnuts, double walnutSpeed)
	{
		this.spawnTime = spawnTime * 60;
		this.growTime = growTime;
		this.totalWalnuts = totalWalnuts;
		this.walnutSpeed = walnutSpeed;
		
		if(frustrationMeter != null)
		{
			frustrationMeter.setMaximum(maxCanMiss);
			frustrationMeter.resetFrustration();
		}
	}
	public void clearWalnuts()
	{
		Iterator<Walnut> i;
		i = walnuts.iterator();
		
		while(i.hasNext())
		{		
			Walnut tempNut;
			
			tempNut = i.next();
			nutsToRemove.add(tempNut);
		}
		
		removeNuts();
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public boolean isStageClear()
	{
		return stageClear;
	}
	@Override
	public void handleTick(int arg0)
	{		
		if(running)
		{
			if(walnutsMissed >= maxCanMiss)
			{				
				reset();
			}
			else
			{
				if(spawnTimer <= 0 && (walnutsSpawned < totalWalnuts) )
				{
					if(currentWalnuts < maxWalnuts)
					{
						this.add(rng.nextInt(spawnX) + 10, rng.nextInt(spawnY), growTime, walnutSpeed);
						currentWalnuts++;
						walnutsSpawned++;
					}
					
					spawnTimer = spawnTime;
				}
				else
				{
					spawnTimer--;
				}
			}
		}
	}
	
	public void reset()
	{
		frustrationMeter.resetFrustration();
		this.clearWalnuts();
		this.resetValues();
		this.stop();
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
			
			if((int)tempNut.getY() >= height)
			{
				nutsToRemove.add(tempNut);
				walnutsMissed++;
				walnutsRemoved++;
				frustrationMeter.incrementFrustration();
			}
			else
				tempNut.render(g2);
		}
		
		removeNuts();
	}

	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
		currentWalnuts--;
	}

	public void removeNuts()
	{
		Iterator<Walnut> i;
		i = nutsToRemove.iterator();
		while(i.hasNext())
		{			
			Walnut tempNut;
			tempNut = i.next();
			remove(tempNut);
		}
	}
	
	public void resetValues()
	{
		currentWalnuts = 0;
		walnutsSpawned = 0;
		walnutsRemoved = 0;
		walnutsCollected = 0;
		walnutsMissed = 0;
		spawnTimer = spawnTime;
	}
	
	public void setScoreMeter(ScoreMeter scoreMeter)
	{
		this.scoreMeter = scoreMeter;
	}
	
	public void setFrustrationMeter(FrustrationMeter frustrationMeter)
	{
		this.frustrationMeter = frustrationMeter;
	}

	
	public void start()
	{
		running = true;
	}
	
	public void stop()
	{
		running = false;
	}
	
	public void setSpawnTime(int spawnTime)
	{
		this.spawnTime = spawnTime;
	}
	public void mouseClicked(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mousePressed(MouseEvent event) 
	{
		mouseX = event.getX();
		mouseY = event.getY();
		
		Iterator<Walnut> i;
		i = walnuts.iterator();
		
		while(i.hasNext())
		{
			Walnut tempNut;
			tempNut = i.next();
			if(tempNut.inBounds(mouseX, mouseY))
			{
				nutsToRemove.add(tempNut);
				walnutsCollected++;
				walnutsRemoved++;
				scoreMeter.incrementScore();
			}
		}
		removeNuts();
	}
	public void mouseReleased(MouseEvent event) {}
}