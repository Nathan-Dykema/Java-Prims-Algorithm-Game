package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity
{
   
	
	public OBJ_Heart(GamePanel gp)
	{
		super(gp);
		
		name = "Heart";
		image = setup("/objects/heart_full", gp.TileSize, gp.TileSize);
		image2 = setup("/objects/heart_half", gp.TileSize, gp.TileSize);
		image3 = setup("/objects/heart_blank", gp.TileSize, gp.TileSize);
		
		
	}
	
}
