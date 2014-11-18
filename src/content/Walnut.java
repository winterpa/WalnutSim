package content;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

public class Walnut extends RuleBasedSprite
{
    private boolean					  isRendered;
    public boolean 					  toDelete;
	private double                    fullHeight, fullWidth, height, heightGrowthRate, 
		maxX, maxY, percentHeight, percentWidth, speed, timeGrow, timeLeft, width, widthGrowthRate, x, y;   
    private int						  currentTime, time;
    private Rectangle2D               bounds, maxWalnutBounds, minWalnutBounds;
    private TransformableContent      content;
    
    /**
     * Explicit Value Constructor
     *
     * @param content   The static visual content
     * @param width     The width of the Stage
     * @param height    The height of the Stage
     */
    public Walnut(TransformableContent content,
                          double x, double y, double timeLeft, double speed)
    {   	
    	 super(content);
    	 this.content = content;
    	 this.toDelete = false;
    	 this.timeGrow = timeLeft;
    	 this.currentTime = 0;
    	 this.x = x;
    	 this.y = y;
    	 this.speed = speed;
    	 this.bounds = content.getBounds2D(false);
    	 this.maxX = bounds.getWidth() + x;
    	 this.maxY = bounds.getHeight()+ y;
    	 setScale(.1, .1);
    	 setLocation(x, y);
    	 setVisible(true);
    }
	
	/*@Override
	protected TransformableContent getContent() 
	{
		return content;
	}*/
	
	public boolean contains(int mX, int mY)
    {
		return bounds.contains(mX, mY);
    }
	
	public boolean isRendered()
	{
		return isRendered;
	}
	
	public boolean toDelete()
	{
		return toDelete;
	}
	public double getHeight()
	{
		return height;
	}
	public double getWidth()
	{
		return width;
	}
    public double getY()
	{
    	return y;
	}
    public double getX()
	{
	    return x;
	}
    public boolean inBounds(int mX, int mY)
    {
    	boolean inBounds;
    	inBounds = false;
    	if(mX >= x && mX <= maxX)
    	{
    		if(mY >= y && mY <= maxY)
    		{
    			inBounds = true;
    		}
    	}
    	
    	return inBounds;
    }
	public Rectangle2D getBounds2D()
	{
		return bounds;
	}
   
   public void grow()
   {
   //Need to know height and width of image and grow until time left
   //Grow between intial height and width to maxHeight and maxWidth at
   //the rate of constant maxHeight/timeLeft & maxWidth/timeLeft
	   
	   setScale(percentWidth, percentHeight);
	   height += heightGrowthRate;
	   width += widthGrowthRate;
	   percentWidth = fullWidth/width;
	   percentHeight = fullHeight/height;
	   setScale(fullWidth/100, fullHeight/100);

   }
   
   public void fall()
   {
   //Until the walnut hits the ground, drop straight down
   //The walnut has hit the ground when y + height = windowHeight
   y += 1;
   setLocation(x, y);
   //isRendered = false;
   }
	/*@Override
	public void handleTick(int currentTime) 
	{
   //Is timeLeft the time until the walnut drops?
		timeLeft = time - currentTime;
	//If timeLeft > 0 -> grow()
   //else, fall()
		if(timeLeft >=0)
         grow();
      else
      {
         fall();
			isRendered = false;
      }
	}*/
   
	@Override
	public void handleTick(int time)
	{
		currentTime++;
		//finished growing
		
		//Time in milliseconds this Walnut has existed
		//System.out.println("Time: " + currentTime + " X: " + x + " Y: " + y + " maxX: " + maxX + " maxY: " + maxY);
		
		if(currentTime > timeGrow)
		{
			//fall down
			y += speed;
			maxY += speed;
			setLocation(x,y);
		}
		//currently growing
		else
		{
			//grow();
			double scale = (double) currentTime / (double) timeGrow;
			//System.out.println(scale);
			//System.out.println(scale);
			setScale(scale, scale);
			
		}
		if(y > 750)
			toDelete = true;
	}

	public void setDeleteTrue()
	{
		toDelete = true;
	}

}
