package Entity;

import main.GamePanel;

public class Projectile extends Entity
{

	public int flameCounter = 0;
	
	public Projectile(GamePanel gp) 
	{
		super(gp);
	}
	
	public void set(int worldx, int worldy, String direction, boolean alive)
	{
		this.worldx = worldx;
		this.worldy = worldy;
		this.direction = direction;
		this.alive = alive;
		this.life = this.maxLife;
		
		
	}
	
	public void update()
	{
		int monsterIndex = gp.collisCheck.checkEntity(this, gp.monster);
		if(monsterIndex != 999)
		{
			gp.player.damageMonster(monsterIndex);
			alive = false;
		}
		
		switch(direction)
		{
		case "standing":
			if(gp.player.lastDirection == "up")
			{
				worldy -= speed;
			}
			if(gp.player.lastDirection == "down")
			{
				worldy += speed;
			}
			if(gp.player.lastDirection == "left")
			{
				worldx -= speed;
			}
			if(gp.player.lastDirection == "right")
			{
				worldx += speed;
			}
			if(gp.player.lastDirection == "")
			{
				gp.player.lastDirection = "down";
				worldy += speed;
			}
			
			break;
			
		case "up":
			worldy -= speed;
			break;
			
		case "down":
			worldy += speed;
			break;
			
		case "left":
			worldx -= speed;
			break;
			
		case "right":
			worldx += speed;
			break;
	
		}
		
		
		
		
		
		life--;
		if(life <= 0)
		{
			alive = false;
		}
		
		if(alive == false)
		{
			flameCounter = 0;
		}
		if(alive == true && flameCounter == 0)
		{
			gp.playSE(3);
			flameCounter++;
		}
		
		spriteCounter++;
		if(spriteCounter > 10)
		{
			if(spriteNum == 1)
			{
				spriteNum = 2;
			}
			else if(spriteNum == 2)
			{
				spriteNum = 1;
			}
			spriteCounter = 0;
			
			
		}
		
	}
			
			
			
			
}
