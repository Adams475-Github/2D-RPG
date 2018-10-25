import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class LoadingZone extends Entity {

	private Rectangle r;
	private String path;
	private Point enter;
	private Point exit;
	private boolean main;
	
	public LoadingZone(Handler handler, float x, float y, int width, int height, String path, Point enter, Point exit) {
		super(handler, x, y, width, height);
		
		
		
		r = new Rectangle( (int) x, (int) y, width, height );
		//removes hitbox
		bounds.width = 0;
		bounds.height = 0;
		this.path = path;
		this.enter = enter;
		this.exit = exit;
		
		if(path == "") {
			main = true;
		} else {
			main = false;
		}
		
	}

	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(r)) {
			
			if(main) {
				State.setState(handler.getGame().gameState);
				handler.setWorld(handler.getGame().getMainWorld());
				handler.getWorld().getEntityManager().getPlayer().x = exit.x;
				handler.getWorld().getEntityManager().getPlayer().y = exit.y;
			} else {
				State.setState(new HouseState(handler, path));
				handler.getWorld().getEntityManager().getPlayer().x = enter.x;
				handler.getWorld().getEntityManager().getPlayer().y = enter.y;
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
