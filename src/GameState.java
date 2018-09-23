import java.awt.Graphics;

public class GameState extends State {

	private Player player;
	private World world;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);

	}
	
	public void tick() {
		world.tick();

	}

	public void render(Graphics g) {
		world.render(g);
	}
	
	

}
