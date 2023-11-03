package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Entity.Entity;
import Entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable
{

	// might change settings later to be bigger
	
	public int baseTileSize = 16; // 16x16 tile
	public int scaler = 3;
	
	public int TileSize = baseTileSize * scaler; // 48x48 tile
	
	
	public int maxScreenCol = 32;
	public int maxScreenRow = 24;
	
	public int screenWidth = TileSize * maxScreenCol; 
	public int screenHeight = TileSize * maxScreenRow; 
	

	// FPS
	int FPS = 60;
	
	// WORLD SETTINGS // may change
	public static int maxWorldCol = 350;
	public static int maxWorldRow = 350;
	public int worldWidth = TileSize * maxWorldCol;
	public int worldHeight = TileSize * maxWorldRow;
	
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	

	
	public CollisionCheck collisCheck = new CollisionCheck(this);
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	
	public static Thread gameThread;
	
	//Entity/Object
	public Player player = new Player(this,keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[460];
	public ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	
	
		
	
	//Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	
	
	
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // gives better rendering performance
		
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setUpGame()
	{
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		
		
		//playMusic(0);
		
		gameState = titleState;
		
	}

	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	@Override
	public void run() 
	{
		double drawInterval = 1000000000/FPS; // = 1 second/60 seconds  = 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null)
		{
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1)
			{
				update(); //update info on position
				repaint(); //paint that position
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000)
			{
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
				
		}
		
	}
	
	
	public void update()
	{
		if(gameState == playState)
		{
			
			
			//player
			player.update();
			
			//NPC
			for(int i = 0; i < npc.length; i++)
			{
				if(npc[i] != null)
				{
					npc[i].update();
				}
			}
			
			//MONSTER
			for(int i = 0; i < monster.length; i++)
			{
				if(monster[i] != null)
				{
					if(monster[i].alive == true && monster[i].dying == false)
					{
						monster[i].update();
					}
					if(monster[i].alive == false)
					{
						monster[i] = null;
					}
				}
			}
			
			//FLAMES
			for(int i = 0; i < projectileList.size(); i++)
			{
				if(projectileList.get(i) != null)
				{
					if(projectileList.get(i).alive == true)
					{
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false)
					{
						projectileList.remove(i);
					}
				}
			}
			
		}
		if(gameState == pauseState)
		{
			///nothing for now
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState)
		{
			ui.draw(g2);;
		}
		else
		{
			//TILE
			tileM.draw(g2);
			
			//Add Entities To List
			for(int i = 0; i < obj.length; i++)
			{
				if(obj[i] != null)
				{
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < npc.length; i++)
			{
				if(npc[i] != null)
				{
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < monster.length; i++)
			{
				if(monster[i] != null)
				{
					entityList.add(monster[i]);
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++)
			{
				if(projectileList.get(i) != null)
				{
					entityList.add(projectileList.get(i));
				}
			}
			
			entityList.add(player);
			
			
			//Draw Entity List
			for(int i = 0; i < entityList.size(); i++)
			{
				entityList.get(i).draw(g2);
			}
			//Empty List
			entityList.clear();
		
			//UI
			ui.draw(g2);;
		}
		
		
		g2.dispose();
	}
	
	public void playMusic(int i)
	{
		music.setFile(i);
		music.Play();
		music.loop();
	}
	
    public void stopMusic()
   {
    	music.stop();
   }
    
    public void playSE(int i)
    {
    	se.setFile(i);
    	se.Play();
    }
	

	
}



