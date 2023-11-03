package object;

import Entity.Projectile;
import main.GamePanel;

public class OBJ_Flames extends Projectile
{

	GamePanel gp;
	
	public OBJ_Flames(GamePanel gp) 
	{
		super(gp);
		this.gp = gp;
		
		name = "FlameShot";
		speed = 17;
		maxLife = 40;
		life = maxLife;
		alive = false;
		getImage();
		
		
	}

	public void getImage()
	{
		up1 = setup("/projectile/flame_attack_up_1", gp.TileSize, gp.TileSize);
		up2 = setup("/projectile/flame_attack_up_2", gp.TileSize, gp.TileSize);
		down1 = setup("/projectile/flame_attack_down_1", gp.TileSize, gp.TileSize);
		down2 = setup("/projectile/flame_attack_down_2", gp.TileSize, gp.TileSize);
		left1 = setup("/projectile/flame_attack_left_1", gp.TileSize, gp.TileSize);
		left2 = setup("/projectile/flame_attack_left_2", gp.TileSize, gp.TileSize);
		right1 = setup("/projectile/flame_attack_right_1", gp.TileSize, gp.TileSize);
		right2 = setup("/projectile/flame_attack_right_2", gp.TileSize, gp.TileSize);
	}
	
}
