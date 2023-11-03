package main;

import java.util.Random;

import Entity.NPC_GUY;
import monster.MONSTER_RSlime;
import object.OBJ_Boots;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_WaterHeal;
import tile.TileManager;

public class AssetSetter 
{
	public int ROW = 0;
	public int COL = 0;
	public int keyCounter = 0;
	public int doorCounter = 0;
	public int slimeCounter = 0;
	public int waterCounter = 0;
	public int bootCounter = 0;

	GamePanel gp;
	
	public AssetSetter(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setObject()
	{
		gp.obj[0] = new OBJ_Key(gp);
		//gp.obj[0].worldx = TileManager.playerStartX + 4 * gp.TileSize;
		//gp.obj[0].worldy = TileManager.playerStartY + 4 * gp.TileSize;
		
		
		for(int row = TileManager.mapTileNumObjects.length / 2; row < TileManager.mapTileNumObjects.length - 5; row++)     
		{
			for(int col = TileManager.mapTileNumObjects[TileManager.mapTileNumObjects.length / 2].length / 2; col < TileManager.mapTileNumObjects[col].length - 5; col++)
			{
				if(TileManager.mapTileNumObjects[row][col] == 0)
				{
					if(keyCounter == 0)
					{
						TileManager.mapTileNumObjects[row][col] = 9;
						gp.obj[0].worldx = TileManager.playerStartX * gp.TileSize;
						gp.obj[0].worldy = TileManager.playerStartY * gp.TileSize;
						System.out.println("key spawned");
						keyCounter++;
					}
					
				}
			}
		}
		
		
		
		
		
		gp.obj[1] = new OBJ_Door(gp);
		
		//gp.obj[1].worldx = TileManager.playerStartX + 4 * gp.TileSize;
		//gp.obj[1].worldy = TileManager.playerStartY + 6 * gp.TileSize;
		
		
		if(doorCounter == 0)
		{
			Random random = new Random();
			int i = random.nextInt(2) + 1;
			if(i <= 1)
			{
				for(int row = TileManager.mapTileNumObjects.length - 5; row > 0; row--)     
				{
					for(int col = TileManager.mapTileNumObjects.length - 5; col > 0; col--)
					{
						if(TileManager.mapTileNumObjects[row][col] == 2 )
						{
							if(doorCounter == 0)
							{
								TileManager.mapTileNumObjects[row][col] = 9;
								gp.obj[1].worldx = row * gp.TileSize;
								gp.obj[1].worldy = col * gp.TileSize;
								System.out.println("door spawned");
							}
							doorCounter++;
						}
						
					}
				}
				
			}
			else
			{
				for(int row = TileManager.mapTileNumObjects.length - 5; row > 0; row--)     
				{
					for(int col = 0; col < TileManager.mapTileNumObjects[col].length - 5; col++)
					{
						if(TileManager.mapTileNumObjects[row][col] == 2 )
						{
							if(doorCounter == 0)
							{
								TileManager.mapTileNumObjects[row][col] = 9;
								gp.obj[1].worldx = row * gp.TileSize;
								gp.obj[1].worldy = col * gp.TileSize;
								System.out.println("door spawned");
							}
							doorCounter++;
						}
						
					}
				}
				
			}
		}
		
		
		
		
		
		gp.obj[2] = new OBJ_WaterHeal(gp);
		
		gp.obj[2].worldx = TileManager.playerStartX + 4 * gp.TileSize;
		gp.obj[2].worldy = TileManager.playerStartY + 8 * gp.TileSize;
		
		
		if(waterCounter == 0)
		{
			for(int row = 0; row < TileManager.mapTileNumObjects.length - 5; row++) 
			{
				for(int col = 0; col < TileManager.mapTileNumObjects[col].length - 5; col++)
				{
					if(TileManager.mapTileNumObjects[row][col] == 0 && TileManager.mapTileNumObjects[row+1][col] == 0 && TileManager.mapTileNumObjects[row+2][col] == 0 && TileManager.mapTileNumObjects[row+3][col] == 0 
						&& TileManager.mapTileNumObjects[row][col+1] == 0 && TileManager.mapTileNumObjects[row][col+2] == 0 && TileManager.mapTileNumObjects[row][col+3] == 0 && TileManager.mapTileNumObjects[row+3][col+3] == 0)
					{
						    if(waterCounter < 4)
							{
						    	TileManager.mapTileNumObjects[row][col] = 9;
						    	gp.obj[2].worldx = row * gp.TileSize;
								gp.obj[2].worldy = col * gp.TileSize;
						    	System.out.println("water pools spawning.....");
							}
						    waterCounter++;
					}
				}
			}
			
		}
		
		System.out.println("water pools spawned");
		
		
		
		
		
		
		
		gp.obj[3] = new OBJ_Boots(gp);
		
		//gp.obj[3].worldx = TileManager.playerStartX + 4 * gp.TileSize;
		//gp.obj[3].worldy = TileManager.playerStartY + 10 * gp.TileSize;
	
		
		if(bootCounter == 0)
		{
			for(int row = 0; row < TileManager.mapTileNumObjects.length - 5; row++) 
			{
				for(int col = 0; col < TileManager.mapTileNumObjects[col].length - 5; col++)
				{
					if(TileManager.mapTileNumObjects[row][col] == 0 && TileManager.mapTileNumObjects[row+1][col] == 0 && TileManager.mapTileNumObjects[row+2][col] == 0 && TileManager.mapTileNumObjects[row+3][col] == 0 
						&& TileManager.mapTileNumObjects[row][col+1] == 0 && TileManager.mapTileNumObjects[row][col+2] == 0 && TileManager.mapTileNumObjects[row][col+3] == 0 && TileManager.mapTileNumObjects[row+3][col+3] == 0)
					{
						    if(bootCounter < 4)
							{
						    	TileManager.mapTileNumObjects[row][col] = 9;
						    	gp.obj[2].worldx = row * gp.TileSize;
								gp.obj[2].worldy = col * gp.TileSize;
						    	System.out.println("boots spawning.....");
							}
						    bootCounter++;
					}
				}
			}
			
		}
		
		
		System.out.println("boots spawned");
		
		
		
		
		
		
				
	}
	
	public void setNPC()
	{
		gp.npc[0] = new NPC_GUY(gp);
		gp.npc[0].worldx = TileManager.playerStartX + 2 * gp.TileSize; //Spawn
		gp.npc[0].worldy = TileManager.playerStartY + 0 * gp.TileSize;
		
	}
	
	public void setMonster()
	{  
		
		
		//for no slimes near player
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize] = 9;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+1][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+1][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+1][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+1][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+1][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+2][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+2][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+2][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+2][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+2][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+3][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+3][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+3][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+3][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+3][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+4][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+4][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+4][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+4][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+4][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+5][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+5][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+5][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+5][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+5][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+6][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+6][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+6][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+6][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+6][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+7][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+7][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+7][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+7][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+7][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+8][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+8][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+8][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+8][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+8][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+9][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+9][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+9][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+9][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+9][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+10][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+10][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+10][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+10][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+10][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+11][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+11][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+11][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+11][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+11][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+12][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+12][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+12][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+12][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+12][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+13][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+13][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+13][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+13][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+13][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+14][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+14][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+14][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+14][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+14][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+15][TileManager.playerStartY/gp.TileSize+1] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+15][TileManager.playerStartY/gp.TileSize+2] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+15][TileManager.playerStartY/gp.TileSize+3] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+15][TileManager.playerStartY/gp.TileSize+4] = 8;
		TileManager.mapTileNumObjects[TileManager.playerStartX/gp.TileSize+15][TileManager.playerStartY/gp.TileSize+5] = 8;
		
		
		
		int i = 0;
		for(int row = 0; row < TileManager.mapTileNumObjects.length - 5; row++)     
		{
			for(int col = 0; col < TileManager.mapTileNumObjects[col].length - 5; col++)
			{
				if(row != TileManager.playerStartX && col != TileManager.playerStartY)
				{
					
				
				if(i < 460)
				{
					if(TileManager.mapTileNumObjects[row][col] == 0)
					{
						if(TileManager.mapTileNumObjects[row+1][col] == 2 && TileManager.mapTileNumObjects[row-1][col] == 2 || TileManager.mapTileNumObjects[row][col+1] == 2 && TileManager.mapTileNumObjects[row][col-1] == 2)
						{
							if(TileManager.mapTileNumObjects[row][col+1] == 0 && TileManager.mapTileNumObjects[row][col+2] == 0 && TileManager.mapTileNumObjects[row][col+3] == 0 && TileManager.mapTileNumObjects[row][col+4] == 0 && TileManager.mapTileNumObjects[row][col+5] == 0 
									&& TileManager.mapTileNumObjects[row][col-1] == 0 && TileManager.mapTileNumObjects[row][col-2] == 0 && TileManager.mapTileNumObjects[row][col-3] == 0 && TileManager.mapTileNumObjects[row][col-4] == 0 && TileManager.mapTileNumObjects[row][col-5] == 0)
									
								{
								    TileManager.mapTileNumObjects[row][col] = 9;
									gp.monster[i] = new MONSTER_RSlime(gp);
									gp.monster[i].worldx = row * gp.TileSize;    //Spawn
									gp.monster[i].worldy = col * gp.TileSize;
									i++;
									if(slimeCounter == 0)
									{
										System.out.println("slimes spawning...");
										slimeCounter++;
									}
								}
								
								if(TileManager.mapTileNumObjects[row+1][col] == 0 && TileManager.mapTileNumObjects[row+2][col] == 0 && TileManager.mapTileNumObjects[row+3][col] == 0 && TileManager.mapTileNumObjects[row+4][col] == 0 && TileManager.mapTileNumObjects[row+5][col] == 0 
										&& TileManager.mapTileNumObjects[row-1][col] == 0 && TileManager.mapTileNumObjects[row-2][col] == 0 && TileManager.mapTileNumObjects[row-3][col] == 0 && TileManager.mapTileNumObjects[row-4][col] == 0 && TileManager.mapTileNumObjects[row-5][col] == 0)
								{
									TileManager.mapTileNumObjects[row][col] = 9;
									gp.monster[i] = new MONSTER_RSlime(gp);
									gp.monster[i].worldx = row * gp.TileSize;    //Spawn
									gp.monster[i].worldy = col * gp.TileSize;
									i++;
									if(slimeCounter == 0)
									{
										System.out.println("slimes spawning...");
										slimeCounter++;
									}
								}
						}
						
						if(TileManager.mapTileNumObjects[row][col+1] == 0 && TileManager.mapTileNumObjects[row][col+2] == 0 && TileManager.mapTileNumObjects[row][col+3] == 0 
							&& TileManager.mapTileNumObjects[row+1][col] == 0 && TileManager.mapTileNumObjects[row+2][col] == 0 && TileManager.mapTileNumObjects[row+3][col] == 0 
							&& TileManager.mapTileNumObjects[row+1][col+1] == 0 && TileManager.mapTileNumObjects[row+2][col+1] == 0 && TileManager.mapTileNumObjects[row+3][col+1] == 0 
							&& TileManager.mapTileNumObjects[row+1][col+2] == 0 && TileManager.mapTileNumObjects[row+2][col+2] == 0 && TileManager.mapTileNumObjects[row+3][col+2] == 0
							&& TileManager.mapTileNumObjects[row+1][col+3] == 0 && TileManager.mapTileNumObjects[row+2][col+3] == 0 && TileManager.mapTileNumObjects[row+3][col+3] == 0
							&& TileManager.mapTileNumObjects[row+1][col+4] == 0 && TileManager.mapTileNumObjects[row+2][col+4] == 0 && TileManager.mapTileNumObjects[row+3][col+4] == 0)
						{
							
							TileManager.mapTileNumObjects[row][col] = 9;
							TileManager.mapTileNumObjects[row][col+1] = 8;
							TileManager.mapTileNumObjects[row][col+2] = 8;
							TileManager.mapTileNumObjects[row][col+3] = 8;
							TileManager.mapTileNumObjects[row][col+4] = 8;
							TileManager.mapTileNumObjects[row+1][col+1] = 8;
							TileManager.mapTileNumObjects[row+1][col+2] = 8;
							TileManager.mapTileNumObjects[row+1][col+3] = 8;
							TileManager.mapTileNumObjects[row+1][col+4] = 8;
							TileManager.mapTileNumObjects[row+2][col+1] = 8;
							TileManager.mapTileNumObjects[row+2][col+2] = 8;
							TileManager.mapTileNumObjects[row+2][col+3] = 8;
							TileManager.mapTileNumObjects[row+2][col+4] = 8;
							TileManager.mapTileNumObjects[row+3][col+1] = 8;
							TileManager.mapTileNumObjects[row+3][col+2] = 8;
							TileManager.mapTileNumObjects[row+3][col+3] = 8;
							TileManager.mapTileNumObjects[row+3][col+4] = 8;
						
							gp.monster[i] = new MONSTER_RSlime(gp);
							gp.monster[i].worldx = row * gp.TileSize;    //Spawn
							gp.monster[i].worldy = col * gp.TileSize;
							i++;
							if(slimeCounter == 0)
							{
								System.out.println("slimes spawning...");
								slimeCounter++;
							}
							
							
						}
						
						
					}
				
				
					
					
				 }
				
				}
				
			}
		}
		
	
		System.out.println("slimes spawned");
		System.out.println("");
		
		if(i >= 460)
		{
			for(int row = 0; row < TileManager.mapTileNumObjects.length; row++)     
			{
				for(int col = 0; col < TileManager.mapTileNumObjects[col].length - 1; col++)
				{
					
					if(TileManager.mapTileNumObjects[row][col] == 8)
					{
						TileManager.mapTileNumObjects[row][col] = 0;
					}
					
						
						
			    }
					
					
					
			}
		}
			
				
	}
		
		
		
		
		
		
	
	
	
	
}















