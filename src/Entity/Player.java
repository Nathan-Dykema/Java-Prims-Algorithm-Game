package Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Flames;
import tile.TileManager;

public class Player extends Entity
{
	
	
	KeyHandler keyH;
	
	int standCount = 0;
	
	public final int screenx;
	public final int screeny;
	
	public String lastDirection = "";
	
	
	public int hasKey = 0; //how many keys are owned
	
	
	ArrayList<String> previousDirections = new ArrayList<String>();
	
	
	public Player(GamePanel gp, KeyHandler keyH)
	{
		super (gp);
		
		
		this.keyH = keyH;
		
		screenx = gp.screenWidth / 2 - (gp.TileSize/2);
		screeny = gp.screenHeight / 2 - (gp.TileSize/2);
		
		hitBox = new Rectangle();
		hitBox.x = 18;
		hitBox.y = 34;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		hitBox.width = 14;
		hitBox.height = 12;
		
		attackHitBox.width = gp.TileSize;
		attackHitBox.height = gp.TileSize;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackimage();
	}
	
	public void setDefaultValues()
	{
		
		worldx = TileManager.playerStartX;
		worldy = TileManager.playerStartY;
		
		speed = 7; //7 
		
		direction = "standing";
		
		//Player Status
		maxLife = 6;
		life = maxLife;
		
		projectile = new OBJ_Flames(gp);
	}
	
	public void getPlayerImage()
	{
		
		standing = setup("/player/player_standing_0", gp.TileSize, gp.TileSize);
		down1 = setup("/player/player_down_1", gp.TileSize, gp.TileSize);
		down2 = setup("/player/player_down_2", gp.TileSize, gp.TileSize);
		up1 = setup("/player/player_up_1", gp.TileSize, gp.TileSize);
		up2 = setup("/player/player_up_2", gp.TileSize, gp.TileSize);
		left1 = setup("/player/player_left_1", gp.TileSize, gp.TileSize);
		left2 = setup("/player/player_left_2", gp.TileSize, gp.TileSize);
		right1 = setup("/player/player_right_1", gp.TileSize, gp.TileSize);
		right2 = setup("/player/player_right_2", gp.TileSize, gp.TileSize);
		
		
	}
	
	public void getPlayerAttackimage()
	{
		attackUpS = setup("/player/player_attack_up_standing", gp.TileSize, gp.TileSize);
		attackUpM = setup("/player/player_attack_up_moving", gp.TileSize, gp.TileSize);
		attackDownS = setup("/player/player_attack_down_standing", gp.TileSize, gp.TileSize);
		attackDownM = setup("/player/player_attack_down_moving", gp.TileSize, gp.TileSize);
		attackLeftS = setup("/player/player_attack_left_standing", gp.TileSize, gp.TileSize);
		attackLeftM = setup("/player/player_attack_left_moving", gp.TileSize, gp.TileSize);
		attackRightS = setup("/player/player_attack_right_standing", gp.TileSize, gp.TileSize);
		attackRightM = setup("/player/player_attack_right_moving", gp.TileSize, gp.TileSize);
	}
	
	public void update()
	{
		
		
		if(attacking == true)
		{
			attacking();
		}
		else if(keyH.pressedUP == true 
			|| keyH.pressedDOWN == true 
			|| keyH.pressedLEFT == true 
			|| keyH.pressedRIGHT == true
			|| keyH.pressedSpace == true)
		{
			if(keyH.pressedUP == true)
			{
				direction = "up";
				
			}
			else if(keyH.pressedDOWN == true)
			{
				direction = "down";
			}
			else if(keyH.pressedLEFT == true)
			{
				direction = "left";
			}
			else if(keyH.pressedRIGHT == true)
			{
				direction = "right";
			}
			
			
			if(previousDirections.isEmpty())
			{
				previousDirections.add("down");
			}
			
			if(direction != previousDirections.get(previousDirections.size() - 1));
			{

				if(direction != "standing")
				{
					previousDirections.add(direction);
					lastDirection = direction;
				}
				
			}
			
		
			
			
			
			// CHECK FOR TILE COLLISION
			collisionOn = false;
			gp.collisCheck.checkTile(this);
			
			//Check Object collision
			int objIndex = gp.collisCheck.checkObject(this, true);
			pickUpObject(objIndex);
			
			//Check NPC collision
			int npcIndex = gp.collisCheck.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//Check Monster Collision
			int monsterIndex = gp.collisCheck.checkEntity(this, gp.monster);
			touchMonster(monsterIndex);
			
			gp.eHandler.checkEvent();
			
			
			
			// IF COLLISION IS FALSE THEN PLAYER WONT MOVE
			if(collisionOn == false && keyH.pressedSpace == false)
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
			gp.keyH.pressedSpace = false;
			
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
			
		}
		 else
			{
				standCount++;
				
				if(standCount == 5)
				{
					
					spriteNum = 1;
					standCount = 0;
					direction = "standing";
				}
				
			} 
		
		//Set Coordinates and Direction of Fire
		if(gp.keyH.pressedSpace == true && projectile.alive == false && shotAvailableCounter == 30)
		{
			projectile.set( worldx,  worldy, direction, true);
			
			//Add it to the list
			gp.projectileList.add(projectile);
			
			shotAvailableCounter = 0;
			
			//gp.playSE(10);
		}
		
		
		    
		   //invincible time
		    if(invincible == true)
		    {
		    	invincibleCounter++;
		    	if(invincibleCounter > 60)//one second
		    	{
		    		invincible = false;
		    		invincibleCounter = 0;
		    	}
		    }
		    
		    if(shotAvailableCounter < 30)
		    {
		    	shotAvailableCounter++;
		    }
		
	}
	
	public void attacking()
	{
		spriteCounter++;
		
		
		if(spriteCounter <= 18)
		{
			spriteNum = 2;
			
			//current position
			int currentWorldX = worldx;
			int currentWorldY = worldy;
			int hitBoxWidth = hitBox.width;
			int hitBoxHeight = hitBox.height;
			
			//change players position to match attack for correct hitbox position
			switch(direction)
			{
			case "standing":
				if(lastDirection == "up")
				{
					worldy -= attackHitBox.height;
				}
				if(lastDirection == "down")
				{
					worldy += attackHitBox.height;
				}
				if(lastDirection == "left")
				{
					worldx -= attackHitBox.width;
				}
				if(lastDirection == "right")
				{
					worldx += attackHitBox.width;
				}
				if(lastDirection == "")
				{
					worldy += attackHitBox.height;
				}
				
				break;
				
			case "up":
				worldy -= attackHitBox.height;
				break;
				
			case "down":
				worldy += attackHitBox.height;
				break;
				
			case "left":
				worldx -= attackHitBox.width;
				break;
				
			case "right":
				worldx += attackHitBox.width;
				break;
			}
			
			//change hitbox
			hitBox.width = attackHitBox.width;
			hitBox.height = attackHitBox.height;
			
			//check collision
			int monsterIndex = gp.collisCheck.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			//change back to original position afterward
			worldx = currentWorldX;
			worldy = currentWorldY;
			hitBox.width = hitBoxWidth;
			hitBox.height = hitBoxHeight;
			
			
		}
		if(spriteCounter > 18)
		{
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i)
	{
		if(i != 999)
		{
			String ObjectName = gp.obj[i].name;
			
			
			switch(ObjectName)
			{
			  case "Key":
				  gp.playSE(1);
				  hasKey++;
				  gp.obj[i] = null;
				  gp.ui.showMessage("You Picked Up A Key!");
				 break;
				 
			  case "Door":
				  if(hasKey > 0)
				  {
					  gp.obj[i] = null;
				  }
				  else if(hasKey == 0)
				  {
					  gp.playSE(5);
					  gp.ui.showMessage("You Need To Find A Key!");
				  }
				  
				  gp.ui.gameFinished = true;
				  
					 break;
					 
			  case "Water":
				  gp.playSE(1);
				  life = maxLife;
				  gp.ui.showMessage("You Feel Better Now");
					 break;
					 
			  case "Boots":
				  gp.playSE(1);
				  speed += 1;
				  gp.obj[i] = null;
				  gp.ui.showMessage("You Feel Faster!");
					 break;
					 
			 
			}
			
		}
	}
	
	public void interactNPC(int i)
	{
		if(gp.keyH.pressedSpace == true)
		{
			if(i != 999) //if player touches it
			{
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
				gp.playSE(6);
			}
			else
			{
				
				attacking = true;
			}
		}
		
		
	}
	
	public void touchMonster(int i)
	{
		if(i != 999)
		{
			if(invincible == false && gp.monster[i].dying == false)
			{
				gp.playSE(4); //when touching monster
				life -= 1;
				invincible = true;
			}
			if(life <= 0)
			{
				gp.player.alive = false;
			}
			
		}
		
	}
	
	public void damageMonster(int i)
	{
		if(i != 999)
		{
			if(gp.monster[i].invincible == false)
			{
				gp.playSE(7); //when hitting monster
				gp.monster[i].life -= 1;
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0)
				{
					gp.monster[i].dying = true;
				}
			}
		}
	}
	
	public void draw(Graphics2D g2)
	{
		BufferedImage image = null;
		int tempScreenX = screenx;
		int tempScreenY = screeny;
		
		switch(direction)
		{
		case "standing":
			if(attacking == false)
			{
				image = standing;
			}
			else
			{
				if(lastDirection == "up")
				{
					image = attackUpS;
				}
				if(lastDirection == "down")
				{
					image = attackDownS;
				}
				if(lastDirection == "left")
				{
					image = attackLeftS;
				}
				if(lastDirection == "right")
				{
					image = attackRightS;
				}
				if(lastDirection == "")
				{
					image = attackDownS;
					lastDirection = "down";
				}
			}
			
			
			break;
		case "up":
			if(attacking == false)
			{
				
				if(spriteNum == 1)
				{
					image = up1;
				}
				if(spriteNum == 2)
				{
					image = up2;
				}
			}
			if(attacking == true)
			{
				image = attackUpM;
			}
			
			break;
		case "down":
			if(attacking == false)
			{
				if(spriteNum == 1)
				{
					image = down1;
				}
				if(spriteNum == 2)
				{
					image = down2;
				}
			}
			if(attacking == true)
			{
				image = attackDownM;
			}
			
			
			break;
		case "left":
			
			if(attacking == false)
			{
				
				if(spriteNum == 1)
				{
					image = left1;
				}
				if(spriteNum == 2)
				{
					image = left2;
				}
			}
			if(attacking == true)
			{
				image = attackLeftM;
			}
			
			
			break;
		case "right":
			if(attacking == false)
			{
				if(spriteNum == 1)
				{
					image = right1;
				}
				if(spriteNum == 2)
				{
					image = right2;
				}
			}
			if(attacking == true)
			{
				image = attackRightM;
			}
			
			
			break;
		}
		
		if(invincible == true)
		{
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)); //make player transparent a bit when invincible
		}
		
		if(gp.player.alive == false)
		{
			gp.stopMusic();
			dyingAnimation(g2);
			
			
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100F));
			g2.setColor(Color.RED);
			
			String text;
			int textLength;
			int x;
			int y;
			
			
			gp.playSE(2); 
			text = "YOU DIED";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.TileSize * 3);
			g2.drawString(text, x, y);
			
			
			
			if(deathEndCounter == 1)
			{
				GamePanel.gameThread = null;
			}
			
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		//resets alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//g2.setColor(Color.RED);
		//g2.drawRect(screenx + hitBox.x, screeny + hitBox.y, hitBox.width, hitBox.height );   // show player hitbox
		
		
			
	}
	
	
	
}
