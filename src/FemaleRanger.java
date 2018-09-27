import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class FemaleRanger extends Creature{
	
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
	Rectangle walkingArea = new Rectangle(200, 300, 300, 200);
	private Random rand = new Random();

	public FemaleRanger(Handler handler, float x, float y, int width, int height) {
		//TODO add bounding rectangle to the arguments in constructor
		super(handler, x, y, width, height);
		this.width = width;
		this.handler = handler;
		this.height = height;
		this.y = y;
		this.x = x;
		
		bounds.x = 18;
		bounds.y = 40;
		bounds.width = 50;
		bounds.height = 45;
		
		animDown = new Animation(250, Assets.archF_down);
		animUp = new Animation(250, Assets.archF_up);	
		animLeft = new Animation(250, Assets.archF_left);
		animRight = new Animation(250, Assets.archF_right);
		animStill = new Animation(250, Assets.archF_idle);
	
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
		
		
		npcWanderLogic();
		checkBounds();
		move();
		
		
		
	}
	
	

	@Override
	public void render(Graphics g) {
		
		if(currentAnim != animStill) {
			g.drawImage(currentAnim.getCurrentFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		} else {
			g.drawImage(Assets.archF_idle[direction], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		}
		
		//g.drawRect( (int) (walkingArea.x - handler.getGameCamera().getxOffset()),(int) (walkingArea.y - handler.getGameCamera().getyOffset()), walkingArea.width, walkingArea.height);
	}

	@Override
	public void die() {
		
		
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

	@Override
	public void interact() {
		
		
	}

}