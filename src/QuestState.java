import java.awt.Graphics;
import java.awt.Point;

public class QuestState extends State {
	
	public Point centerScreen = new Point(Launcher.SCREEN_WIDTH/2, Launcher.SCREEN_HEIGHT/2);
	private UIManager uiManager;

	public QuestState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(160, 215, 19 * 4, 8 * 4, Assets.questLeft, new ClickListener() {

			@Override
			public void onClick() {
				
				
			}}));
		
		uiManager.addObject(new UIImageButton(795, 215, 19 * 4, 8 * 4, Assets.questRight, new ClickListener() {

			@Override
			public void onClick() {
				
				
			}}));
		
		for(int i = 0; i < 3; i++) {
			uiManager.addObject(new UIImageButton(150, 298 + 104 * i, 10 * 4, 10 * 4, Assets.questButton, new ClickListener() {
				
				@Override
				public void onClick() {
					
					
				}}));
		}
		
		for(int i = 3; i < 6; i++) {
			uiManager.addObject(new UIImageButton(524, 298 + 104 * i, 10 * 4, 10 * 4, Assets.questButton, new ClickListener() {
				
				@Override
				public void onClick() {
//					if(handler.getWorld().getEntityManager().getPlayer().getQuests().size() >= (i + 1) ) {
//						State.setState(new QuestDisplayState(handler, handler.getWorld().getEntityManager().getPlayer().getQuests().get(i)));
//					}
					
				}}));
		}
		
//		uiManager.addObject(new UIImageButton(150, 402, 10 * 4, 10 * 4, Assets.questButton, new ClickListener() {
//
//			@Override
//			public void onClick() {
//				
//				
//			}}));
//		
//		uiManager.addObject(new UIImageButton(150, 506, 10 * 4, 10 * 4, Assets.questButton, new ClickListener() {
//
//			@Override
//			public void onClick() {
//				
//				
//			}}));
		
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.questScreen, centerScreen.x - Assets.questScreen.getWidth() * 4 / 2, 
				centerScreen.y - Assets.questScreen.getWidth() - 20,
				Assets.questScreen.getWidth() * 4 + 1, Assets.questScreen.getHeight() * 4 + 1, null);
		uiManager.render(g);
	}

	@Override
	public void init() {
		
		handler.getMouseManager().setUIManager(uiManager);
	
	}

}
