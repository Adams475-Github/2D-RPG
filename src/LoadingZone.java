import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class LoadingZone extends Entity {

	private Rectangle r;
	private Point enter;
	private Point exit;
	private World previousWorld;
	private World world;
	private BufferedImage enterTexture;
	private BufferedImage exitTexture;
	
	public LoadingZone(Handler handler, float x, float y, int width, int height, Point enter, Point exit, World world) {
		super(handler, x, y, width, height);
		
		
		
		r = new Rectangle( (int) x, (int) y, width, height );
		//removes hitbox
		bounds.width = 0;
		bounds.height = 0;
		this.enter = enter;
		this.exit = exit;
		this.world = world;
		previousWorld = handler.getWorld();
		
		
	}

	@Override
	public void tick() {
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(r)) {
			handler.setWorld(world);
			world.getEntityManager().addEntity(new LoadingZone(handler, exit.x, exit.y,  100, 100, new Point((int) x + width/2 - 24 , (int) y + height/2), new Point(-100, -100), previousWorld) {{
				if(exitTexture != null) {
					setEnterTexture(Assets.stairs);
				}
			}
			});
			
			handler.getWorld().getEntityManager().getPlayer().x = enter.x;
			handler.getWorld().getEntityManager().getPlayer().y = enter.y;			
			
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		if(enterTexture != null) {
			g.drawImage(enterTexture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, width, null);
		} else {
			g.fillRect((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);
		}
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

	public BufferedImage getEnterTexture() {
		return enterTexture;
	}

	public void setEnterTexture(BufferedImage enterTexture) {
		this.enterTexture = enterTexture;
	}

	public BufferedImage getExitTexture() {
		return exitTexture;
	}

	public void setExitTexture(BufferedImage exitTexture) {
		this.exitTexture = exitTexture;
	}

	
	
	

}
