package Entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity 
{
	
	GamePanel gp;
	
	public int worldx, worldy;
	public int speed;
	
	public BufferedImage standing, down1, down2, up1, up2, left1, left2, right1, right2;
	public BufferedImage attackUpS, attackUpM, attackDownS, attackDownM, attackLeftS, attackLeftM,attackRightS, attackRightM;
	
	public String direction = "standing";
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	
	public int deathEndCounter = 0;
	public int shotAvailableCounter = 0;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public Rectangle attackHitBox = new Rectangle(0, 0, 0, 0); //attack hitbox
	
	public int hitBoxDefaultX, hitBoxDefaultY;
	public boolean collisionOn = false;
	
	public int actionLockCounter = 0;
	public int dialogueIndex = 0;
	
	String dialogues[] = new String[20];
	
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type; //0 = player, 1 = npc, 2 = monster
	public int dyingCounter = 0;
	
	//Character Status
	public int maxLife;
	public int life;
	
	public Projectile projectile;
	
	
	
	public Entity(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void speak()
	{
		if(dialogues[dialogueIndex] == null)
		{
			dialogueIndex = dialogueIndex - 1;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		
		switch(gp.player.direction)
		{
		case "up":
			direction = "down";
			break;
			
		case "down":
			direction = "up";
			break;
			
		case "left":
			direction = "standing";
			break;
			
		case "right":
			direction = "standing";
			break;
		}
		
	}
	
	public void setAction()
	{
		
	}
	public void damageReaction()
	{
		
	}
	
	public void update()
	{
		setAction();
		
		collisionOn = false;
		gp.collisCheck.checkTile(this);
		gp.collisCheck.checkObject(this, false);
		gp.collisCheck.checkEntity(this, gp.npc);
		gp.collisCheck.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.collisCheck.checkPlayer(this);
		
		if(this.type == 2 && contactPlayer == true)
		{
			if(gp.player.invincible == false)
			{
				gp.playSE(4); //when monster touches player
				gp.player.life -= 1;
				gp.player.invincible = true;
			}
			
		}
		
		if(collisionOn == false)
		{
			switch(direction)
			{
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
		}
		
		spriteCounter++;
		
		if(spriteCounter > 12)
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
		
		 if(invincible == true)
		    {
		    	invincibleCounter++;
		    	if(invincibleCounter > 40)
		    	{
		    		invincible = false;
		    		invincibleCounter = 0;
		    	}
		    }
	}

	
	public void draw(Graphics2D g2)
	{
		int screenx = worldx - gp.player.worldx + gp.player.screenx; // place position of tile in correct spot
		int screeny = worldy - gp.player.worldy + gp.player.screeny;
		
		
		if(worldx + gp.TileSize > gp.player.worldx - gp.player.screenx &&  // better performance. doesn't draw object tiles if not on screen.
		   worldx - gp.TileSize < gp.player.worldx + gp.player.screenx &&
		   worldy + gp.TileSize > gp.player.worldy - gp.player.screeny &&
		   worldy - gp.TileSize < gp.player.worldy + gp.player.screeny)
		{
			BufferedImage image = null;
			
			switch(direction)
			{
			case "standing":
				
				image = standing;
				
				break;
			case "up":
				if(spriteNum == 1)
				{
					image = up1;
				}
				if(spriteNum == 2)
				{
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1)
				{
					image = down1;
				}
				if(spriteNum == 2)
				{
					image = down2;
				}
				
				break;
			case "left":
				if(spriteNum == 1)
				{
					image = left1;
				}
				if(spriteNum == 2)
				{
					image = left2;
				}
				
				break;
			case "right":
				if(spriteNum == 1)
				{
					image = right1;
				}
				if(spriteNum == 2)
				{
					image = right2;
				}
				
				break;
			}
			
			if(invincible == true)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)); //make player transparent a bit when invincible
				
			}
			if(dying == true)
			{
				dyingAnimation(g2);
			}
			g2.drawImage(image, screenx, screeny, gp.TileSize, gp.TileSize, null);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // reset
		}
	}
	
	public void dyingAnimation(Graphics2D g2)
	{
		dyingCounter++;
		if(dyingCounter <= 5)
		{
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > 5 && dyingCounter >= 10)
		{
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > 10 && dyingCounter >= 15)
		{
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > 15 && dyingCounter >= 20)
		{
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > 20 && dyingCounter >= 25)
		{
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > 25 && dyingCounter >= 30)
		{
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > 30 && dyingCounter >= 35)
		{
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > 35 && dyingCounter >= 40)
		{
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > 40)
		{
			
			alive = false;
			deathEndCounter = 1;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue)
	{
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath, int width, int height)
	{
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try 
		{
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
					
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return image;
	}
	
}
