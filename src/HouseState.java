import java.awt.Graphics;
import java.awt.Point;
 
public class HouseState extends State {	
	
	private World world;
	
	public HouseState(Handler handler, String path) {
		super(handler);
		
		
	}
	
	public void init() {
		EntityManager houseEntityManager = new EntityManager(handler, handler.getWorld().getEntityManager().getPlayer());
		world = new World(handler, "res/worlds/world2.txt");
		handler.setWorld(world);
		world.setEntityManager(houseEntityManager);
		handler.getGame().setHouseWorld(world);
		
		houseEntityManager.addEntity(new Chest(handler, 100, 100, 50, 50, Item.blueSword));
		houseEntityManager.addEntity(new LoadingZone(handler, 100, 10, 5, 5, "", new Point(0, 0), new Point(0, 0), handler.getGame().gameState));
	}

	@Override
	public void tick() {
		world.tick();
		
		
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		
	}

}
