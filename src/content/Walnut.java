package content;

import java.awt.geom.Rectangle2D;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

public class Walnut extends RuleBasedSprite
{
	private double                    x, y;      
    private TransformableContent      content;
    private boolean					  isRendered;
    private int						  time, timeLeft;
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
       isRendered   = true;
       this.time 	= time;
       timeLeft 	= this.time;  

       this.x = x;       
       this.y = y;       
       this.speed = 5;
       bounds = content.getBounds2D(false);
       
       setLocation(x, y);       
       setVisible(true);
    }
	
	public boolean isRendered()
	{
		return isRendered;
	}

	@Override
	public void handleTick(int time) 
	{
		timeLeft--;
		
		if(timeLeft <=0)
			isRendered = false;
		
		y += speed;
		setLocation(x,y);
	}
	
	public Rectangle2D getBounds2D()
    {
       return bounds;
    }
}
