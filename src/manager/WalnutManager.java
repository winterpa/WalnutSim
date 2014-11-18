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
import content.Walnut;
public class WalnutManager extends DescribedSprite

implements MouseListener
{
	private ArrayList<Walnut> walnuts;
	private TransformableContent walnutImage;
	private Boolean running;
	private int currentX, currentY, height, spawnX, spawnY, width;
	private static Random rng = new Random();
	
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
	
	/*
	 *  Level variables
	 *  
	 *  totalWalnuts - total num of Walnuts to spawn
	 *  spawnTime - time between each spawn of a Walnut
	 *  growTime - time for walnut to 'grow' before falling
	 *  walnutSpeed - speed the walnut falls
	 */
	private int totalWalnuts;
	private double spawnTime, spawnTimer, growTime, walnutSpeed;
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
		spawnTimer = 0.75 * 60;
		this.height = height;
		this.width = width;
		maxWalnuts = 10;
	}
	public void add(double x, double y, int time, int speed)
	{
		walnuts.add(new Walnut(walnutImage, x, y, time, speed));
	}
	public void remove(Walnut walnut)
	{
		walnuts.remove(walnut);
	}
	public void start()
	{
		running = true;
		

	}
	public Walnut getWalnut(int pos)
	{
		return walnuts.get(pos);
	}
	public void setSpawnTime(int spawnTime)
	{
		this.spawnTime = spawnTime;
	}
	@Override
	public void handleTick(int arg0)
	{
		if(running && spawnTimer <= 0 && walnuts.size()<maxWalnuts)
		{
			//System.out.println("Here: " +  arg0);
			walnuts.add(new Walnut(walnutImage, rng.nextInt(spawnX) + 10, rng.nextInt(spawnY), 30, 5));
			spawnTimer = spawnTime; //to pass in
		}
		//render()
		spawnTimer--;
	}
	
	public void render(Graphics g)
	{
		//System.out.println("Oi!");
		Graphics2D g2;
		g2 = (Graphics2D) g;
		//System.out.println("Here");
		Iterator<Walnut> i;
		i = walnuts.iterator();
		
		ArrayList<Walnut> nutsToRemove = new ArrayList<Walnut>();
		while(i.hasNext())
		{
			//System.out.println("Here");
			Walnut tempNut;
			tempNut = i.next();
			tempNut.handleTick(0);
			if((int)tempNut.getY() >= height || tempNut.toDelete() == true)
			{
				nutsToRemove.add(tempNut);
			}
			else
				tempNut.render(g2);
		}
		
		removeNuts(nutsToRemove);
	}	
	
	public void changeLevel(double spawnTime, double growTime, int totalWalnuts, double walnutSpeed)
	{
		this.spawnTime = spawnTime * 60;
		this.growTime = growTime;
		this.totalWalnuts = totalWalnuts;
		this.walnutSpeed = walnutSpeed;
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
			System.out.println("Number of nuts: " + walnuts.size() + "Nuts removed: " + removed);
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) 
	{
		//System.out.println("Event-1");
		//toDelete = true;
		currentX = e.getX();
		currentY = e.getY();
		Walnut tempNut;
		Iterator<Walnut> i;
		ArrayList<Walnut> nutsToRemove = new ArrayList<Walnut>();
		System.out.println("Mouse click:" + currentX + ", " + currentY);
		i = walnuts.iterator();
		while(i.hasNext())
		{
			System.out.println("Inside Walnuts");
			tempNut = i.next();
			//if((currentX>= tempNut.getX() && currentX <= (tempNut.getX() + tempNut.getWidth())) && (currentY>= tempNut.getY() && currentY <= (tempNut.getY() + tempNut.getHeight())))
			if(tempNut.inBounds(currentX, currentY))
			{
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				tempNut.setDeleteTrue();
				//remove(tempNut);
				nutsToRemove.add(tempNut);
			
			}
		}
	
		removeNuts(nutsToRemove);
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
