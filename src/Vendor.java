import java.util.ArrayList;

public abstract class Vendor extends Creature{
	

	protected ArrayList<Item> inventory;
	
	public Vendor(Handler handler, float x, float y, int width, int height, ArrayList<Item> inventory) {
		super(handler, x, y, width, height);
		this.inventory = inventory;
	}
	
	//GETTERS / SETTERS
	public ArrayList<Item> getInv(){
		return inventory;
	}
	
	public void setInv(ArrayList<Item> i) {
		inventory = i;
	}
	
}
