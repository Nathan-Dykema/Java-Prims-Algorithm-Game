package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity
{

	
	
	public OBJ_Key(GamePanel gp)
	{
		
		super(gp);
		
		name = "Key";
		standing = setup("/objects/key", gp.TileSize, gp.TileSize);
		
		
		
	}
	
}
