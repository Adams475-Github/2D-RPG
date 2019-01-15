import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable{
	
	static Animation coinSpin1 = new Animation(125, Assets.coin_spin);
	private static final long serialVersionUID = -154986130137370073L;
	//Universal Item Types
	public static int nothingType = 0;
	public static int swordType = 1;
	public static int shield = 2;
	public static int helmet = 3;
	
	//Item Instantiation
	
	//syntax for args (texture, name, id, width, height, type, armorvalue, attackvalue, goldvalue, decription)
	//Swords are type 1, armor is type 3, nothing type 0, shield 4
	public static Item[] items = new Item[256];
	public static Item coinItem = new Item(Assets.coin, "coin", 2, 10, 10, 0, 0, 0, 1, "A common coin.", true, coinSpin1);
	public static Item nothing = new Item(Assets.nothing, "nothing", 3, 10, 10, 4, 0, 0, 0, "", false, null);
	public static Item swordStarter = new Item(Assets.swordStarter, "Basic Sword", 5, 14, 22, 1, 0, 5, 5, "A rusty old thing, its seen better days. This is alex's sword. Wooohoo", false, null);
	public static Item blueSword = new Item(Assets.swordBlue, "Blue Sword", 6, 14, 22, 1, 0, 100, 5, "A blue sword. It seems to shimmer when you look at it.", false, null);
	public static Item armorStarter = new Item(Assets.chestPlate, "chestPlate", 4, 14, 22, 3, 10, 0, 5, "Standard leather armor. Comfortable but not invincible.", false, null);
	public static Item shieldStarter = new Item(Assets.shieldStarter, "Basic Shield", 7, 14, 22, 4, 5, 0, 1, "A basic shield. It's been through some things.", false, null);
	
	//Class
	public static final int ITEMWIDTH = 2, ITEMHEIGHT = 2;

	protected transient Handler handler;
	protected transient BufferedImage texture;
	protected String name;
	protected final int id;
	protected int wid, hei;
	protected Rectangle bounds;
	protected transient Animation anim;
	protected int type;
	protected int armorValue;
	protected int attackValue;
	protected int goldValue;
	protected String description;
	protected int x, y, count;
	protected boolean pickedUp = false;
	protected boolean hasAnimation;
	
	public Item(BufferedImage texture, String name, int id, 
			int wid, int hei, int type, int armorValue, int attackValue, int goldValue, String description, boolean hasAnimation, Animation anim) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		this.wid = wid;
		this.hei = hei;
		this.type = type;
		this.armorValue = armorValue;
		this.attackValue = attackValue;
		this.goldValue = goldValue;
		this.description = description;
		this.hasAnimation = hasAnimation;
		this.anim = anim;
		
		bounds = new Rectangle(x, y, wid * ITEMWIDTH, hei * ITEMHEIGHT);		
		
		items[id] = this;
	}
	
	public void tick() {
		anim.tick();
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	
	public void render(Graphics g) {
		if(handler == null) {
			return;
		}
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y) {
		if(hasAnimation) {
			g.drawImage(anim.getCurrentFrame(), x, y, wid*ITEMWIDTH, hei*ITEMHEIGHT, null);
			
		} else {
			g.drawImage(texture, x, y, wid*ITEMWIDTH, hei*ITEMHEIGHT, null);
		}
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}
	
	
	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id, wid, hei, type, armorValue, attackValue, goldValue, description, hasAnimation, anim);
		i.setPosition(x, y);
		return i;
	}
	
	public Item createNew(int count) {
		Item i = new Item(texture, name, id, wid, hei, type, armorValue, attackValue, goldValue, description, hasAnimation, anim);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}
	
}
