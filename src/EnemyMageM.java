import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyMageM extends Creature{
	
	private Animation animDown;
	private Animation animUp;
	private Animation animLeft;
	private Animation animRight;
	private Animation animStill;
	private Animation currentAnim;
	private int direction;
	private int num;
	private int forceDir;
	private int moveCount = 0;
	private long lastMove, moveCooldown = 800, moveTimer = moveCooldown;
	private boolean forceStop = false;
	private boolean moving = true;
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
	
	}

	@Override
	public void tick() {
		
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animDown.tick();
		  
		if(xMove > 0) {
			currentAnim = animRight;
			direction = 0;
			
		} else if(xMove < 0) {
			currentAnim = animLeft;
			direction = 1;
			
		} else if(yMove < 0) {
			direction = 2;
			currentAnim = animUp;
			
		} else if(yMove > 0) {
			direction = 3;
			currentAnim = animDown;
			
		} else {
			currentAnim = animStill;
		}
		
		
		//npcWanderLogic();
		npcTrackLogic();
		checkBounds();
		checkPlayer();
		move();
		
		
		
	}
	
	

	@Override
	public void render(Graphics g) {
		
		if(currentAnim != animStill) {
			g.drawImage(currentAnim.getCurrentFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		} else {
			g.drawImage(Assets.mageM_idle[direction], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		}
		
		
		
		//g.drawRect( (int) (walkingArea.x - handler.getGameCamera().getxOffset()),(int) (walkingArea.y - handler.getGameCamera().getyOffset()), walkingArea.width, walkingArea.height);
	}

	@Override
	public void die() {
		
		
	}
	
	private void npcTrackLogic(){
		
		if(this.x < handler.getWorld().getEntityManager().getPlayer().x) {
			if(Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) < 5) {
				xMove = 0;
			} else {
				xMove = 2;
			}
			
		}
		
		if(this.x > handler.getWorld().getEntityManager().getPlayer().x) {
			if(Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().x) < 5) {
				xMove = 0;
			} else {
				xMove = -2;
			}
			
		}
		
		if(this.y < handler.getWorld().getEntityManager().getPlayer().y) {
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 5) {
				yMove = 0;
			} else {
				yMove = 2;
			}
			
		}
		
		if(this.y > handler.getWorld().getEntityManager().getPlayer().y) {
			
			if(Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().y) < 5) {
				yMove = 0;
			} else {
				yMove = -2;
			}
			
		}
		
	}
	
	private void npcWanderLogic(){
		moveTimer += System.currentTimeMillis() - lastMove;
		lastMove = System.currentTimeMillis();		
		
		
		if(moveTimer < moveCooldown) {
			return;
		}
		
		if(forceStop) {
			xMove = 0;
			yMove = 0;
			
		}
			
		if(forceDir == 0) {
			yMove = -speed;
			return;
			
		} else if(forceDir == 1) {
			xMove = speed;
			return;
			
		} else if(forceDir == 2) {
			yMove = speed;
			return;
			
		} else if(forceDir == 3) {
			xMove = -speed;
			return; 
			
		}
			
		
		
		num = rand.nextInt(10);
		
		if(num == 0) {
			xMove = speed;
			yMove = 0;
			forceStop = true;
			
		} else if (num == 1) {
			xMove = -speed;
			yMove = 0;
			forceStop = true;
			
		}else if (num == 2) {
			yMove = speed;
			xMove = 0;
			forceStop = true;
			
		} else if (num == 3) {
			yMove = -speed;
			xMove = 0;
			forceStop = true;
			
		} else if (num >=4) {
			xMove = 0;
			yMove = 0;
			forceStop = false;
		}
		
		moveTimer = 0;

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
