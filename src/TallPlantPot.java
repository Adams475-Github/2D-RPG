import java.awt.Graphics;

public class TallPlantPot extends StaticEntity{

	public TallPlantPot(Handler handler, float x, float y) {
		super(handler, x, y, 15 * 4, 30 * 4);
		
		bounds.y = 85;
		bounds.height = 20;
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die() {

	}
	
	public void interact() {
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().coins >= 10) {
			handler.getWorld().getEntityManager().getPlayer().getInventory().coins -= 10;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(Item.blueSword);
		} else {
			System.out.println("Hahaha nice try poor boy!");
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}

}
