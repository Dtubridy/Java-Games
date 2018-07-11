import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Iterator;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {

	boolean isDead = false;
	private BufferStrategy strategy;
	private static final Dimension windowSize = new Dimension(800,600);
	private static final int NUMALIENS = 30;
	private Alien[] AliensArray = new Alien[NUMALIENS];
	private Spaceship spaceShip;	
	private Image bulletImage;
	private ArrayList<PlayerBullet> bulletsList = new ArrayList<PlayerBullet>();
	private static int score = 0;
	private boolean isGameInProgress;
	
	
	public InvadersApplication() 
	{

		this.setTitle("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - windowSize.width/2;
		int y = screensize.height/2 - windowSize.height/2;
		setBounds(x , y, windowSize.width, windowSize.height);
		setVisible(true);
		
		ImageIcon icon = new ImageIcon("//bin//alien_ship_1.png");
		Image alienImage = icon.getImage();
		
		icon = new ImageIcon("//bin//alien_ship_2.png");
		Image alienImage2 = icon.getImage();
		
		icon = new ImageIcon("//bin//bullet.png");
		bulletImage = icon.getImage();
			
		for(int i = 0; i<NUMALIENS; i++)
		{
	
			AliensArray[i] = new Alien(alienImage, alienImage2);
			double xx = (i%5)*80 + 70;
			double yy = (i/5)*40 + 50;
			AliensArray[i].setPosition(xx, yy);
				
		}
		
		Alien.setFleetXSpeed(2);
		
		icon = new ImageIcon("//bin//player_ship.png");
		Image shipImage = icon.getImage();
		spaceShip = new Spaceship(shipImage);
		spaceShip.setPosition(300,530);
		
		Sprite2D.setWinWidth(windowSize.width);
		
		Thread t = new Thread(this);
		t.start();
		
		
		
		addKeyListener(this);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
        		
	}
	
	public void run()
	{
		while(true)
		{
				
			try 
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e) {
			}
			
			boolean alienReversal = false;
			for(int i= 0 ; i< NUMALIENS; i++)
			{
				if(AliensArray[i].move())
				alienReversal = true;
			}
			
			if(alienReversal)
			{
				Alien.reverseDirection();
				for(int i=0; i <NUMALIENS; i++)
				{
					AliensArray[i].jumpDownwards();
				}
			}
						
			spaceShip.move();
						
			for(PlayerBullet pb : bulletsList)
			{
				
                pb.move();
                
			}
			for(PlayerBullet pb : bulletsList) 
			{
				for(int i =0; i< NUMALIENS; i++)
				{
					
					if(pb.checkForCollision(AliensArray[i]) == true)
					AliensArray[i].isDead = true;

				}
				}
			
			
			
			
			
			this.repaint();
		}
			
		}
		
	public void startNewGame()
	{
		
	}

	public void startNewWave()
	{
		if(score == 600) {
			
		}
	}
	

	public void shootBullet()
	{
		PlayerBullet b = new PlayerBullet(bulletImage);
		bulletsList.add(b);   
	}
		
	public void paint(Graphics g)
	{
		g = strategy.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		for(int i=0; i <NUMALIENS; i++) 
		{
			if(AliensArray[i].isDead == false)
			AliensArray[i].paint(g);
		
		}
			
		spaceShip.paint(g);
			
		Iterator<PlayerBullet> iterator = bulletsList.iterator();
		while(iterator.hasNext()){
		PlayerBullet b = (PlayerBullet) iterator.next();
		if(b.collided == false)
		b.paint(g);
		
		
		}
		
		
		g.dispose();
		strategy.show();
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			spaceShip.setXSpeed(-5);
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			spaceShip.setXSpeed(5);
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			shootBullet();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT || (e.getKeyCode()==KeyEvent.VK_LEFT))
		{
			spaceShip.setXSpeed(0);
		}
	}
	
	public static void main(String[] args)
	{
		InvadersApplication m = new InvadersApplication();
	}
}