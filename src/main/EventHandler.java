package main;
import java.awt.Rectangle;


public class EventHandler 
{
	GamePanel gp;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gp)
	{
		this.gp = gp;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}
	
	public void checkEvent()
	{         //0s are position col, row, direction
		if(hit(0,0,"right") == true)
		{
			//what happens
		}
	}
	public boolean hit(int eventCol, int eventRow, String reqDirtrection)
	{
		boolean hit = false;
		
		gp.player.hitBox.x = gp.player.worldx + gp.player.hitBox.x;
		gp.player.hitBox.y = gp.player.worldy + gp.player.hitBox.y;
		eventRect.x = eventCol * gp.TileSize + eventRect.x;
		eventRect.y = eventRow * gp.TileSize + eventRect.y;
		
		if(gp.player.hitBox.intersects(eventRect)) //if collision then hit is true
		{
			if(gp.player.direction.contentEquals(reqDirtrection) || reqDirtrection.contentEquals("any"))
			{
				hit = true;
			}
		}
		
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		
		
		return hit;
	}
}
