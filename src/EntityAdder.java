import java.awt.List;
import java.util.ArrayList;

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
		entityManager.addEntity(new FemaleRanger(handler, 250, 350, 27, 33));
		entityManager.addEntity(new EnemyMageM(handler, 500, 350, 27, 33));
		entityManager.addEntity(new Chest(handler, 300, 100, 22, 17, Item.blueSword));
		entityManager.addEntity(new LongWall(handler, 100, 500));
		entityManager.addEntity(new WizardVendor(handler, 90, 90, 90, 90, new ArrayList<Item>() {{
			add(Item.blueSword);
			add(Item.coinItem);
		}}));
		
	}

}
