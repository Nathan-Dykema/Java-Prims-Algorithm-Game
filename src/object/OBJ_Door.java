package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity
{

	
	
	public OBJ_Door(GamePanel gp)
	{
        
		super(gp);
		
		name = "Door";
		
		standing = setup("/objects/door", gp.TileSize, gp.TileSize);
		
		collision = true;
		
	}
	
	
}
