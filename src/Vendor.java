import java.util.ArrayList;

public abstract class Vendor extends Creature{
	

	protected Inventory inventory;
	
	public Vendor(Handler handler, float x, float y, int width, int height, Inventory inventory) {
		super(handler, x, y, width, height);
		this.inventory = inventory;
	}
	
	//GETTERS / SETTERS
	public Inventory getInv(){
		return inventory;
	}
	
	public void setInv(Inventory i) {
		inventory = i;
	}
	
}
