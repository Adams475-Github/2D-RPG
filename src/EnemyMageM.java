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
	private int direction;
	private int num;
	private int forceDir;
	private int moveCount = 0;
	private int steeringTime;
	private float centerX;
	private float centerY;
	private float distanceNeed;
	private int ticksNeed;
	private Entity immediateBlocker;
	private float offsetX, offsetY;
	private boolean tracking = true;
	private Point ahead = new Point();
	private long lastMove, moveCooldown = 200, moveTimer = moveCooldown;
	private boolean forceStop = false;
	private boolean moving = true;
	private int steering, steeringY;
	private float rightSide, leftSide, top, bottom;
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
	
	}

	@Override
	public void tick() {
		
		
		offsetX = handler.getGameCamera().getxOffset();
		offsetY = handler.getGameCamera().getyOffset();
//		ahead.addElement(0);
//		ahead.addElement(1);
//		
//		ahead.setElementAt( (int) (x - handler.getGameCamera().getxOffset() + (this.xMove * 25)), 0);
//		ahead.setElementAt( (int) (y - handler.getGameCamera().getyOffset() + (this.yMove * 25)), 1);
//		
//		ahead2.addElement(0);
//		ahead2.addElement(1);
//		
//		ahead2.setElementAt( (int) (x - handler.getGameCamera().getxOffset() + (this.xMove * 50)), 0);
//		ahead2.setElementAt( (int) (y - handler.getGameCamera().getyOffset() + (this.yMove * 50)), 1);
		
		
		
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
		
		
		//npcWanderLogic();
		npcTrackLogic();
		checkBounds();
		checkNav();
		attack();
		checkPlayer();
		move();
		npcWanderLogic();
		
		
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
		
		
		
		//g.drawRect( (int) (walkingArea.x - handler.getGameCamera().getxOffset()),(int) (walkingArea.y - handler.getGameCamera().getyOffset()), walkingArea.width, walkingArea.height);
	}
	
	public void attack() {
		if(Math.abs((this.x - handler.getWorld().getEntityManager().getPlayer().getX())) < 50 && Math.abs((this.y - handler.getWorld().getEntityManager().getPlayer().getY())) < 50){
			
			handler.getWorld().getEntityManager().getPlayer().health -= 5;
		}
	}
	
	//public void 

	@Override
	public void die() {
		
	}
	
	public void checkNav() {
		
//		for(int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++){
//			
//			if(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-handler.getGameCamera().getxOffset(),
//					-handler.getGameCamera().getyOffset()).contains(ahead) && handler.getWorld().getEntityManager().getEntities().get(i) != this 
//					&& handler.getWorld().getEntityManager().getEntities().get(i) != handler.getWorld().getEntityManager().getPlayer()) {
//				
//				avoidance(handler.getWorld().getEntityManager().getEntities().get(i));
//
//				return;
//			} 
//			
//			if(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-handler.getGameCamera().getxOffset(), 
//					-handler.getGameCamera().getyOffset()).contains(ahead) && handler.getWorld().getEntityManager().getEntities().get(i) != this 
//					&& handler.getWorld().getEntityManager().getEntities().get(i) != handler.getWorld().getEntityManager().getPlayer()) {
//				
//				avoidance(	handler.getWorld().getEntityManager().getEntities().get(i));
//
//				return;
//			} 
//		}
		
		
		
		
	}
	
	private void avoidance(Entity e) {
		
		
		
		centerX = (e.x - handler.getGameCamera().getxOffset()) + e.bounds.width / 2;
		centerY = (e.y - handler.getGameCamera().getyOffset()) + e.bounds.height / 2;
		
		rightSide = (e.x - handler.getGameCamera().getxOffset() + e.bounds.width);
		leftSide = (e.x - handler.getGameCamera().getxOffset());
		top = (e.y - handler.getGameCamera().getyOffset());
		bottom = (e.y - handler.getGameCamera().getyOffset() + e.bounds.height);
		
		
		
		if(ahead.x > centerX  && Math.abs(this.y - bottom) > e.bounds.height - 3 || Math.abs((this.y + bounds.height) - top) > e.bounds.height - 3) {
			
			steering = 2;
			tracking = false;
			
		} else {
			steering = -2;
			tracking = false;
			
		}
		
		if(ahead.y > centerY) {
			
			steeringY = 2;
			
		} else {
			steeringY = -2;
		}
		
		steeringTime = 2;
		
	}
	
	private void npcTrackLogic(){
		
		if(!tracking) {
			return;
		}
		
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
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(-offsetX, -offsetY).contains(ahead)) {
			return;
			
		}
		
		if (steeringTime > 0) {
			
			xMove = steering;
			yMove = steeringY;

			steeringTime -= 1;
			
		} else {
			tracking = true;
		}
		
		if(direction == 0) {
			
			if(lastX == x) {
				
				steering = 0;
				
				for(int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
					
					if(handler.getWorld().getEntityManager().getEntities().get(i).getCollisionBounds(-offsetX, -offsetY).contains(ahead)) {
						immediateBlocker = handler.getWorld().getEntityManager().getEntities().get(i);
					}
					
				}
				
				
				
				if( (this.x - offsetX) - (handler.getWorld().getEntityManager().getPlayer().x - offsetX) > 0) {
					
					steeringY = -2;
					distanceNeed = Math.abs(ahead.y - immediateBlocker.getCollisionBounds(-offsetX, -offsetY).y);
					ticksNeed = (int) Math.ceil(distanceNeed / 20.0);
					steeringTime = ticksNeed;
					tracking = false;
					
					System.out.println("distance : " + distanceNeed);
					System.out.println("ticks: " + ticksNeed);
					System.out.print(immediateBlocker);
					
				} else {
					
					distanceNeed = Math.abs(immediateBlocker.getCollisionBounds(-offsetX, -offsetY).y - ahead.y);
					ticksNeed = (int) Math.ceil(distanceNeed / 40.0);
					steeringTime = ticksNeed;
					steeringY = 2;
					tracking = false;
					System.out.println("distance : " + distanceNeed);
					System.out.println("ticks: " + ticksNeed);
					System.out.print(immediateBlocker);
					
				}
				
				tracking = false;
			}
			
		} else if (direction == 1)  {
			
			if(lastX == x) {
				steeringY = -2;
				steering = 0;
				steeringTime = 1;
				tracking = false;
			}
			
		} else if(direction == 2) {
			
			if(lastY == y) {
				steering = 2;
				steeringY = 0;
				steeringTime = 1;
				tracking = false;
			}
			
			
		} else if(direction == 3) {
			
			if(lastY == y) {
				steeringTime = 1;
				steering = -2;
				steeringY = 0;
				tracking = false;
			}
			
		} else if(lastX != x && lastY != y){
			tracking = true;
		}
		
		
		
		lastY = y;
		lastX = x;
		
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
