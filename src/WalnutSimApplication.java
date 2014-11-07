import app.*;

import javax.swing.*;

public class WalnutSimApplication extends MultimediaApplication {

	public static void main(String[] args) throws Exception
	{
		SwingUtilities.invokeAndWait(new WalnutSimApplication(args, 500, 700));

	}
	
	public WalnutSimApplication(String[] args, int width, int height)
	{
		super(args, new WalnutSimApp(), width, height);
	}
}
