import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DialogueState extends State {

	private Creature creature;
	private BufferedImage background;
	private Rectangle dialogueBox = new Rectangle(468, 135, 418, 100);
	private UIManager uiManager;
	private ArrayList<Quest> giveableQuests;
	private int dialogueIndex = 0;
	
	public DialogueState(Handler handler, Creature creature, BufferedImage background, ArrayList<Quest> giveableQuests) {
		super(handler);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		this.background = background;
		this.creature = creature;
		this.giveableQuests = giveableQuests;
		
		//left arrow		
		uiManager.addObject(new UIImageButton(475, 360, 19 * 4, 8 * 4, Assets.questLeft, new ClickListener() {
			
		
			@Override
			public void onClick() {
				if(dialogueIndex > 0) {
					dialogueIndex--;
				}
				
			}}));
		
		//Right Arrow
		
		uiManager.addObject(new UIImageButton(800, 360, 19 * 4, 8 * 4, Assets.questRight, new ClickListener() {

			@Override
			public void onClick() {
				if(dialogueIndex < creature.dialogueList.size() - 1) {
					dialogueIndex++;
				}
				
			}}));
		
		//top text
		uiManager.addObject(new UIImageButton(115, 702 - Assets.btn_text[0].getHeight() * 2, Assets.btn_text[0].getWidth(),  Assets.btn_text[0].getHeight(), Assets.btn_text, new ClickListener() {

			@Override
			public void onClick() {
				handler.getWorld().getEntityManager().getPlayer().getQuests().add(giveableQuests.get(0));
				creature.giveableQuests.clear();
			}}));
		//middle text
		uiManager.addObject(new UIImageButton(115, 702 - Assets.btn_text[0].getHeight(), Assets.btn_text[0].getWidth(),  Assets.btn_text[0].getHeight(), Assets.btn_text, new ClickListener() {

			@Override
			public void onClick() {
				creature.giveableQuests.clear();
				
			}}));
		//bottom text
		uiManager.addObject(new UIImageButton(115, 702, Assets.btn_text[0].getWidth(),  Assets.btn_text[0].getHeight(), Assets.btn_text, new ClickListener() {

			@Override
			public void onClick() {
				State.setState(handler.getGame().gameState);
				handler.getMouseManager().setUIManager(null);
				
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
		//first layer
		//g.drawImage(background, 0, 0, Launcher.SCREEN_WIDTH, Launcher.SCREEN_HEIGHT, null);
		
		
		//last layer (excluding font)
		
		g.drawImage(Assets.conversationUI, 0, 0, Launcher.SCREEN_WIDTH, Launcher.SCREEN_HEIGHT, null);
		g.setColor(Color.WHITE);
		if(giveableQuests.isEmpty()) {
			FontHandler.drawFont(g, creature.dialogueList.get(dialogueIndex), dialogueBox, FontLoader.highTower);
		} else {
			FontHandler.drawFont(g, "I have a quest for you. " + giveableQuests.get(0).description + ". Would you like to accept?   ", dialogueBox, FontLoader.highTower);
		}
		uiManager.render(g);
	}

	@Override
	public void init() {
		handler.getMouseManager().setUIManager(uiManager);
		
	}

}
