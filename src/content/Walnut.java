package content;

import java.util.Random;

import visual.dynamic.described.AbstractSprite;
import visual.statik.TransformableContent;

public class Walnut extends AbstractSprite
{
	private double                    x, y;      
    private TransformableContent      content;
    private boolean					  isRendered;
    private int						  time, timeLeft;
    
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
       super();
       this.content = content;
       isRendered   = true;
       this.time 	= time;
       timeLeft 	= this.time;  

       this.x = x;       
       this.y = y;       
       setLocation(x, y);       

       setVisible(true);
    }
	
	@Override
	protected TransformableContent getContent() 
	{
		return content;
	}
	
	public boolean isRendered()
	{
		return isRendered;
	}

	@Override
	public void handleTick(int arg0) 
	{
		timeLeft--;
		
		if(timeLeft <=0)
			isRendered = false;
	}
}
