import java.awt.Graphics;

public class SpellFireBall extends Creature {
	
	private Animation right, left, up, down;
	private Animation currentAnim;
	private int direction;
	private boolean doneTracking;

	public SpellFireBall(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.health = 999;
		
		right = new Animation(100, Assets.fb_right);
		left = new Animation(100, Assets.fb_left);
		up = new Animation(100, Assets.fb_up);
		down = new Animation(100, Assets.fb_down);
		
		//ignore(?)
		currentAnim = right;
		
	}

	@Override
	public void tick() {
		
		
		right.tick();
		left.tick();
		up.tick();
		down.tick();
		
		if(xMove > 0) {
			this.width = 52;
			this.height = 18;
			currentAnim = right;
			direction = 0;
			
		} else if(xMove < 0) {
			this.width = 52;
			this.height = 18;
			currentAnim = left;
			direction = 1;
			
		} else if(yMove < 0) {
			this.width = 16;
			this.height = 32;
			direction = 3;
			currentAnim = up;
			
		} else if(yMove > 0) {
			this.width = 16;
			this.height = 32;
			direction = 2;
			currentAnim = down;
			
		} 
		
		
		
		trackPlayer();
		move();
		
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getGameCamera().getxOffset()), (int) (this.y - handler.getGameCamera().getyOffset() ), width, height, null);
	
	}

	@Override
	public void die() {
		
	}

	@Override
	public void interact() {
		
	}
	
	public void trackPlayer() {
		
//		if(doneTracking && Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) > 80 || Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) > 80) {
//			active = false;
//		}
//		
		if(Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) < 30 || Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 30 || doneTracking) {
			doneTracking = true;
			return;
		}
		
		if(this.x < handler.getWorld().getEntityManager().getPlayer().x) {
			if(Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) < 5) {
				xMove = 0;
			} else {
				xMove = 1;
			}
			
		}
		
		if(this.x > handler.getWorld().getEntityManager().getPlayer().x) {
			if(Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) < 5) {
				xMove = 0;
			} else {
				xMove = -1;
			}
			
		}
		
		if(this.y < handler.getWorld().getEntityManager().getPlayer().y) {
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 20) {
				yMove = 0;
			} else {
				yMove = 1;
			}
			
		}
		
		if(this.y > handler.getWorld().getEntityManager().getPlayer().y) {
			
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 20) {
				yMove = 0;
			} else {
				yMove = -1;
			}
			
		}
		
		
	}

}
