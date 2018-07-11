import java.awt.*;

public class Sprite2D {

    protected double x, y;
	protected Image myImage, myImage2;;

	protected static int winWidth;
	private int framesDrawn;

	public Sprite2D(Image i, Image i2)
	{	
		myImage = i;
		myImage2 = i2;
	}

	public void paint(Graphics g) {	
		framesDrawn++;
		if ( framesDrawn%100<50 )
		g.drawImage(myImage, (int)x, (int)y, null);
		else
		g.drawImage(myImage2, (int)x, (int)y, null);
		}
	
	

	public void setPosition(double xx, double yy) {
		
		x = xx;
		y = yy;
		
	}

	public static void setWinWidth(int w) {
		winWidth = w;
		
	}
	
	public int getImageHeight()
	{
		Image i = myImage;
		return i.getHeight(null);
	}
	
	public int getImageWidth()
	{
		Image i = myImage;
		return i.getWidth(null);
	}
}