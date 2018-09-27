import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Chest extends Entity {

	private boolean opened = false;
	private boolean display = false;
	Item item;
	
	public Chest(Handler handler, float x, float y, int width, int height, Item item) {
		super(handler, x, y, width, height);
		bounds.height = height * 4 - 20;
		bounds.width = width * 4;
		this.item = item;
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			display = false;
		}
		
	}

	@Override
	public void render(Graphics g) {
		if(!opened) {
			g.drawImage(Assets.chestClosed, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width * 4, height * 4, null);
			
		} else {
			g.drawImage(Assets.chestOpen, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width * 4, height * 4, null);
			
		}
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		if(!opened) {
			opened = !opened;

		} else {
			return;
		}
		
		handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this.item);
		
	}

}
