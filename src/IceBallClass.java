import java.awt.Graphics;
import java.io.Serializable;

public class IceBallClass extends Creature implements Serializable{

	private static final long serialVersionUID = 1971804220179129277L;
	private Animation right, left, up, down;
	private Animation currentAnim;
	private long birthTime = System.currentTimeMillis();

	public IceBallClass(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.health = 999;
		
		//right = new Animation(100, Assets.fb_right);
		//left = new Animation(100, Assets.fb_left);
		up = new Animation(100, Assets.iceShot);
		//down = new Animation(100, Assets.fb_down);
		
		//ignore(?)
		currentAnim = up;
		yMove = -5;
	}

	@Override
	public void tick() {
		
		if(System.currentTimeMillis() - birthTime > 2500) {
			handler.getWorld().getEntityManager().getEntities().remove(this);
		}
		
		//right.tick();
		//left.tick();
		up.tick();
		//down.tick();
		
		if(xMove > 0) {
			this.width = 52;
			this.height = 18;
			currentAnim = right;
			
		} else if(xMove < 0) {
			this.width = 52;
			this.height = 18;
			currentAnim = left;
			
		} else if(yMove < 0) {
			this.width = 16;
			this.height = 32;
			currentAnim = up;
			
		} else if(yMove > 0) {
			this.width = 16;
			this.height = 32;
			currentAnim = down;
			
		} 
		
		
		
		
		move();
		
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(up.getCurrentFrame(), (int) (this.x - handler.getGameCamera().getxOffset()), 
				(int) (this.y - handler.getGameCamera().getyOffset() ), width, height, null);
	
	}

	@Override
	public void die() {
		
	}

	@Override
	public void interact() {
		
	}
	

}
