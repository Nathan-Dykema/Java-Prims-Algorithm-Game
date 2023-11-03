package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import Entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;

public class UI 
{

	GamePanel gp;
	
	Graphics2D g2;
	
	Font arial_40;
	Font maruMonica;
	BufferedImage keyImage;
	BufferedImage heart_full, heart_half, heart_blank;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		
		
		
		try 
		{
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} 
		catch (FontFormatException | IOException e) 
		{
			e.printStackTrace();
		}
		
		
		
		//create HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		
		//create key object
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
		
		
	}
	
	
	
	
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	public void drawPauseScreen()
	{
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		
		String text = "PAUSED";
		
		int x = getXforCenteredText(text);
		
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
		
		
		g2.drawString("TIME: " + dFormat.format(playTime), gp.TileSize/2, gp.TileSize * 23);
	}
	
	
	
	
	
    public void drawPlayerLife()
	{
		int x = gp.TileSize*15-28;
		int y = gp.TileSize*20;
		int i = 0;
		
		//Draw Max Life
		while(i < gp.player.maxLife/2) //each heart is 2 hp
		{
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x +=gp.TileSize;
		}
		//Reset
		x = gp.TileSize*15-28;
		y = gp.TileSize*20;
		i = 0;
		
		//Draw Current Life
		while(i < gp.player.life)
		{
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life)
			{
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.TileSize;
		}
		
	}


	
	public int getXforCenteredText(String text)
	{
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	
	public void drawTitleScreen()
	{
		//Title Name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,120F));
		String text = "L O S T";
		int x = getXforCenteredText(text);
		int y = gp.TileSize * 7;
		
		//Shadow
		g2.setColor(new Color(75,0,130));
		g2.drawString(text, x+5, y+5);
		
		//Main Color
		g2.setColor(new Color(138,43,226));
		g2.drawString(text, x, y);
		
		//Character Image
		x = gp.screenWidth/2 - (gp.TileSize*2) /2;
		y += gp.TileSize*3;
		g2.drawImage(gp.player.standing, x, y, gp.TileSize*2, gp.TileSize*2, null);
		
		//Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		g2.setColor(new Color(0,191,255));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.TileSize*6;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString("> ", x - gp.TileSize, y);	
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.TileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1)
		{
			g2.drawString("> ", x - gp.TileSize, y);	
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.TileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2)
		{
			g2.drawString("> ", x - gp.TileSize, y);	
		}
	}
	
	public void drawDialogueStateScreen()
	{
		//WINDOW
		int x = gp.TileSize * 2;
		int y = gp.TileSize;
		int width = gp.screenWidth - (gp.TileSize * 4);
		int height = gp.TileSize * 7;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));
		x += gp.TileSize;
		y += gp.TileSize;
		
		for(String line: currentDialogue.split("\n")) //making text fit into box
		{
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height)
	{
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
	}
	
	
	public void draw(Graphics2D g2)
	{
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState)
		{
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState)
		{
			drawPlayerLife();
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState)
		{
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState)
		{
			drawPlayerLife();
			drawDialogueStateScreen();
		}
		
		
		
		
		
		
		if(gameFinished == true && gp.player.hasKey == 0)
		{
			gameFinished = false;
			
		}
		
		if(gameFinished == true)
		{
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
			g2.setColor(Color.WHITE);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "YOU FOUND THE WAY OUT!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.TileSize * 3);
			g2.drawString(text, x, y);
			
			///////////////////////////////
			
			if(playTime < 59)
			{
				text = "AMOUNT OF TIME WASTED - " + dFormat.format(playTime) + " SECONDS";
			}
			else if(playTime > 59)
			{
				String seconds = "";
				int temp;
				int temp2;
				temp = (int) (playTime / 60);
				temp2 = (int) (playTime % 60);
				seconds = Integer.toString(temp2);
				
				text = "AMOUNT OF TIME WASTED - " + temp + "." + seconds + " MINUTES";
			}
			
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.TileSize * 4);
			g2.drawString(text, x, y);
			
			///////////////////////////////
			
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
			g2.setColor(Color.YELLOW);
			
			text = "CONGRATULATIONS!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.TileSize * 2);
			g2.drawString(text, x, y);
					
			/////////END GAME
			gp.stopMusic();
			GamePanel.gameThread = null;
		}
		else if(gp.gameState == gp.playState)
		{
			
			    g2.setFont(maruMonica);
			    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
				g2.setColor(Color.WHITE);
				
				g2.drawImage(keyImage, 74, 50, gp.TileSize, gp.TileSize, null);
				g2.drawString("Keys x " + gp.player.hasKey, 74, 55);
				
				
				//TIME
				playTime += (double)1/60;
				g2.drawString("TIME: " + dFormat.format(playTime), gp.TileSize/2, gp.TileSize * 23);
				
				
				//message on screen
				if(messageOn == true)
				{
					g2.setFont(g2.getFont().deriveFont(30F));
					g2.drawString(message, gp.TileSize/2, gp.TileSize * 5);
					
					messageCounter++;
					
					if(messageCounter > 120)
					{
						messageCounter = 0;
						messageOn = false;
					}
				}
				
			
			
		}
		
		
	}

}
