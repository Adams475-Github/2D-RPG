import java.awt.Graphics;

public class SkillState extends State{

	private static final long serialVersionUID = -8479080241939924883L;
	private UIManager uiManager;
	
	
	public SkillState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(400, 260, 200, 50, Assets.btn_blank, new ClickListener() {

			@Override
			public void onClick() {
				
			}}));
		
		

		
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
	}

	@Override
	public void init() {
		handler.getMouseManager().setUIManager(uiManager);
		
	}

}
