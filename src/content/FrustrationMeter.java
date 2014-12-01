package content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import visual.dynamic.described.RuleBasedSprite;

public class FrustrationMeter extends RuleBasedSprite
{
    // Attributes measured in pixels
    private double                height, left, top, width;

    // Attributes measured in dollars
    private double                maxFrustration, currentFrustration;
    
    // Other Attributes
    private Color                 frustrationColor;


    
    /**
     * Explicit Value Constructor
     * 
     * @param left   The left-most coordinate of the bar (in pixels)
     * @param top    The top-most coordinate of the bar (in pixels)
     * @param width  The width of the bar (in pixels)
     * @param height The height of the bar (in pixels)
     */
    public FrustrationMeter(double left, double top, double width, double height)
    {
    	super(null);
       this.left   = left;
       this.top    = top;
       this.width  = width;
       this.height = height;
       
       maxFrustration = 20;

       frustrationColor = Color.RED;          
    }


    
    /**
     * Set the maximum of this PriceBar (i.e., the maximum price
     * this PriceBar can display)
     * 
     * @param maxInDollars   The maximum price (in dollars)
     */
    public void setMaximum(double maxFrustration)
    {
       this.maxFrustration = maxFrustration;       
    }

    /**
     * Increment the frustration whenever a walnut is missed
     */
    public void incrementFrustration()
    {
    	currentFrustration%=maxFrustration;
    	currentFrustration++;
    }
    
    /**
     * Resets the currentFrustration
     * (Mainly helps at the start of a new round)
     */
    public void resetFrustration()
    {
    	currentFrustration = 0;
    }
    
    /**
     * Set the current price to display
     *
     * @param priceInDollars   The current price (in dollars)
     */
    public void currentFrustration(double currentFrustration)
    {
       this.currentFrustration = currentFrustration;       
    }
    

    /**
     * Render the FrustrationMeter
     */
    public void render(Graphics g)
    {
       double               pixelsPerFrustration, frustrationInPixels, y;       
       Graphics2D           g2; 
       Rectangle2D.Double   bar, outline;       


       // Cast the Graphics object as a Graphics2D object
       g2 = (Graphics2D)g;       

       pixelsPerFrustration = (double)width / maxFrustration;

       frustrationInPixels = pixelsPerFrustration * currentFrustration;       

       // Construct a Rectangle2D for the price
       bar = new Rectangle2D.Double(left,  top,
                                    frustrationInPixels, height);
       
       double colorPerFrustration = 255/maxFrustration;
       
       frustrationColor = new Color((int)(colorPerFrustration * currentFrustration), 0, 0);

       // Render the price
       g2.setColor(frustrationColor);
       g2.fill(bar);

       // Render the tick marks (on top of the price)
       g2.setColor(Color.BLACK);

       // Render the outline (on top of everything)
       g2.setColor(Color.BLACK);
       outline = new Rectangle2D.Double(left, top, width, height);
       g2.draw(outline);

    }

	@Override
	public void handleTick(int arg0) 
	{
		
	}

}
