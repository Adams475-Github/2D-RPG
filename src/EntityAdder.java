
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
		entityManager.addEntity(new FemaleRanger(handler, 300, 500, 27, 33));
	}

}
