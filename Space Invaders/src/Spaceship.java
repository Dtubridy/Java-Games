import java.awt.*;

public class Spaceship extends Sprite2D {

	private double xSpeed;
	
	public Spaceship(Image i)
	{
		super(i,i);
	}
	
	public void move()
	{
		x+= xSpeed;
		
		if(x<0) {
			x=0;
		    xSpeed = 0;
		}
		    else if(x>750)
		    {
		x= 750;
		xSpeed= 0;
		    }
	}

	public void setXSpeed(int dx) {
	     xSpeed = dx;
		
	}
}