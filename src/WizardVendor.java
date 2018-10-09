import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WizardVendor extends Creature {
	
	private ArrayList<Item> inventory;
	private int width, height;
	private BufferedImage texture;

	public WizardVendor(Handler handler, float x, float y, int width, int height, ArrayList<Item> inventory) {
		super(handler, x, y, width, height);
		this.width = width * 4;
		this.height = height * 4;
		this.inventory = inventory;
		this.texture = Assets.mageM_idle[3];
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
