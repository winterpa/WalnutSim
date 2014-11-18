package content;

import java.awt.geom.Rectangle2D;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

public class Walnut extends RuleBasedSprite
{
	private int	                      x, y;      
    private boolean					  toDelete;
    private double					  currentTime;
    private double					  growTime;
    private double					  walnutSpeed;
    private Rectangle2D				  bounds;
    
    //debugging
    private int id;
    
    /**
     * Explicit Value Constructor
     *
     * @param content   The static visual content
     * @param width     The width of the Stage
     * @param height    The height of the Stage
     */
    public Walnut(TransformableContent content,

                          int x, int y, double growTime, double walnutSpeed, int id)

    {
       super(content);
       this.toDelete   = false;
       this.growTime = growTime * 60;

       this.currentTime = 0;  

       this.x = x;       
       this.y = y;       
       this.walnutSpeed = walnutSpeed;
       this.bounds = content.getBounds2D(false);

       setLocation(x, y);
	   this.bounds.setRect(x,y,55,55);
       setVisible(true);
       
       this.id = id;
    }
    
	public boolean contains(int mX, int mY)
	{
		return bounds.contains(mX, mY);
	}
    
    public int getID()
    {
    	return id;
    }

	@Override
	public void handleTick(int time) 
	{
		currentTime++;
		
		//finished growing
		if(currentTime > growTime)
		{
			//fall down
			y += walnutSpeed;
			setLocation(x,y);
			bounds.setRect(x,y,55,55);
		}
		
		if(y > 750)
			toDelete = true;
	}
	
	public Rectangle2D getBounds2D()
	{
		return bounds;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void print()
	{
		System.out.println( this.toString() );
	}
	
	public boolean toDelete()
	{
		return toDelete;
	}
	
	public void setDeleteTrue()
	{
		toDelete = true;
	}
	
	public String toString()
	{
		String result;
		
		result = "Walnut " + id + " Bounds: " + bounds.toString();
		
		return result;
	}
}
