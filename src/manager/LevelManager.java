package manager;

import java.util.*;

public class LevelManager {
	
	private HashMap<Integer, double[]> levels;
	private double[] level;
	private double growTime, spawnTime, walnutSpeed;
	private int totalWalnuts, id;
	private String levelName;
	
	public LevelManager()
	{
		this(0,0.0,0.0,0,0.0);
	}
	

	public LevelManager(int id, double spawnTime, double growTime, int totalWalnuts, double walnutSpeed)
	{
		
		level = new double[4];
		this.spawnTime = spawnTime;
		level[0] = this.spawnTime;
		
		this.growTime = growTime;
		level[1] = this.growTime;
		
		this.totalWalnuts = totalWalnuts;
		level[2] = this.totalWalnuts;
		
		this.walnutSpeed = walnutSpeed;
		level[3] = this.walnutSpeed;
		
		
		this.id = id;
		
		levels = new HashMap<Integer, double[]>();
		
	}
	public int getLevelId()
	{
		return this.id;
	}
	
	public void addLevel(int id, double spawnTime, double growTime, int totalWalnuts, double walnutSpeed)
	{
		level = new double[4];
		level[0] = this.spawnTime;
		level[1] = this.growTime;
		level[2] = this.totalWalnuts;
		level[3] = this.walnutSpeed;
		levels.put(id, level);
	}
	
	public double[] changeLevel(int id)
	{
		level = levels.get(id);
		spawnTime = level[0];
		growTime = level[1];
		totalWalnuts = (int)level[2];
		walnutSpeed = level[3];
		this.id = id;
		return level;
	}
}
