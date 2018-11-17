import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Chest extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4095869842042066014L;
	private boolean opened = false;
	private boolean display = false;
	Item item;
	
	public Chest(Handler handler, float x, float y, int width, int height, Item item) {
		super(handler, x, y, width, height);
		bounds.height = height * 4 - 20;
		bounds.width = width * 4;
		this.item = item;
		health = 1;
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
			g.drawRect((int)x, (int)y, width * 4, height * 4);
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
	
	public Point center() {
		
		Point center = new Point( (int) (x - handler.getGameCamera().getxOffset() + (width * 4 / 2)) , (int) (y - handler.getGameCamera().getyOffset() + (height * 4 / 2)) );		
		
		return center;
	}



}
