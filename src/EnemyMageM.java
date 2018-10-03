import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

public class EnemyMageM extends Creature{
	
	private Animation animDown;
	private Animation animUp;
	private Animation animLeft;
	private Animation animRight;
	private Animation animStill;
	private Animation currentAnim;
	private Rectangle left, right, up, down;
	private int direction;
	private int num;
	private int forceDir;
	private int moveCount = 0;
	private int steeringTime;
	private float centerX;
	private float centerY;
	private boolean xStopped, yStopped, stopped;
	private float distanceNeed;
	private int ticksNeed;
	private Entity pathBlocker;
	private float offsetX, offsetY;
	private boolean tracking = true;
	private Point ahead = new Point();
	private long lastMove, moveCooldown = 200, moveTimer = moveCooldown;
	private boolean forceStop = false;
	private boolean moving = true;
	private int steering, steeringY;
	private float lastX, lastY;
	Rectangle walkingArea = new Rectangle(200, 300, 300, 200);
	private Random rand = new Random();

	public EnemyMageM(Handler handler, float x, float y, int width, int height) {
		//TODO add bounding rectangle to the arguments in constructor
		super(handler, x, y, width, height);
		this.width = width;
		this.handler = handler;
		this.height = height;
		this.y = y;
		this.x = x;
		
		bounds.width = 20*4;
		bounds.height = 23*4;
		
		animDown = new Animation(250, Assets.mageM_down);
		animUp = new Animation(250, Assets.mageM_up);	
		animLeft = new Animation(250, Assets.mageM_left);
		animRight = new Animation(250, Assets.mageM_right);
		animStill = new Animation(250, Assets.mageM_idle);
		
		left = new Rectangle( (int) this.x - 20, (int) this.y, 20, bounds.height);
		right = new Rectangle( (int) this.x + bounds.width, (int) this.y, 20, bounds.height );
		up = new Rectangle( (int) this.x, (int) this.y - 20, bounds.width, 20);
		down = new Rectangle( (int) this.x, (int) this.y + bounds.height, bounds.width, 20);
	
	}

	@Override
	public void tick() {
		
		updateBoxes();
		
		offsetX = handler.getGameCamera().getxOffset();
		offsetY = handler.getGameCamera().getyOffset();		
		
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animDown.tick();
		  
		if(xMove > 0) {
			currentAnim = animRight;
			ahead.setLocation((int) (x - offsetX + bounds.width + 20), (int) (y - offsetY + bounds.height/2));
			direction = 0;
			
		} else if(xMove < 0) {
			currentAnim = animLeft;
			ahead.setLocation((int) (x - offsetX - 20), (int) (y - offsetY + bounds.height/2));
			direction = 1;
			
		} else if(yMove < 0) {
			direction = 2;
			ahead.setLocation((int) (x - offsetX + bounds.width/2), (int) (y - offsetY - 20));
			currentAnim = animUp;
			
		} else if(yMove > 0) {
			direction = 3;
			ahead.setLocation((int) (x - offsetX + bounds.width/2), (int) (y - offsetY + bounds.height + 20));
			currentAnim = animDown;
			
		} else {
			currentAnim = animStill;
		}
		
		npcWanderLogic();
		collisionLogic();
		if(!stopped) {
			npcTrackLogic();
		}
		avoidance();
		checkBounds();
		attack();
		checkPlayer();
		move();
		
		
		
	}
	
	

	@Override
	public void render(Graphics g) {
		
		if(currentAnim != animStill) {
			g.drawImage(currentAnim.getCurrentFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
			
			g.fillOval(ahead.x- 5, ahead.y -5, 10, 10);
			
		} else {
			g.drawImage(Assets.mageM_idle[direction], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		}
		
		for(int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++){
			
		
			g.drawRect((int)(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(0, 0).x - handler.getGameCamera().getxOffset()), (int) (handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(0, 0).y - handler.getGameCamera().getyOffset()), handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(0, 0).width, handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(0, 0).height);
		}
		
		g.drawRect(left.x, left.y, left.width, left.height);
		g.drawRect(right.x, right.y, right.width, right.height);
		g.drawRect(up.x, up.y, up.width, up.height);
		g.drawRect(down.x, down.y, down.width, down.height);
			
	}
	
	
	private void updateBoxes() {
		
		left.x = (int) (this.x - 20 - offsetX); 
		left.y = (int) (this.y - offsetY);
		left.width = 20;
		left.height = bounds.height;
		
		right.x = (int) (this.x + bounds.width - offsetX); 
		right.y = (int) (this.y - offsetY);
		right.width = 20;
		right.height = bounds.height;	
		
		up.x = (int) (this.x - offsetX); 
		up.y = (int) (this.y - 20 - offsetY);
		up.width = bounds.width;
		up.height = 20;
		
		down.x = (int) (this.x - offsetX); 
		down.y = (int) (this.y + bounds.height - offsetY);
		down.width = bounds.width;
		down.height = 20;
		
		
	}
	
	public void attack() {
		
	}
	

	@Override
	public void die() {
		
	}
	
	private void avoidance() {
				
		for(int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
			
			if(left.intersects(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-offsetX, -offsetY)) && xMove != 0) {
				dodge(1);		
				
			} 
			
			if(up.intersects(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-offsetX, -offsetY)) && yMove != 0) {
				dodge(2);	
						
			}
			if(right.intersects(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-offsetX, -offsetY)) && xMove != 0) {
				dodge(0);	
				
			}
			
			if(down.intersects(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-offsetX, -offsetY)) && yMove != 0) {
				dodge(3);	
				
			}
			
		}
		
		
		
	}
	
	private void dodge(int d) {
		
		moveTimer += System.currentTimeMillis() - lastMove;
		lastMove = System.currentTimeMillis();		
		
		
		if(moveTimer < moveCooldown) {
			return;
		}
		
		
		moveTimer = 0;
		
	}
	
	private void collisionLogic() {
//		
//		moveTimer += System.currentTimeMillis() - lastMove;
//		lastMove = System.currentTimeMillis();		
//		
//		if(xMove != 0 && lastX == this.x) {
//			xStopped = true;
//		} else {
//			xStopped = false;
//			
//		}
//		
//		if(yMove != 0 && lastY == this.y) {
//			yStopped = true;
//			
//		} else {
//			yStopped = false;
//			steering = 0;
//			steeringY = 0;
//		}
//		
//		if(yStopped || xStopped) {
//			stopped = true;
//		} else {
//			stopped = false;
//		}
//		
//		if(moveTimer < moveCooldown) {
//			return;
//		}
//		
//		lastX = this.x;
//		lastY = this.y;
//		
//		moveTimer = 0;
//		
		
	}

	
	private void npcTrackLogic(){
		
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
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 5) {
				yMove = 0;
			} else {
				yMove = 1;
			}
			
		}
		
		if(this.y > handler.getWorld().getEntityManager().getPlayer().y) {
			
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 5) {
				yMove = 0;
			} else {
				yMove = -1;
			}
			
		}
		
	}
	
	private void npcWanderLogic(){
		

	}
	
	private void checkBounds() {
		if(x < walkingArea.x) {
			forceDir = 1;
			forceStop = true;
			moveCount = 2;
			
		} else if(x > walkingArea.x + walkingArea.width) {
			forceDir = 3;
			forceStop = true;
			moveCount = 2;
			
		} else if(y < walkingArea.y) {
			forceDir = 2;
			forceStop = true;
			moveCount = 2;
			
		} else if(y > walkingArea.y + walkingArea.height) {
			forceDir = 0;
			forceStop = true;
			moveCount = 2;
			
		} else {
			forceDir = -1;
			forceStop = true;
			moveCount = 1;
		}
		
	}
	
	public void checkPlayer() {
		
		if(Math.abs(handler.getWorld().getEntityManager().getPlayer().getX() - x) < 100 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getY() - y) < 100) {
			moving = false;
		} else {
			moving = true;
		}
		
		if(handler.getWorld().getEntityManager().getPlayer().getX() - x < 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getY() - y) < 50) {
			direction = 1;
		} else if(handler.getWorld().getEntityManager().getPlayer().getX() - x > 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getY() - y) < 50) {
			direction = 0;
		} else if(handler.getWorld().getEntityManager().getPlayer().getY() - y < 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getX() - x) < 50) {
			direction = 2;
		} else if(handler.getWorld().getEntityManager().getPlayer().getY() - y > 0 && Math.abs(handler.getWorld().getEntityManager().getPlayer().getX() - x) < 50) {
			direction = 3;
		}
		
	}

	@Override
	public void interact() {
		
		
	}


}
