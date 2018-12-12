import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Player extends Creature implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3519735914463066053L;
	//Instance variable initialization
	
	//Inventory
	private float baseSpeed = 4;
	private Inventory inventory;
	private EscapeMenu escapeMenu;
	private Item sword;
	private Item helmet;
	private Item chestPlate;
	private Item bobble;
	private Item shield;
	
	//Post control variables
	private int damageFinal;
	private int critChanceFinal;
	private int healthFinal;
	private float moveSpeedFinal;
	private int resistanceFinal;
	

	
	//display box code
	private boolean drawDisplayBox = false;
	private Item displayBoxItem;
	private Rectangle displayRect = new Rectangle(30 + Launcher.SCREEN_WIDTH / 2 - Assets.displayBoxRed.getWidth() * 2,
			Launcher.SCREEN_HEIGHT / 2 - Assets.displayBoxRed.getHeight() * 2 + 38, Assets.displayBoxRed.getWidth() * 4 - 200, 60);
	private Quest displayQuest;
	
	//Quests
	private ArrayList<Quest> quests = new ArrayList<Quest>();
	private ArrayList<Quest> completedQuests = new ArrayList<Quest>();
	
	//Interaction Rectangle
	Rectangle interactionBounds;
	
	//Overlay stuff
	private int expL;
	private int exp;
	private int maxHealth;
	
	//Skills
	private SkillManager skillManager;
	private int skillAttack;
	private int skillCrit;
	private int skillHealth;
	private int skillMS;
	private int skillAnim;
	
	
	//Animations
	
	//Walking
	private Animation animDown = new Animation(250, Assets.player_down);
	private Animation animUp = new Animation(250, Assets.player_up);
	private Animation animLeft = new Animation(250, Assets.player_left);
	private Animation animRight = new Animation(250, Assets.player_right);
	
	//Attacking with a normal sword
	private Animation animAttackDown = new Animation(125, Assets.player_attack_down);
	private Animation animAttackUp = new Animation(125, Assets.player_attack_up);
	private Animation animAttackLeft = new Animation(125, Assets.player_attack_left);
	private Animation animAttackRight = new Animation(125, Assets.player_attack_right);
	
	//Attacking with a blue sword
	private Animation animAttackDownB = new Animation(125, Assets.player_attack_downB);
	private Animation animAttackUpB = new Animation(125, Assets.player_attack_upB);
	private Animation animAttackLeftB = new Animation(125, Assets.player_attack_leftB);
	private Animation animAttackRightB = new Animation(125, Assets.player_attack_rightB);

	//Array for still sprites
	private transient BufferedImage still[] = Assets.player_idle;
	
	//Miscellaneous Variables from ongoing implementation (will refactor)
	private int lastAnim = 0;
	private Animation currentAttack = animAttackDown;
	private boolean attacking;
	private long lastAttackTimer, attackCooldown = 550, attackTimer = attackCooldown;
	private boolean inv = false;
	private boolean esc = false;
	
	//Overlay (Might not need this but for now I'm doing it this way)
	transient Overlay playerO = new Overlay(handler);	
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 60, 98);
		
		//hit-box initialization
		bounds.x = 13;
		bounds.y = 60;
		bounds.width = 23;
		bounds.height = 32;	 
		exp = 29;
		maxHealth = 16;
		
		//Initializing stats/gear
		chestPlate = Item.nothing;
		sword = Item.nothing;
		this.health = 7;
		
		//inventory initialization
		inventory = new Inventory(handler);
		skillManager = new SkillManager(handler);
		escapeMenu = new EscapeMenu(handler);
		
		
	}
	
	public void tick() {
		
		skillManager.tick();
		calculateFinalValues();
		setValues();
		tickAnimations();
		checkQuests();
		manageSword();
		updateExp();
		playerO.tick();
		resetAnimations();
		checkAttacks();
		interactWith();
		manageMenus();
		getInput();
		move();
		resize();
		inventory.tick();
		removeEscapeMenu();
		
	}
	
	public void render(Graphics g) {

		//forces render to loop through attack if called, else draws normally
		//TODO refactor this garbage code
		if(attacking) {
			g.drawImage(currentAttack.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset() - 7), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			if(currentAttack.hasPlayedOnce() && !handler.getKeyManager().attackDown && !handler.getKeyManager().attackUp && !handler.getKeyManager().attackRight && !handler.getKeyManager().attackLeft) {
				attacking = false;
				setDimension(60, 98);
			}
		} else {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset() - 7), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
		
		escapeMenu.render(g);
		playerO.render(g);
		inventory.render(g);
		
	}
	
	public void renderDisplayBox(Graphics g) {
		g.drawImage(Assets.displayBoxRed, (Launcher.SCREEN_WIDTH / 2) - (Assets.displayBoxRed.getWidth() * 2), 
				(Launcher.SCREEN_HEIGHT / 2) - (Assets.displayBoxRed.getHeight() * 2), Assets.displayBoxRed.getWidth() * 4, 
				Assets.displayBoxRed.getHeight() * 4, null);
		
		g.drawImage(displayBoxItem.texture, (Launcher.SCREEN_WIDTH / 2) + 88, (Launcher.SCREEN_HEIGHT / 2) - 43, 80, 80, null);
		g.setColor(Color.white);
		FontHandler.drawFont(g, "You completed the \"" + displayQuest.title + "\" quest. Here is your reward: ", displayRect, FontLoader.highTowerSmall);
		
	}

	public void postRender(Graphics g) {
		playerO.render(g);
		inventory.render(g);
		escapeMenu.render(g);
		
		if(drawDisplayBox) {
			renderDisplayBox(g);
		}
	}
	
	private void calculateFinalValues() {
		damageFinal = sword.attackValue + skillAttack;
		healthFinal = health + skillHealth;
		moveSpeedFinal = baseSpeed + skillMS;
		
	}
	
	private void setValues() {
		this.speed = moveSpeedFinal;
	}
	
	private void checkQuests() {
		for(int i = 0; i < quests.size(); i++) {
			quests.get(i).checkCompleted();
		}
	}
	
	private void resetAnimations() {
		if(!attacking) {
			animAttackUp.set(0);
			animAttackDown.set(0);
			animAttackRight.set(0);
			animAttackLeft.set(0);
			
			animAttackUpB.set(0);
			animAttackDownB.set(0);
			animAttackRightB.set(0);
			animAttackLeftB.set(0);
		}
	}
	
	private void updateExp() {
		if(exp > 50) {
			exp = exp - 50;
			expL++;
		}
		
	}
	
	private void resize() {
		//Fixes Resizing if player sprite is bigger for animations
		if(attacking && (xMove + yMove) == 0) {
			
			if(currentAttack == animAttackRight || currentAttack == animAttackLeft || currentAttack == animAttackLeftB || currentAttack == animAttackRightB) {
				//attack left or right resizing
				setDimension(86, 122);
			}else {
				//attack up or down resizing
				setDimension(64, 122);
			}
		}else{
			//base size
			setDimension(60, 98);
			handler.getGameCamera().centerOnEntity(this);
		}
	}
	
	private void removeEscapeMenu() {
		if(drawDisplayBox && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			drawDisplayBox = false;
			displayBoxItem = null;
			displayQuest = null;
		} else {
			escapeMenu.tick();
		}
	}
	
	private void manageMenus() {
		if(this.getInventory().isActive()) {
			inv = true;
		} else {
			inv = false;
		}
		if(this.getEscapeMenu().isActive()) {
			esc = true;
		} else {
			esc = false;
		}
	}
	
	private void tickAnimations() {
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
	}
	
	private void manageSword() {
		if(this.sword == Item.swordStarter) {
			//Basic Sword
			animAttackDown.tick();
			animAttackUp.tick();
			animAttackRight.tick();
			animAttackLeft.tick();
		} else {
			//Blue Sword
			animAttackDownB.tick();
			animAttackUpB.tick();
			animAttackRightB.tick();
			animAttackLeftB.tick();
		}
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		//gets key presses
		if(handler.getKeyManager().up && !attacking && !inv) {
			yMove = -speed;
			
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
			State.setState(handler.getGame().skillState);
			handler.getGame().skillState.init();
		}
		if(handler.getKeyManager().down && !attacking && !inv) {
			yMove = speed;
		}
		if(handler.getKeyManager().left && !attacking && !inv) {
			xMove = -speed;
		}
		if(handler.getKeyManager().right&& !attacking && !inv) {
			xMove = speed;
			
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && !inv && sword != Item.nothing) {
			attacking = true;
			if(handler.getWorld().getEntityManager().getPlayer().sword != Item.blueSword) {
				currentAttack = animAttackDown;
			} else {
				currentAttack = animAttackDownB;
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && !inv && sword != Item.nothing) {
			attacking = true;
			if(handler.getWorld().getEntityManager().getPlayer().sword != Item.blueSword) {
				currentAttack = animAttackUp;
			} else {
				currentAttack = animAttackUpB;
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && !inv && sword != Item.nothing) {
			attacking = true;
			if(handler.getWorld().getEntityManager().getPlayer().sword != Item.blueSword) {
				currentAttack = animAttackLeft;
			} else {
				currentAttack = animAttackLeftB;
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && !inv && sword != Item.nothing) {
			attacking = true;
			if(handler.getWorld().getEntityManager().getPlayer().sword != Item.blueSword) {
				currentAttack = animAttackRight;
			} else {
				currentAttack = animAttackRightB;
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
			if(handler.getWorld().isRaining()) {
				handler.getWorld().setRaining(false);
				exp++;
			} else {
				handler.getWorld().setRaining(true);
			}
		}
	}
	
	public void setDisplayBox(Item item, Quest quest) {
		drawDisplayBox = true;
		displayBoxItem = item;
		displayQuest = quest;
	}
	
	public void addExp(int exp) {
		this.exp += exp;
	}
	
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		if(attackTimer < attackCooldown) {
			return;
		}
		
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().attackUp) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize - 40;
		} else if(handler.getKeyManager().attackDown) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height + 10;
		} else if(handler.getKeyManager().attackLeft) {
			ar.x = cb.x - arSize - 10;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if(handler.getKeyManager().attackRight) {
			ar.x = cb.x + cb.width + 10;
			ar.y = cb.y + cb.height / 2 - arSize / 2;;
		} else {
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(damageFinal);
			}
		}
		
	}
	
	public void removeQuest(Quest quest) {
		quests.remove(quest);
		
	}
	
	public void die() {
		//TODO
		System.out.println("You lose");
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		//returns animation frames based on movement
		//last anim keeps track of which direction the player is facing
		if(xMove < 0) {
			lastAnim = 3;
			return animLeft.getCurrentFrame();
		} else if(xMove > 0) {
			lastAnim = 2;
			return animRight.getCurrentFrame();
		} else if(yMove < 0) {
			lastAnim = 1;
			return animUp.getCurrentFrame();
		} else if(yMove > 0){
			lastAnim = 0;
			return animDown.getCurrentFrame();
		} else {
			//returns a standing sill player in the correct direction
			return still[lastAnim];
		}
		
	}
	

	//sets render dimension of player, used for consistent sizes with different width/height sprites
	public void setDimension(int setWidth, int setHeight) {
		width = setWidth;
		height = setHeight;
		
	}

	
	private void interactWith() {
		//Basically the exact same code as attack but interact instead, only need 1 hitbox that's kinda big as well
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		if(attackTimer < attackCooldown) {
			return;
		}
		
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 70;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			ar.x = cb.x - 30;
			ar.y = cb.y - 30;
			interactionBounds = new Rectangle((int)(this.x - 20), (int)(this.y - 20), 100, 130);
		} else {
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.interact();
			}
		}
	}
	
	//Implemented method needed since all entities must have an interact method, but the player doesn't do anything when interacted with yet.
	public void interact() {
		
	}
	
	//Getters and Setters

	public Inventory getInventory() {
		return inventory;
	}
	
	public EscapeMenu getEscapeMenu() {
		return escapeMenu;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Item getSword() {
		return sword;
	}

	public void setSword(Item sword) {
		this.sword = sword;
	}

	public Item getHelmet() {
		return helmet;
	}
	
	public ArrayList<Quest> getCompletedQuests() {
		return completedQuests;
	}

	public void setCompletedQuests(ArrayList<Quest> completedQuests) {
		this.completedQuests = completedQuests;
	}

	public void setHelmet(Item helmet) {
		this.helmet = helmet;
	}

	public Item getChestPlate() {
		return chestPlate;
	}

	public void setChestPlate(Item chestPlate) {
		this.chestPlate = chestPlate;
	}

	public Item getBobble() {
		return bobble;
	}

	public void setBobble(Item bobble) {
		this.bobble = bobble;
	}
	
	public int getExp() {
		return exp;
	}

	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public void setQuests(ArrayList<Quest> quests) {
		this.quests = quests;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getExpL() {
		return expL;
	}

	public void setExpL(int expL) {
		this.expL = expL;
	}

	public SkillManager getSkillManager() {
		return skillManager;
	}

	public void setSkillManager(SkillManager skillManager) {
		this.skillManager = skillManager;
	}

	public int getDamageFinal() {
		return damageFinal;
	}

	public void setDamageFinal(int damageFinal) {
		this.damageFinal = damageFinal;
	}

	public int getCritChanceFinal() {
		return critChanceFinal;
	}

	public void setCritChanceFinal(int critChanceFinal) {
		this.critChanceFinal = critChanceFinal;
	}

	public int getHealthFinal() {
		return healthFinal;
	}

	public void setHealthFinal(int healthFinal) {
		this.healthFinal = healthFinal;
	}

	public float getMoveSpeedFinal() {
		return moveSpeedFinal;
	}

	public void setMoveSpeedFinal(float moveSpeedFinal) {
		this.moveSpeedFinal = moveSpeedFinal;
	}

	public int getResistanceFinal() {
		return resistanceFinal;
	}

	public void setResistanceFinal(int resistanceFinal) {
		this.resistanceFinal = resistanceFinal;
	}

	public int getSkillAttack() {
		return skillAttack;
	}

	public void setSkillAttack(int skillAttack) {
		this.skillAttack = skillAttack;
	}

	public int getSkillCrit() {
		return skillCrit;
	}

	public void setSkillCrit(int skillCrit) {
		this.skillCrit = skillCrit;
	}

	public int getSkillHealth() {
		return skillHealth;
	}

	public void setSkillHealth(int skillHealth) {
		this.skillHealth = skillHealth;
	}

	public int getSkillMS() {
		return skillMS;
	}

	public void setSkillMS(int skillMS) {
		this.skillMS = skillMS;
	}

	public int getSkillAnim() {
		return skillAnim;
	}

	public void setSkillAnim(int skillAnim) {
		this.skillAnim = skillAnim;
	}

	
	

	



	
}
