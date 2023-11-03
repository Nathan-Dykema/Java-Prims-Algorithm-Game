package monster;

import java.util.Random;

import Entity.Entity;
import main.GamePanel;

public class MONSTER_RSlime extends Entity
{
	GamePanel gp;

	public MONSTER_RSlime(GamePanel gp) 
	{
		super(gp);
		
		this.gp = gp;
		
		type = 2;
		name = "Red Slime";
		speed = 2;
		maxLife = 2;
		life = maxLife;
		
		hitBox.x = 3;
		hitBox.y = 18;
		hitBox.width = 42;
		hitBox.height = 30;
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
		
	}
	
	public void getImage()
	{
		standing = setup("/monster/red_slime_moving_1", gp.TileSize, gp.TileSize);
		up1 = setup("/monster/red_slime_moving_1", gp.TileSize, gp.TileSize);
		up2 = setup("/monster/red_slime_moving_2", gp.TileSize, gp.TileSize);
		down1 = setup("/monster/red_slime_moving_1", gp.TileSize, gp.TileSize);
		down2 = setup("/monster/red_slime_moving_2", gp.TileSize, gp.TileSize);
		left1 = setup("/monster/red_slime_moving_1", gp.TileSize, gp.TileSize);
		left2 = setup("/monster/red_slime_moving_2", gp.TileSize, gp.TileSize);
		right1 = setup("/monster/red_slime_moving_1", gp.TileSize, gp.TileSize);
		right2 = setup("/monster/red_slime_moving_2", gp.TileSize, gp.TileSize);
		
	}
	
	public void setAction()
	{
         actionLockCounter++;
		
		if(actionLockCounter == 60)
		{
			direction = "standing";
		}
		if(actionLockCounter == 120)
		{
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			int j = random.nextInt(2) + 1;
			
			if(j < 1)
			{
				if(i <= 20)
				{
					
				}
				if(i > 20 && i <= 40)
				{
					direction = "down";
				}
				if(i > 40 && i <= 60)
				{
					direction = "left";
				}
				if(i > 60 && i <=  80)
				{
					direction = "right";
				}
				if(i > 80 && i <=  100)
				{
					direction = "up";
				}
			}
			else
			{
				if(i <= 15)
				{
					
				}
				if(i > 15 && i <= 30)
				{
					direction = "down";
				}
				if(i > 30 && i <= 65)
				{
					direction = "left";
				}
				if(i > 65 && i <=  80)
				{
					direction = "right";
				}
				if(i > 80 && i <=  100)
				{
					direction = "up";
				}
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void damageReaction()
	{
		actionLockCounter = 0;
		if(gp.player.direction == "standing")
		{
			direction = gp.player.lastDirection;
		}
		if(gp.player.direction == "left")
		{
			direction = "right";
		}
		if(gp.player.direction == "right")
		{
			direction = "left";
		}
		if(gp.player.direction == "up")
		{
			direction = "down";
		}
		if(gp.player.direction == "down")
		{
			direction = "up";
		}
		
	}
}
