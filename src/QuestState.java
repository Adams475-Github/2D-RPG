import java.awt.Graphics;
import java.awt.Point;

public class QuestState extends State {
	
	public Point centerScreen = new Point(Launcher.SCREEN_WIDTH/2, Launcher.SCREEN_HEIGHT/2);
	private UIManager uiManager;

	public QuestState(Handler handler) {
		super(handler);
		System.out.println("yeyeye");
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(100, 100, 19 * 4, 8 * 4, Assets.questLeft, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.questScreen, centerScreen.x - Assets.questScreen.getWidth() * 4 / 2, 
				centerScreen.y - Assets.questScreen.getWidth() - 20,
				Assets.questScreen.getWidth() * 4, Assets.questScreen.getHeight() * 4, null);
		uiManager.render(g);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		handler.getMouseManager().setUIManager(uiManager);
	}

}
