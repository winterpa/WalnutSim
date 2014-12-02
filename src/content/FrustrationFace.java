package content;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;
import visual.statik.sampled.ContentFactory;

public class FrustrationFace extends RuleBasedSprite
{
	private double						  						   x,y;
	private int 						  time, timeLeft, curFaceIndex;
	private ContentFactory 								contentFactory;
	private TransformableContent							   curFace;
	private TransformableContent[]								 faces;
	
		
	public FrustrationFace()
	{
		this(0.0, 0.0, 0, null);
	}
	
	/**
	* Explicit Value Constructor
	*
	* @param x The left-most coordinate of the bar (in pixels)
	* @param y The top-most coordinate of the bar (in pixels)
	* @param time The time between changes poses of the face
	* @param ContentFactory The factory used to make the image
	*/
	public FrustrationFace(double x, double y, int time, ContentFactory contentFactory)
	{
		super(null);
		
		this.contentFactory = contentFactory;
		faces = new TransformableContent[3];
		setContent();
		
		this.x = x;
		this.y = y;
		this.time = time;
		timeLeft = this.time;
		curFaceIndex = 0;
	}
	
	/**
	* Set the content of the face
	*/
	private void setContent()
	{
		for(int x=0;x<faces.length;x++)
		{
			faces[x] = contentFactory.createContent("face" + x + ".png");
		}
		this.content = faces[1];
	}
	
	/**
	* Ticks down the time between each facial change and changes the face if necessary
	*
	* @param arg0 Unused
	*/
	@Override
	public void handleTick(int arg0) 
	{
		timeLeft--;
		
		if(timeLeft == 0)
		{
			curFaceIndex++;
			timeLeft = time;
		}
		
		if(curFaceIndex>=faces.length)
			curFaceIndex = 0;
		
		content = faces[curFaceIndex];
	}
}
