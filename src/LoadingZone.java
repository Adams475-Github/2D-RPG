import java.awt.Graphics;
import java.awt.Rectangle;

public class LoadingZone extends Entity {

	private Rectangle r;
	private String path;
	
	public LoadingZone(Handler handler, float x, float y, int width, int height, String path) {
		super(handler, x, y, width, height);
		
		r = new Rectangle( (int) x, (int) y, width, height );
		//removes hitbox
		bounds.width = 0;
		bounds.height = 0;
		this.path = path;
		
	}

	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(r)) {
			
			if(path == "/res/worlds/world2.txt") {
				State.setState(new HouseState(handler, path));
			} else {
				State.setState(new GameState(handler));
				
			}
			
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.fillRect((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
