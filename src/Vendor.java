import java.util.ArrayList;

public abstract class Vendor extends Creature{
	
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATURE_HEIGHT = 64;
	
	protected float speed;
	protected float xMove, yMove;
	protected ArrayList<Item> inventory;
	
	public Vendor(Handler handler, float x, float y, int width, int height, ArrayList<Item> inventory) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		this.inventory = inventory;
	}
	
	//GETTERS / SETTERS
	public ArrayList<Item> getInv(){
		return inventory;
	}
	
}
