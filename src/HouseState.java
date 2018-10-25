import java.awt.Graphics;

public class HouseState extends State {

	private World house;
	
	
	public HouseState(Handler handler, String path) {
		super(handler);

		System.out.println("initialized");
		house = new World(handler, "res/worlds/world2.txt");
		handler.setWorld(house);
		EntityManager houseEntityManager = new EntityManager(handler, new Player(handler, 0, 0));
		
		house.setEntityManager(houseEntityManager);
		houseEntityManager.addEntity(new Chest(handler, 100, 100, 50, 50, Item.blueSword));
		houseEntityManager.addEntity(new LoadingZone(handler, 100, 10, 5, 5, "/res/worlds/world1.txt"));
		
	}

	@Override
	public void tick() {
		house.tick();
		
		
	}

	@Override
	public void render(Graphics g) {
		house.render(g);
		
	}

}
