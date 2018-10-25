import java.awt.Graphics;

public class SettingState extends State {
	
	private UIManager uiManager;

	public SettingState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(100, 100, 150, 50, Assets.btn_inc_pos, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().getMenuState());
			}}));
	}
		
	public void tick() {
		
		uiManager.tick();
	}

	public void render(Graphics g) {
		uiManager.render(g);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	

}