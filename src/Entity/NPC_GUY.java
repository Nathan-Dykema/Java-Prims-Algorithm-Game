package Entity;

import java.util.Random;

import main.GamePanel;

public class NPC_GUY extends Entity
{

	public NPC_GUY(GamePanel gp) 
	{
		super(gp);
		
		direction = "standing";
		speed = 1;
		
		
		getImage();
		setDialogue();
	}

	
	
	public void getImage()
	{
	 
			standing = setup("/npc/ai_standing", gp.TileSize+100, gp.TileSize+100);
			down1 = setup("/npc/ai_down_1", gp.TileSize+100, gp.TileSize+100);
			down2 = setup("/npc/ai_down_2", gp.TileSize+100, gp.TileSize+100);
			up1 = setup("/npc/ai_up_1", gp.TileSize+100, gp.TileSize+100);
			up2 = setup("/npc/ai_up_2", gp.TileSize+100, gp.TileSize+100);
			left1 = setup("/npc/ai_left_1", gp.TileSize+100, gp.TileSize+100);
			left2 = setup("/npc/ai_left_2", gp.TileSize+100, gp.TileSize+100);
			right1 = setup("/npc/ai_right_1", gp.TileSize+100, gp.TileSize+100);
			right2 = setup("/npc/ai_right_2", gp.TileSize+100, gp.TileSize+100);
			
	}
	
	public void setDialogue() //all dialogues for this npc
	{
		dialogues[0] = "Hey, you. You're finally awake.";
		dialogues[1] = "You were messing around with that suspicious amulet right?";
		dialogues[2] = "It must've teleported you into this weird place.";
		dialogues[3] = "Same as me.";
		dialogues[4] = "See those slimes Over There? This place is crawling with them.";
		dialogues[5] = "I've been trying to find a way out of here.";
		dialogues[6] = "It's been weeks, but all I've found is this cool dagger.";
		dialogues[7] = "Uhg... Man this sucks!";
		dialogues[8] = "I wouldn't be in this mess if I didn't wear that stupid thing!";
		dialogues[9] = "Could've just sold it off to the Empire and moved on with my life.";
		dialogues[10] = "Well, now that I think about it...";
		dialogues[11] = "You look a little young to even be here... ";
		dialogues[12] = "Eh whatever im not even gunna ask.";
		dialogues[13] = "Look I'm tired of dealing with these things.";
		dialogues[14] = "Here, how about you take this dagger I found and find the way out.";
		dialogues[15] = "(( DOUBLE TAP SPACEBAR TO SHOOT FIRE ))";
		dialogues[16] = "Good luck! Dont get Lost kid!";
		
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
			
			if(i <= 25)
			{
				
			}
			if(i > 25 && i <= 50)
			{
				direction = "down";
			}
			if(i > 50 && i <= 75)
			{
				direction = "left";
			}
			if(i > 75 && i <=  100)
			{
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
		
	}
	
	public void speak()
	{
		super.speak();
	}
}
