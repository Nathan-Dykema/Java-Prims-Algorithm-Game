package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_WaterHeal extends Entity
{
	

	public OBJ_WaterHeal(GamePanel gp)
	{
		super(gp);

	    name = "Water";
		
	    standing = setup("/objects/waterheal", gp.TileSize, gp.TileSize);
	    
	    collision = false;
		
	}
	
	
}
