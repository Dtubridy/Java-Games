import java.awt.Image;

public class PlayerBullet extends Sprite2D{
	
	boolean collided;
	
	public PlayerBullet(Image i)
	{
		super(i,i);
	}
	
	public boolean move()
	{	
			y += -10;	
			return(y<0);
			
	}	
	
	public boolean checkForCollision(Alien a)
	{
		if (((a.x<this.x && a.x+a.getImageWidth()>this.x) || (this.x<a.x && this.x+this.getImageWidth()>a.x))
				&& ((a.isDead == false))
				&& ( (a.y<this.y && a.y+a.getImageHeight()>this.y) || (this.y<a.y && this.y+this.getImageHeight()>a.y)))
				
		{
	    collided = true;		
		return true;
	}
		return false;
	}
	
}