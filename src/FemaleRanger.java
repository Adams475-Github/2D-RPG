import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class FemaleRanger extends Creature{
	
	private Animation animDown;
	private Animation animUp;
	private Animation animLeft;
	private Animation animRight;
	private Animation animStill;
	private Animation currentAnim = animDown;
	private int direction;
	private int num;
	private int forceDir;
	private int moveCount = 0;
	private long lastMove, moveCooldown = 800, moveTimer = moveCooldown;
	private boolean forceStop = false;
	private boolean moving = true;
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
		
		giveableQuests.add(new BountyQuest(handler, "Kill the elf", "Just kill me lol",
				"0", this, 
				Item.swordStarter,  handler.getWorld().getEntityManager().getPlayer(), handler.getWorld(), 300));
		
		dialogueList.add("Hello and welcome to chilis");
		
		bounds.width = 20*4;
		bounds.height = 23*4;
		
		animDown = new Animation(250, Assets.archF_down);
		animUp = new Animation(250, Assets.archF_up);	
		animLeft = new Animation(250, Assets.archF_left);
		animRight = new Animation(250, Assets.archF_right);
		animStill = new Animation(250, Assets.archF_idle);
		expGive = 100;
	
	}

	@Override
	public void tick() {
		
		checkQuest();
		tickAnimations();
		findDirection();		
		npcWanderLogic();
		checkBounds();
		checkPlayer();
		move();		

	}
	
	

	@Override
	public void render(Graphics g) {
		
		if(currentAnim != animStill) {
			g.drawImage(currentAnim.getCurrentFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		} else {
			g.drawImage(Assets.archF_idle[direction], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 77, 93, null);
			
		}
		
//		if(!moving) {
//			g.setFont(Assets.font28);
//			g.drawImage(Assets.speechBubble, (int) (x - handler.getGameCamera().getxOffset()) - 10, (int) (y - handler.getGameCamera().getyOffset()) - 100, 52 * 2, 47 * 2, null);
//			g.drawString("Hello!", (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()) - 50);
//		}
		
		if(hasQuest) {
			g.drawImage(Assets.questMark, (int)(x + 25 - handler.getGameCamera().getxOffset()),  
					(int)(y - 75 - handler.getGameCamera().getyOffset()), 7 * 4, 17 * 4, null);
		}
		
		//g.drawRect( (int) (walkingArea.x - handler.getGameCamera().getxOffset()),(int) (walkingArea.y - handler.getGameCamera().getyOffset()), walkingArea.width, walkingArea.height);
	}

	@Override
	public void die() {
		handler.getWorld().getEntityManager().getPlayer().addExp(expGive);
		
	}
	
	private void tickAnimations() {
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animDown.tick();
	}
	
	
	
	private void checkQuest() {
		if(!giveableQuests.isEmpty()) {
			hasQuest = true;
		} else {
			hasQuest = false;
		}
	}
	
	private void findDirection() {
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
	}
	
	private void npcWanderLogic(){
		moveTimer += System.currentTimeMillis() - lastMove;
		lastMove = System.currentTimeMillis();

		if(!moving) {
			
			xMove = 0;
			yMove = 0;
			return;
		}
		
		
		
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
		this.isTalkedTo = true;
		State.setState(new DialogueState(handler, this, null, giveableQuests));
		System.out.println("hello");
	}

	
}
