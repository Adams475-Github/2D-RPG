import java.awt.Graphics;

public class SpellFireBall extends Creature {
	
	private Animation right, left, up, down;
	private Animation currentAnim = down;
	private int direction;

	public SpellFireBall(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.health = 999;
		
		right = new Animation(100, Assets.fb_right);
		left = new Animation(100, Assets.fb_left);
		up = new Animation(100, Assets.fb_up);
		down = new Animation(100, Assets.fb_down);
		
	}

	@Override
	public void tick() {
		
		right.tick();
		left.tick();
		up.tick();
		down.tick();
		
		if(xMove > 0) {
			currentAnim = right;
			direction = 0;
			
		} else if(xMove < 0) {
			currentAnim = left;
			direction = 1;
			
		} else if(yMove < 0) {
			direction = 3;
			currentAnim = up;
			
		} else if(yMove > 0) {
			direction = 2;
			currentAnim = down;
			
		} else {
			currentAnim = down;
		}
		
		checkPlayer();
		move();
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getGameCamera().getxOffset()), (int) (this.y - handler.getGameCamera().getyOffset() ), 20, 20, null);
	}

	@Override
	public void die() {
		
	}

	@Override
	public void interact() {
		
	}
	
	public void checkPlayer() {
		
		if(handler.getWorld().getEntityManager().getPlayer().getX() - x < 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getY() - y) < 50) {
			direction = 1;
			xMove = -1;
			yMove = 0;
		} else if(handler.getWorld().getEntityManager().getPlayer().getX() - x > 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getY() - y) < 50) {
			direction = 0;
			xMove = 1;
			yMove = 0;
		} else if(handler.getWorld().getEntityManager().getPlayer().getY() - y < 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getX() - x) < 50) {
			direction = 2;
			yMove = -1;
			xMove = 0;
		} else if(handler.getWorld().getEntityManager().getPlayer().getY() - y > 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getX() - x) < 50) {
			direction = 3;
			yMove = 1;
			xMove = 0;
		}
		
	}

}
