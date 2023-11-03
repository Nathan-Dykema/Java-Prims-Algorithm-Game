package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity
{
	
	
	public OBJ_Boots(GamePanel gp)
	{
		 
		super(gp);
		
		name = "Boots";
		
		standing = setup("/objects/boots", gp.TileSize, gp.TileSize);
		
		
	}
}
