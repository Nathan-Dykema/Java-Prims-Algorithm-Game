package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	
	public boolean pressedUP, pressedDOWN, pressedLEFT, pressedRIGHT, pressedF, pressedSpace;
	
	GamePanel gp;
	
	
	public KeyHandler(GamePanel gp)
	{
		this.gp = gp;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) // not used
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
	
		int code = e.getKeyCode(); //gets number associated to key pressed
		
		//TITLE STATE
		if(gp.gameState == gp.titleState)
		{
			if(code == KeyEvent.VK_W)
			{
				gp.playSE(8);
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S)
			{
				gp.playSE(8);
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2)
				{
					gp.ui.commandNum = 0;
				}
			}
			
			if(code == KeyEvent.VK_SPACE)
			{
				gp.playSE(8);
				if(gp.ui.commandNum == 0)
				{
					gp.gameState = gp.playState;
					gp.playMusic(0);
					
				}
				if(gp.ui.commandNum == 1)
				{
					//LOAD
				}
				if(gp.ui.commandNum == 2)
				{
					System.exit(0);
				}
			}
		}
		
		
		//PLAY STATE
		else if(gp.gameState == gp.playState)
		{
			
			if(code == KeyEvent.VK_W)
			{
				pressedUP = true;
			}
			if(code == KeyEvent.VK_S)
			{
				pressedDOWN = true;
			}
			if(code == KeyEvent.VK_A)
			{
				pressedLEFT = true;
			}
			if(code == KeyEvent.VK_D)
			{
				pressedRIGHT = true;
			}
			if(code == KeyEvent.VK_SPACE)
			{
				pressedSpace = true;
			}
			if(code == KeyEvent.VK_F)
			{
				pressedF = true;
			}
			
			
		}
		
		//PAUSE STATE
		if(code == KeyEvent.VK_ESCAPE)
		{
			if(gp.gameState == gp.playState)
			{
				gp.gameState = gp.pauseState;
				
			}
			else if(gp.gameState == gp.pauseState)
			{
				gp.gameState = gp.playState;
				
			}
			
		
		}
		
		//DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState)
		{
			if(code == KeyEvent.VK_SPACE)
			{
				gp.gameState = gp.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W)
		{
			pressedUP = false;
		}
		if(code == KeyEvent.VK_S)
		{
			pressedDOWN = false;
		}
		if(code == KeyEvent.VK_A)
		{
			pressedLEFT = false;
		}
		if(code == KeyEvent.VK_D)
		{
			pressedRIGHT = false;
		}
		if(code == KeyEvent.VK_SPACE)
		{
			pressedSpace = false;
		}
		if(code == KeyEvent.VK_F)
		{
			pressedF = false;
		}
		
		
	}

}
