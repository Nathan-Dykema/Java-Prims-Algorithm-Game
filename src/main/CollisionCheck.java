package main;

import Entity.Entity;

public class CollisionCheck 
{
	GamePanel gp;

	public CollisionCheck(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void checkTile(Entity entity)
	{   
		// find col and row num of tile
		int entityLeftWorldx = (entity.worldx + entity.hitBox.x);
		int entityRightWorldx = (entity.worldx + entity.hitBox.x + entity.hitBox.width);
		int entityTopWorldy = (entity.worldy + entity.hitBox.y);
		int entityBottomWorldy = (entity.worldy + entity.hitBox.y + entity.hitBox.height);
		
		int entityLeftCol = entityLeftWorldx / gp.TileSize;
		int entityRightCol = entityRightWorldx / gp.TileSize;
		int entityTopRow = entityTopWorldy / gp.TileSize;
		int entityBottomRow = entityBottomWorldy / gp.TileSize;
		
		int tileNum1 = 0, tileNum2 = 0;
		
		if(entity.direction == "up")
		{
			entityTopRow = ((entityTopWorldy - entity.speed) / gp.TileSize); 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
		}
		else if(entity.direction == "down")
		{
			entityBottomRow = (int) ((entityBottomWorldy + entity.speed) / gp.TileSize); 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
		}
		else if(entity.direction == "left")
		{
			entityLeftCol = (int) ((entityLeftWorldx - entity.speed) / gp.TileSize); 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
		}
		else if(entity.direction == "right")
		{
			entityRightCol = (int) ((entityRightWorldx + entity.speed) / gp.TileSize); 
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
		}
		
		if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
		{
			entity.collisionOn = true;
		}
	}
	
	public int checkObject(Entity entity, boolean player) //checks collision of an object
	{
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++)
		{
			if(gp.obj[i] != null)
			{
				entity.hitBox.x = entity.worldx + entity.hitBox.x;
				entity.hitBox.y = entity.worldy + entity.hitBox.y;
				
				
				gp.obj[i].hitBox.x = gp.obj[i].worldx + gp.obj[i].hitBox.x;
				gp.obj[i].hitBox.y = gp.obj[i].worldy + gp.obj[i].hitBox.y;
				
				
				
				switch(entity.direction)
				{
				case "up":
					break;
					
				case "down":
					entity.hitBox.y += entity.speed;
					break;
					
				case "left":
					entity.hitBox.x -= entity.speed;
					break;
					
				case "right":
					entity.hitBox.x += entity.speed;
					break;
				}
				
				if(entity.hitBox.intersects(gp.obj[i].hitBox))
				{
					if(gp.obj[i].collision == true)
					{
						entity.collisionOn = true;
					}
					
					if(player == true)
					{
						index = i;
					}
				}
				
				entity.hitBox.x = entity.hitBoxDefaultX;   //reset values
				entity.hitBox.y = entity.hitBoxDefaultY;
				
				gp.obj[i].hitBox.x = gp.obj[i].hitBoxDefaultX;
				gp.obj[i].hitBox.y = gp.obj[i].hitBoxDefaultY;
						
			}
		}
		
		
		
		
		return index;
	}
	
	
	//MONSTER COLLISION
	public int checkEntity(Entity entity, Entity[] target)
	{
		
        int index = 999;
		
		for(int i = 0; i < target.length; i++)
		{
			if(target[i] != null)
			{
				entity.hitBox.x = entity.worldx + entity.hitBox.x;
				entity.hitBox.y = entity.worldy + entity.hitBox.y;
				
				
				target[i].hitBox.x = target[i].worldx + target[i].hitBox.x;
				target[i].hitBox.y = target[i].worldy + target[i].hitBox.y;
				
				
				
				switch(entity.direction)
				{
				case "up":
					entity.hitBox.y -= entity.speed;
					break;
					
				case "down":
					entity.hitBox.y += entity.speed;
					break;
					
				case "left":
					entity.hitBox.x -= entity.speed;
					break;
					
				case "right":
					entity.hitBox.x += entity.speed;
					break;
				}
				
				if(entity.hitBox.intersects(target[i].hitBox))
				{
					if(target[i] != entity)
					{
						entity.collisionOn = true;
						index = i;
					}
				}
				
				entity.hitBox.x = entity.hitBoxDefaultX;   //reset values
				entity.hitBox.y = entity.hitBoxDefaultY;
				
				target[i].hitBox.x = target[i].hitBoxDefaultX;
				target[i].hitBox.y = target[i].hitBoxDefaultY;
						
			}
		}
		
		return index;
	}
	
	
	public boolean checkPlayer(Entity entity) //if npc hits player
	{
		boolean contactPlayer = false;
		
		
		//get position of entity
		entity.hitBox.x = entity.worldx + entity.hitBox.x;
		entity.hitBox.y = entity.worldy + entity.hitBox.y;
		
		//get position of object
		gp.player.hitBox.x = gp.player.worldx + gp.player.hitBox.x;
		gp.player.hitBox.y = gp.player.worldy + gp.player.hitBox.y;
		
		
		
		switch(entity.direction)
		{
		case "up":
			entity.hitBox.y -= entity.speed;
			break;
			
		case "down":
			entity.hitBox.y += entity.speed;
			break;
			
		case "left":
			entity.hitBox.x -= entity.speed;
			break;
			
		case "right":
			entity.hitBox.x += entity.speed;
			break;
		}
		
		if(entity.hitBox.intersects(gp.player.hitBox))
		{
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.hitBox.x = entity.hitBoxDefaultX;   //reset values
		entity.hitBox.y = entity.hitBoxDefaultY;
		
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		
		return contactPlayer;
	}
	
}















