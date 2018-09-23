import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {
	
	//Handler initialization
	private Handler handler;
	//Whether its active or not
	private boolean active = false;
	//Array lists for the 3 separate inventories
	private ArrayList<Item> inventoryAttack;
	private ArrayList<Item> invAdd;
	private ArrayList<Item> inventoryArmor;
	private ArrayList<Item> inventoryPotions;
	private ArrayList<Item> hotbar;
	//Used to condense code
	private ArrayList<Item> currentInv = inventoryAttack;
	//2d arrays for the item position storage
	private Item[][] weapon2d = new Item[5][3];
	private Item[][] armor2d = new Item[5][3];
	private Item[][] potions2d = new Item[5][3];
	//used to condense code
	private Item[][] current2d = weapon2d;
	//hard coded locations for inventory menu
	private int invX = (1024/2) - 215, invY = 768/2 - 360, invWidth = 119, invHeight = 171;
	private int invSlotDist = 17 * 4;
	//movement variables for item high lighter
	private int xMove = 0, yMove = 0;
	//legacy code(not really)
	private int display = 0;
	//Button bounding rectangles
	private Rectangle swordBounds = new Rectangle(invX + 409, invY + 152, 13*4, 18*4);
	private Rectangle shieldBounds = new Rectangle(invX + 409, invY + 224, 13*4, 18*4);
	private Rectangle potionsBounds = new Rectangle(invX + 409, invY + 296, 13*4, 18*4);
	private Rectangle questBounds = new Rectangle(invX + 409, invY + 368, 13*4, 18*4);
	private Rectangle use = new Rectangle(invX + 290, invY + 415, 20*4, 9*4);
	private Rectangle drop = new Rectangle(invX + 290, invY + 460, 20*4, 9*4);
	//Mouse x and y variable initialization so I don't have to type out a huge line to get mouseX and mouseY
	private int mouseX;
	private int mouseY;
	//Initializes Coins
	public int coins = 0;
	
	//constructor
	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryAttack = new ArrayList<Item>();
		inventoryArmor = new ArrayList<Item>();
		inventoryPotions = new ArrayList<Item>();
		invAdd = new ArrayList<Item>();
		hotbar = new ArrayList<Item>();
		//testing add item
		for (int i = 0; i <= 15; i++) {
			inventoryAttack.add(Item.nothing);
		}
		this.addItem(Item.armorStarter);
		//inventoryCoins.add(Item.coinItem);
		this.addItem(Item.swordStarter);
		this.addItem(Item.blueSword);
		
		//fills the hot-bar with blank items to be replaced.
		for (int i = 0; i <= 5; i++) {
			hotbar.add(Item.nothing);
		}
		
		
		
	
	}
	
	//This just update the display array so items are displayed/removed properly
	public void Update() {
		int i = 0;
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 3; y++) {
				if(i < currentInv.size()) {
					current2d[x][y] = currentInv.get(i);
					i++;
				} else {
					current2d[x][y] = Item.nothing;
					i++;
				}
				
			}
		}
	}
	
	//tick method
	public void tick() {

		
		
		//if user presses e either opens or closes menu
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E) && !handler.getWorld().getEntityManager().getPlayer().getEscapeMenu().isActive()) {
			active = !active;
		}
		
		//doesn't tick if it's not active
		if(!active) {
			return;
		}
		
		//updates correct arrays based on which one it's displaying
		if(display == 0) {
			//attackUpdate();
			currentInv = inventoryAttack;
			Update();
		} else if(display == 1) {
			//armorUpdate();
			Update();
		} else if(display == 2) {
			//potionsUpdate();
			Update();
		} else {
			
		}
		
		
		//sets mouse x and y to variables for ease of use
		mouseX = handler.getMouseManager().getMouseX();
		mouseY = handler.getMouseManager().getMouseY();
		
		//adds controls to move item high lighter 
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
			if(xMove < 4) {
				xMove += 1;
			} else {
				return;
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
			if(xMove > 0) {
				xMove -= 1;
			} else {
				return;
				
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
			if(yMove < 2) {
				yMove += 1;
			} else {
				return;
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
			if(yMove > 0) {
				yMove -= 1;
			} else {
				return;
			}
		}
		
		//sets up correct display, array list, and 2d arrays to be modified based on which display is selected
		if(active) {
			if (swordBounds.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				display = 0;
				current2d = weapon2d;
				currentInv = inventoryAttack;
				Update();
			}
			if (shieldBounds.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				display = 1;
				current2d = armor2d;
				currentInv = inventoryArmor;
				Update();
			}
			if (potionsBounds.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				display = 2;
				current2d = potions2d;
				currentInv = inventoryPotions;
				Update();
			}
			if (questBounds.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				display = 3;
			}
			
			//code to drop items(now it is just junk items but not gonna refactor lol)
			if (drop.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				int i = 0;
				for(int x = 0; x < 5; x++) {
					for(int y = 0; y < 3; y++) {
						
						if(x == xMove && y == yMove) {
							if(i < inventoryAttack.size()) {
								currentInv.set(i, Item.nothing);
							}
							current2d[x][y] = Item.nothing;
							
						}
						i++;
					}
				}
			}
			if (use.contains(mouseX, mouseY) && handler.getMouseManager().isLeftPressed()) {
				hotbarAdd();
				
			}
			
		}
		
		
	}
	
	//render method
	public void render(Graphics g) {
		
		//quits out of render if not active
		if(!active) {
			return;
		}
		
		//text color white
		g.setColor(Color.YELLOW);
		
		//draws inventory screen
		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth*4, invHeight*4, null);
		
		//draws item high lighter
		g.drawImage(Assets.itemHighlighter, 338 + invSlotDist * xMove, 195 + invSlotDist * yMove, 14*4, 14*4, null);
		
		//draws coin counter
		g.setFont(Assets.fontPlaceHolder);
		g.drawString(Integer.toString(coins), 650, 545);
		g.setColor(Color.WHITE);
		g.setFont(Assets.font28nBold);
		g.drawString("Gold", 589, 545);
		//loops through 2d array to draw the items
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 3; y++) {
				g.drawImage(current2d[x][y].texture, 342 + invSlotDist * x, 199 + invSlotDist * y, 48, 48, null);
			}
		}	
		
		for(int i = 0; i < hotbar.size(); i++) {
			g.drawImage(hotbar.get(i).texture, 341 + invSlotDist * i, 621, 48, 48, null);
		}

	}

	
	public void addItem(Item item) {

		int type = item.type;
		
		if(type == 0) {
			coins += 1;
		} else if(type == 1) {
			invAdd = inventoryAttack;
		} else if(type == 2) {
			invAdd = inventoryArmor;
		}
		
		//this code checks for empty slots to fill in blanks first, and then add on to end.
		for(int i = 0; i < invAdd.size(); i++) {
			if(invAdd.get(i).getId() == Item.nothing.getId() && invAdd.get(i).type != 0 && type != 0) {
				invAdd.set(i, item);
				return;
			} else if(invAdd.get(i) != Item.nothing && i == (invAdd.size() - 1) && type != 0){
				invAdd.add(item);
				return;
			} else {
				continue;
			}
			
		}
		
		
		
		
		//Previous code, may re-implement further down.. (for now it will just sit.)
		
//		for(Item i : inventoryAttack) {
//			if(i.getId() == item.getId()) {
//				i.setCount(i.getCount() + item.getCount());
//				return;
//		}

	}
	
	public void setNothing(int i, int x, int y) {
		currentInv.set(i, Item.nothing);
		current2d[x][y] = Item.nothing;
	}
	
	public void hotbarAdd() {
		int i = 0;
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 3; y++) {
				if(x == xMove && y == yMove) {
					//checks which item it is so it knows where to put in hot-bar. could condense but it would be a lot of work tbh, don't even know if it really would "condense".
					if(i < currentInv.size() && currentInv.get(i).type == 1) {
						if(hotbar.get(0) != Item.nothing) {
							this.addItem((hotbar.get(0)));
						}
						hotbar.set(0, currentInv.get(i));
						handler.getWorld().getEntityManager().getPlayer().setSword(currentInv.get(i));
						setNothing(i, x, y);
						
					} else if (currentInv.get(i).type == 3) {
						hotbar.set(2, currentInv.get(i));
						handler.getWorld().getEntityManager().getPlayer().setChestPlate(currentInv.get(i));
						setNothing(i, x, y);
					} else {
						break;
					}
				}
				//need this to loop through arrayList as well, honestly could do x * y but i think its a little more intuitive to just do i++ after
				i++;
			}
		}
	}
	
	//getters and setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public ArrayList<Item> getInventoryAttack() {
		return inventoryAttack;
	}
	public ArrayList<Item> getInventoryPotions() {
		return inventoryAttack;
	}
	public ArrayList<Item> getInventory() {
		return inventoryAttack;
	}
	public int getCoins() {
		return coins;
	}

}
