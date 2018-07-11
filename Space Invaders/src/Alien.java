import java.awt.Image;

public class Alien extends Sprite2D {

	private static double xSpeed = 0;
	public boolean isDead;
	
	public Alien(Image i, Image i2)
	{
		super(i, i2);
	}
	
	public boolean move()
	{
            x+= xSpeed;
            
            if(x<=0 || x>=750)
            	return true;
            else 
            	return false;
	}

	public static void setFleetXSpeed(double dx)
	{
		xSpeed = dx;
	}
	
	public static void reverseDirection() {
		xSpeed = -xSpeed;
		
	}

	public void jumpDownwards() {
		y+=20;
		
	}
	
}