import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

public class ConwaysGameOfLife extends JFrame implements Runnable, MouseListener, MouseMotionListener {
	
	private BufferStrategy strategy;
	private Graphics offscreenbuffer;
	private boolean gameState[][][] = new boolean[40][40][2];
	private boolean isGameRunning = false;
	private boolean initialised;
	private int gameStateFrontBuffer = 0;
	private static String workingDirectory;
	
	public ConwaysGameOfLife () {
		workingDirectory = System.getProperty("user.dir");
		System.out.println("Working Directory = " + workingDirectory);
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - 400;
        int y = screensize.height/2 - 400;
        setBounds(x, y, 800, 800);
        setVisible(true);
    	this.setTitle("Conway's game of life");
    	
    	 Thread t = new Thread(this);
         t.start();
    	createBufferStrategy(2);
    	strategy = getBufferStrategy();
    	
        
		
        addMouseListener(this);
        addMouseMotionListener(this);
        
        for(int m=0;m<40;m++){
        		for(int n=0;n<40;n++){
        			for(int z = 0; z<2; z++){
        			gameState[m][n][z] = false;     
        			}
        		}

                initialised = true;
                this.repaint();
                
    			
                
        }
	}
	

	public void run() {
		while ( 1==1 )   {                  
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) { }
			
			
		if(isGameRunning){
			doOneEpochOfGame();	
			this.repaint();	
			}
		}
	}


    public void mousePressed(MouseEvent e) {
		int x = e.getX()/20;
		int y = e.getY()/20;
	   	 
	   	if((e.getX()>= 30 && e.getX()<= 90) && (e.getY()>=40 && e.getY()<=70)){   //coordinate bounds for start button
	   		 isGameRunning = true;                       
	   	}
	   	 
	   	if((e.getX()>= 100 && e.getX()<= 160) && (e.getY()>=40 && e.getY()<=70)){
	   		random();
	   	}
		

		if((e.getX()>= 350 && e.getX()<=410) && (e.getY()>=40 && e.getY()<=70)){
			load();
		}

		if((e.getX()>= 430 && e.getX()<=500) && (e.getY()>=40 && e.getY()<=70)){
			SaveGame();
		}
		
		gameState[x][y][gameStateFrontBuffer] = !gameState[x][y][gameStateFrontBuffer];

		this.repaint();
     }

    public void mouseMoved(MouseEvent e){
    	
    }
    
    public void mouseDragged(MouseEvent e){
	   	int x = e.getX()/20;
	   	int y = e.getY()/20;
	   	gameState[x][y][gameStateFrontBuffer] = true;
	   	this.repaint();
    }
      
     public void mouseReleased(MouseEvent e) { }
     public void mouseEntered(MouseEvent e) { }
     public void mouseExited(MouseEvent e) { }
     public void mouseClicked(MouseEvent e) { }
    
     public void paint(Graphics g) {
    	
		g = strategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);                         
		
		g.setColor(Color.WHITE);
		for (int x=0;x<40;x++) {
			for (int y=0;y<40;y++) {
				if (gameState[x][y][gameStateFrontBuffer] == true) {
					g.fillRect(x*20, y*20, 20, 20);
				}
			}
		}
        g.setColor(Color.GREEN);
        
        g.fillRect(15, 40, 70, 35);
        g.fillRect(105, 40, 100, 35);
        g.fillRect(350, 40, 60, 35);
        g.fillRect(430, 40, 70, 35);
       	g.setFont(new Font("Times", Font.PLAIN, 24));
        g.setColor(Color.BLACK);
        g.drawString("Start", 22, 65);
        g.drawString("Random", 115, 65);
       	g.drawString("Load", 355, 65);
       	g.drawString("Save", 445, 65);
		
		// flip the buffers
		g.dispose();
		strategy.show();
	}
    private void load() {
    		String filename = workingDirectory+"C:\\Users\\David\\Desktop\\lifegame.txt";
    		String textInput = null;
    		int mod = 0;
    		char c = 0;
    		try {
    			BufferedReader reader = new BufferedReader(new FileReader(filename));
    			do {
    				try {
    					textInput = reader.readLine();
    					for(int k=0; k<40; k++) {
    						for(int l=0; l<40; l++) {
    							if (mod<1600) {
    								c = textInput.charAt(mod);
    							}
    							if (c=='1') {
    								gameState[k][l][0]=true;
    								System.out.println("Detected");
    								mod++;
    							}
    							else if (c=='0') {
    								gameState[k][l][0]=true;
    								mod++;
    							}
    						}
    					}
    				}
    				catch (IOException e) {
    					
    				}
    			}
    			while (textInput != null);
    			reader.close();
    		}
    		catch(IOException e) {
    
    		}
    }
    private void SaveGame() {
    	String filename = workingDirectory+"C:\\Users\\David\\Desktop\\lifegame.txt";
    		try	{
    			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			String outputtext="";
			for(int j=0;j<40;j++){
				for(int k=0;k<40;k++){
					if(gameState[j][k][0]){
						outputtext = outputtext + "1";
					}
					else{
						outputtext = outputtext + "0";
					}
				}
			}
			writer.write(outputtext);
			writer.close();
        	}
        	catch(IOException e){};
    }
    private void random() {
		for(int i = 0; i< 40; i++) {
			for(int j = 0; j<40; j++) {
				int random = (Math.random()<=0.5) ? 0 : 1;
				if (random ==0) gameState[i][j][gameStateFrontBuffer]= true;
				else gameState[i][j][gameStateFrontBuffer] = false;
			}
		}
	}
	
	private void doOneEpochOfGame() {
	    	int front = gameStateFrontBuffer;
	    	int back = (front+1)%2;
	        for (int x=0;x<40;x++) {
	        	for (int y=0;y<40;y++) {
	        		
	        		int liveneighbours=0;
	        		for (int xx=-1;xx<=1;xx++) {
	        			for (int yy=-1;yy<=1;yy++) {
	        				if (xx!=0 || yy!=0) {
	        					int xxx=x+xx;
	        					if (xxx<0)
	        						xxx=39;
	        					else if (xxx>39)
	        						xxx=0;
	        					int yyy=y+yy;
	        					if (yyy<0)
	        						yyy=39;
	        					else if (yyy>39)
	        						yyy=0;           					
	        					if (gameState[xxx][yyy][front])
	        						liveneighbours++;
	        				}
	        			}
	        		}

	        		if (gameState[x][y][front]) {
	        			if (liveneighbours<2)
	        				gameState[x][y][back] = false;
	        			else if (liveneighbours<4)
	        				gameState[x][y][back] = true; 
	        			else
	        				gameState[x][y][back] = false;
	        		}
	        		else {
	        			if (liveneighbours==3)
	        				gameState[x][y][back] = true;
	        			else
	        				gameState[x][y][back] = false;
	        		}
	        	}
        }

    	gameStateFrontBuffer = back;		
	}
	
	public static void main(String[] args) {
		ConwaysGameOfLife w = new ConwaysGameOfLife();
	}

}