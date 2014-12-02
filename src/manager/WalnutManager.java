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

public class WalnutManager extends DescribedSprite implements MouseListener
{
	//The random number generator to spawn the walnuts
    	private static Random rng = new Random();
    	
    	//The list to hold the walnuts to spawn/are spawned in, The list of walnuts to remove
	private ArrayList<Walnut> walnuts, nutsToRemove;
	
	//The image of the walnuts to render with
	private TransformableContent walnutImage;
	
	//The state of the walnut manager is running or not
	private Boolean running;
	
	//The height of the app
	private int height
	
	//The spawn positions of the walnuts
	private int spawnX, spawnY;
	
	//The details of the current level
	private double[] currentLevel;
	
	//The delay in between spawning walnuts
	private double spawnTimer;
	
	//The score meter of the app
	private ScoreMeter scoreMeter;
	
	//The frustration meter of the app
	private FrustrationMeter frustrationMeter;
	
	// Current number of walnuts being rendered
	private int currentWalnuts;
	
	// Max number of walnuts on-screen
	private int maxWalnuts;
	
	// Number of walnuts spawned
	private int walnutsSpawned;
	
	// Number of walnuts removed (clicked or falls below screen)
	private int walnutsRemoved;
	
	// Number of walnuts collected (clicked)
	/* to be used by score */
	private int walnutsCollected;
	
	// Number of walnuts missed (falls below screen)
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
	
	//Records if the stage is cleared of all walnuts
	private boolean stageClear;
	
	//Number of walnuts that can be missed in a level
	private final int maxCanMiss = 20;
	
	//Number of walnuts to collect to win the level
	private final int walnutsToWin = 9999;
	
	/**
	* Default Constructor
	*/
	public WalnutManager()
	{
		this(0, 0, null);
	}
	
	/**
	* Explicit Value Constructor
	*
	* @param contentFactory The ContentFactory used to create each walnut object
	* @param width The width of the app (in pixels)
	* @param height The height of the app (in pixels)
	*/
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
	
	/**
	* Adds a walnut to the manager
	*
	* @param x The x-value to start the walnut at
	* @param y The y-value to start the walnut at
	* @param growTime The time it takes for the walnuts to grow
	* @param walnutSpeed The speed at which the walnuts fall
	*/
	public void add(int x, int y, double growTime, double walnutSpeed)
	{
		walnuts.add(new Walnut(walnutImage, x, y, growTime, walnutSpeed));
	}
	
	/**
	* Changes the level the game is on
	*
	* @param spawnTime The delay between each walnuts spawn time
	* @param growTime The time it takes for the walnuts to grow
	* @param totalWalnuts The total number of walnuts to spawn for that level
	* @param walnutSpeed The speed at which the walnuts fall
	*/
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
	
	/**
	* Clears all the walnuts from the manager
	*/
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
	
	/**
	* Returns true is the manager is currently spawning walnuts or not
	* 
	* @return The running state of the walnut manager
	*/
	public boolean isRunning()
	{
		return running;
	}
	
	/**
	* Returns true if there are no walnuts being rendered
	* 
	* @return The state of the stage
	*/
	public boolean isStageClear()
	{
		return stageClear;
	}
	
	/**
	* Handles the next "tick" of the game
	* 
	* @param arg0
	*/
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
	
	/**
	* Resets all of the walnuts managers values
	*/
	public void reset()
	{
		frustrationMeter.resetFrustration();
		this.clearWalnuts();
		this.resetValues();
		this.stop();
	}
	
	/**
	* Renders each walnut inside of the walnut manager
	* 
	* @param g The graphics engine to render with
	*/
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

	/**
	* Removes the walnut from the walnut manager
	* 
	* @param walnut The walnut to remove
	*/
	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
		currentWalnuts--;
	}

	/**
	* Removes the walnuts that are meant to be removed
	*/
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
	
	/**
	* Called by reset() to reset some of the values 
	*/
	public void resetValues()
	{
		currentWalnuts = 0;
		walnutsSpawned = 0;
		walnutsRemoved = 0;
		walnutsCollected = 0;
		walnutsMissed = 0;
		spawnTimer = spawnTime;
	}
	
	/**
	* Sets the score meter so the manager can alter the score
	* 
	* @param scoreMeter The score meter of the app
	*/
	public void setScoreMeter(ScoreMeter scoreMeter)
	{
		this.scoreMeter = scoreMeter;
	}
	
	/**
	* Sets the frustration meter so the manager can alter the frustration
	* 
	* @param frustrationMeter The frustration meter of the app
	*/
	public void setFrustrationMeter(FrustrationMeter frustrationMeter)
	{
		this.frustrationMeter = frustrationMeter;
	}

	/**
	* Start running the walnut manager
	*/
	public void start()
	{
		running = true;
	}
	
	/**
	* Stop running the walnut manager
	*/
	public void stop()
	{
		running = false;
	}
	
	/**
	* Set the spawn time of the level
	*/
	public void setSpawnTime(int spawnTime)
	{
		this.spawnTime = spawnTime;
	}
	
	public void mouseClicked(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	
	/**
	* Handles the event that occurs when a mouse is clicked
	* 
	* @param event The mouse event to parse
	*/
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
