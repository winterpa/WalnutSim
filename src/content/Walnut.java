package content;

import java.awt.geom.Rectangle2D;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

public class Walnut extends RuleBasedSprite
{
    //The x and y position of the upper left corner of the walnut
    private int	                				 x, y;
    
    //The state of whether or not this walnut should be removed from the manager
    private boolean					     toDelete;
    
    //The current "tick" the walnut is on
    private double					  currentTime;
    
    //How long it takes the walnut to grow
    private double					     growTime;
    
    //How fast the walnut will fall from the tree
    private double					  walnutSpeed;
    
    //The boundaries of the walnut image
    private Rectangle2D				  	       bounds;
    
    //debugging
    private int id;
    
    /*
     * maxX and maxY determine the true max X, Y and (X,Y) coordinates
     * of this walnut. Must be of type double to add bounds.getHeight()
     * and bounds.getWidth().
     * 
     * Used by inBounds
     */
    private double maxX;
    private double maxY;
    
    /**
     * Explicit Value Constructor
     *
     * @param content     The static visual content
     * @param width       The width of the Stage
     * @param height      The height of the Stage
     * @param growTime    The time it takes for the walnut to grow
     * @param walnutSpeed The speed the walnut falls down off the tree
     */
    public Walnut(TransformableContent content, int x, int y, double growTime, double walnutSpeed)
    {
       super(content);
       this.toDelete   = false;
       this.growTime = growTime * 60;

       this.currentTime = 0;  

       this.x = x;       
       this.y = y;       
       this.walnutSpeed = walnutSpeed;
       this.bounds = content.getBounds2D(false);
       
       //Setting current max X and Y coordinates
       this.maxX = bounds.getWidth() + x;
       this.maxY = bounds.getHeight() + y;

       setLocation(x, y);
       setScale(.1);
	   //this.bounds.setRect(x,y,55,55);
       setVisible(true);
    }
    	
    	/**
     	* Checks to see if the mouse intersects the walnut
     	*
     	* @param mX 	The x bound of the walnut
     	* @param mY	The y bound of the walnut
     	*/
	public boolean contains(int mX, int mY)
	{
		return bounds.contains(mX, mY);
	}

	/**
     	* Handles the event when the next tick occurs
     	*
     	* @param time	The current "tick" in the time
     	*/
	@Override
	public void handleTick(int time) 
	{
		double scale;
		
		currentTime++;
		
		//finished growing
		if(currentTime > growTime)
		{
			//fall down
			y += walnutSpeed;
			maxY += walnutSpeed;
			setLocation(x,y);
			bounds.setRect(x,y,55,55);
		}
		else
		{
			scale = currentTime / growTime;
			setScale(scale);
		}
		
		if(y > 750)
			toDelete = true;
	}
	
	/**
     	* Returns true if the mouse is in bounds
     	*
     	* @param mX 	X-Value of the Mouse
     	* @param mY	Y-Value of the Mouse
     	* 
     	* @return inBounds
     	*/
	public boolean inBounds(int mouseX, int mouseY)
	{
		boolean inBounds;
		inBounds = false;
		if(mouseX >= x && mouseX <= maxX)
		{
			if(mouseY >= y && mouseY <= maxY)
			{
				inBounds = true;
			}
		}
		
		return inBounds;
	}
	
	/**
     	* Returns true if the mouse is in bounds
     	*
     	* @param mX 	X-Value of the Mouse
     	* @param mY	Y-Value of the Mouse
     	* 
     	* @return The Rectangle2D surronding the image
     	*/
	public Rectangle2D getBounds2D()
	{
		return bounds;
	}
	
	/**
     	* Returns the y-value of the walnut
     	* 
     	* @return The y-value of the upper-left corner of the walnut
     	*/
	public double getY()
	{
		return y;
	}
	
	/**
     	* Returns the x-value of the walnut
     	* 
     	* @return The x-value of the upper-left corner of the walnut
     	*/
	public double getX()
	{
		return x;
	}
	
	/**
     	* Prints toString() to the Console
     	* 
     	*/
	public void print()
	{
		System.out.println( this.toString() );
	}
	
	/**
     	* Returns the current state of the walnut if it's to be deleted
     	* 
     	* @return State of walnut's deletion
     	*/
	public boolean toDelete()
	{
		return toDelete;
	}
	
	public String toString()
	{
		String result;
		
		result = "Walnut " + id + " Bounds: " + bounds.toString();
		
		return result;
	}
}
