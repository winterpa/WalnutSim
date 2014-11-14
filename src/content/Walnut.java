package content;

import java.awt.geom.Rectangle2D;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

public class Walnut extends RuleBasedSprite
{
	private double                    x, y;      
    private TransformableContent      content;
    private boolean					  toDelete;
    private int						  currentTime, timeGrow;
    private int						  speed;
    private Rectangle2D				  bounds;
    
    /**
     * Explicit Value Constructor
     *
     * @param content   The static visual content
     * @param width     The width of the Stage
     * @param height    The height of the Stage
     */
    public Walnut(TransformableContent content,
                          double x, double y, int time)
    {
       super(content);
       this.content = content;
       this.toDelete   = false;
       this.timeGrow = time;
       this.currentTime 	= 0;  

       this.x = x;       
       this.y = y;       
       this.speed = 5;
       this.bounds = content.getBounds2D(false);
       
       setLocation(x, y);       
       setVisible(true);
    }
	
	public boolean toDelete()
	{
		return toDelete;
	}

	@Override
	public void handleTick(int time) 
	{
		currentTime++;
		
		//finished growing
		if(currentTime > timeGrow)
		{
			//fall down
			y += speed;
			setLocation(x,y);
		}
		//currently growing
		else 
		{
			double scale = (double) currentTime / (double) timeGrow;
			//System.out.println(scale);
			content.setScale(scale, scale);
		}	
		
		if(y > 750)
			toDelete = true;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public Rectangle2D getBounds2D()
    {
       return bounds;
    }
}
