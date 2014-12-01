package manager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import visual.dynamic.described.DescribedSprite;
import visual.statik.TransformableContent;
import visual.statik.sampled.ContentFactory;
import content.FrustrationMeter;
import content.ScoreMeter;
import content.Walnut;
public class WalnutManager extends DescribedSprite implements MouseListener,KeyListener
{
	private ArrayList<Walnut> walnuts;
	private TransformableContent walnutImage;
	private Boolean running;
	private int currentX, currentY, height, spawnX, spawnY, width;
	private static Random rng;
	private Level level;
	private int currentNumWalnutsRendered, maxWalnutsOnScreen, walnutsSpawned, walnutsRemoved;
	private ScoreMeter scoreMeter;
	private FrustrationMeter frustrationMeter;
	
	// num of walnuts collected (clicked)
	/* to be used by score */
	public int walnutsCollected;
	
	// num of walnuts missed (falls below screen)
	/* to be used by frustration */
	public int walnutsMissed;
	
	private double spawnTimer;
	
	public WalnutManager()
	{
		this(0, 0, null);
		rng = new Random();
	}
	
	public WalnutManager(int width, int height, ContentFactory contentFactory)
	{
		walnutImage = contentFactory.createContent("walnut.png", 4);
		walnuts = new ArrayList<Walnut>();
		running = false;
		//to pass in
		spawnX = 450;
		spawnY = 300;
		level = new Level();
		spawnTimer = 0.75 * 60;
		this.height = height;
		this.width = width;
		maxWalnutsOnScreen = 10;
		
		rng = new Random();
	}
	
	public void add(double x, double y, int time, int speed)
	{
		walnuts.add(new Walnut(walnutImage, x, y, time, speed));
	}
	
	public void changeLevel(double spawnTime, double growTime, int totalWalnuts, double walnutSpeed)
	{
		level.spawnTime = spawnTime * 60;
		level.growTime = growTime;
		level.totalWalnuts = totalWalnuts;
		level.walnutSpeed = walnutSpeed;
	}
	
	public Level getLevel()
	{
		return level;
	}

	public Walnut getWalnut(int pos)
	{
		return walnuts.get(pos);
	}
	
	@Override
	public void handleTick(int arg0)
	{
		if(running && spawnTimer <= 0 && walnuts.size()<maxWalnutsOnScreen)
		{
			walnuts.add(new Walnut(walnutImage, rng.nextInt(spawnX) + 10, rng.nextInt(spawnY), 30, 5));
			spawnTimer = level.spawnTime; //to pass in
		}
		spawnTimer--;
		
		while(walnutsCollected > 0)
		{
			scoreMeter.incrementScore();
			walnutsCollected--;
		}
		walnutsCollected = 0;
		
		while(walnutsMissed > 0)
		{
			frustrationMeter.incrementFrustration();
			walnutsMissed--;
		}
		walnutsMissed = 0;
	}
	
	public void pause()
	{
		running = !running;
	}

	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
	}

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
			if((int)tempNut.getY() >= height || tempNut.toDelete() == true)
			{
				nutsToRemove.add(tempNut);
				walnutsMissed++;
			}
			else
				tempNut.render(g2);
		}
		
		removeNuts(nutsToRemove);
	}
	
	public void setScoreMeter(ScoreMeter scoreMeter)
	{
		this.scoreMeter = scoreMeter;
	}
	
	public void setFrustrationMeter(FrustrationMeter frustrationMeter)
	{
		this.frustrationMeter = frustrationMeter;
	}

	public void setSpawnTime(int spawnTime)
	{
		level.spawnTime = spawnTime;
	}
	public void start()
	{
		running = true;
	}

	public void removeNuts(ArrayList<Walnut> nutsToRemove)
	{
		int removed;
		removed = 0;
		
		Iterator<Walnut> i;
		i = nutsToRemove.iterator();
		while(i.hasNext())
		{
			Walnut tempNut;
			tempNut = i.next();
			remove(tempNut);
			removed += 1;
			//System.out.println("Number of nuts: " + walnuts.size() + "Nuts removed: " + removed);
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) 
	{
		if (running)
		{
			currentX = e.getX();
			currentY = e.getY();
			Walnut tempNut;
			Iterator<Walnut> i;
			ArrayList<Walnut> nutsToRemove = new ArrayList<Walnut>();
			i = walnuts.iterator();
			while(i.hasNext())
			{
				System.out.println("Inside Walnuts");
				tempNut = i.next();
				if(tempNut.inBounds(currentX, currentY))
				{
					tempNut.setDeleteTrue();
					//remove(tempNut);
					nutsToRemove.add(tempNut);
					walnutsCollected++;
				}
			}
		
			removeNuts(nutsToRemove);
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		System.out.println(e.getKeyChar() + "P");
	}
	public void keyReleased(KeyEvent e) 
	{
		System.out.println(e.getKeyChar() + "R");
	}
	public void keyTyped(KeyEvent e)
	{
		System.out.println(e.getKeyChar() + "T");
	}
}
