import java.awt.Point;

public class EntityAdder {
	
	private Handler handler;
	private EntityManager entityManager;
	
	public EntityAdder(Handler handler, EntityManager entityManager) {
		this.handler = handler;
		this.entityManager = entityManager;
		addEntities();
	}
	
	public void addEntities() {
		
		entityManager.addEntity(new TallPlantPot(handler, 100, 250));
		entityManager.addEntity(new BushSmall(handler, 150, 100));
		//entityManager.addEntity(new FemaleRanger(handler, 250, 350, 27, 33));
		//entityManager.addEntity(new EnemyMageM(handler, 500, 350, 27, 33));
		entityManager.addEntity(new Chest(handler, 300, 100, 22, 17, Item.blueSword));
		//entityManager.addEntity(new LongWall(handler, 100, 500));
		entityManager.addEntity(new WizardVendor(handler, 100, 400, 20, 23, new Inventory(handler) {{
			
			addItem(Item.blueSword);
			addItem(Item.blueSword);
			addItem(Item.blueSword);
			
		}}));
		
		World houseWorld = new World(handler, "res/worlds/world2.txt");
		EntityManager houseEntityManager = new EntityManager(handler, handler.getWorld().getEntityManager().getPlayer());
		houseEntityManager.addEntity(new Chest(handler, 100, 100, 50, 50, Item.blueSword));
		houseEntityManager.addEntity(new TallPlantPot(handler, 400, 80));
		houseEntityManager.setPlayer(handler.getWorld().getEntityManager().getPlayer());
		houseWorld.setEntityManager(houseEntityManager);
		entityManager.addEntity(new LoadingZone(handler, 600, 100, 100, 100, new Point(470 + 25, 680), new Point(480, 894 - 100), houseWorld) {{
			setEnterTexture(Assets.stairs);
			setExitTexture(Assets.stairs);
		}
		});
		
		
	}

}
