import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FemaleRanger extends Creature{
	
	private Animation animDown;
	private Animation animUp;
	private Animation animLeft;
	private Animation animRight;
	private Animation animStill;
	Rectangle walkingArea = new Rectangle(200, 300, 500, 500);
	private Random rand = new Random();
	
	private long lastTurn = System.currentTimeMillis();
	
	private int moveX, moveY;

	public FemaleRanger(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.width = width;
		this.handler = handler;
		this.height = height;
		this.y = y;
		this.x = x;
		
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
		  
		
		
		move();
		
		
		
	}
	
	

	@Override
	public void render(Graphics g) {
		
		g.drawImage(animRight.getCurrentFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
		
	}

	@Override
	public void die() {
		
		
	}
	
	private void npcWanderLogic(){
		int num = rand.nextInt(3);
		
		if(num == 0) {
			xMove = speed;
		} else if (num == 1) {
			xMove = -speed;
		}else if (num == 2) {
			yMove = speed;
		} else if (num == 3) {
			yMove = -speed;
		}
	}

	@Override
	public void interact() {
		
		
	}

}
