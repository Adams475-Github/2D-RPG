import java.awt.Graphics;
import java.io.Serializable;

public class IceBallClass extends Creature implements Serializable{
    //This whole thing is a TODO 
	private static final long serialVersionUID = 1971804220179129277L;
	private Animation right = new Animation(125, Assets.ice_shot_right), left = new Animation(125, Assets.ice_shot_left), 
			up = new Animation(125, Assets.ice_shot_up), down = new Animation(125, Assets.ice_shot_down);
	private Animation currentAnim;
	private long birthTime = System.currentTimeMillis();

	public IceBallClass(Handler handler, float x, float y, int width, int height, String direction) {
		super(handler, x, y, width, height);
		
		
		bounds.width = 0;
		bounds.height = 0;
		this.health = 999;
		
		
		if(direction == "up") {
			currentAnim = up;
			yMove = -5;
		} else if(direction == "right") {
			currentAnim = right;
			xMove = 5;
		} else if(direction == "down") {
			currentAnim = down;
			yMove = 5;
		} else {
			currentAnim = left;
			xMove = -5;
		}
		
	}

	@Override
	public void tick() {
		
		if(System.currentTimeMillis() - birthTime > 2500) {
			handler.getWorld().getEntityManager().getEntities().remove(this);
		}
		
		right.tick();
		left.tick();
		up.tick();
		down.tick();
		
		move();

	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getGameCamera().getxOffset()), 
				(int) (this.y - handler.getGameCamera().getyOffset() ), width, height, null);
	
	}

	@Override
	public void die() {
		
	}

	@Override
	public void interact() {
		
	}
	

}
