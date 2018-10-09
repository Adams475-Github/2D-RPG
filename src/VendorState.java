import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class VendorState extends State {

	private UIManager uiManager;
	private Vendor vendor;
	
	public VendorState(Handler handler, Vendor vendor) {
		super(handler);
		
		this.vendor = vendor;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(400, 260, 200, 50, Assets.btn_blank, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().getGameState());
			}}));
		
		uiManager.addObject(new UIImageButton(400, 350, 200, 50, Assets.btn_blank, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				//SettingState settingState = new SettingState(handler);
				State.setState(handler.getGame().settingState);
			}}));
		
		uiManager.addObject(new UIImageButton(400, 440, 200, 50, Assets.btn_blank, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(0);
			}}));
		
	}

	

	public void tick() {

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			State.setState(handler.getGame().gameState);
		}
		uiManager.tick();
	}



	@Override
	public void render(Graphics g) {
		
		for(int i = 0; i < vendor.getInv().size(); i++) {
			g.drawImage(vendor.getInv().get(i).texture, 100*i, 10, 80, 80, null);
		}
		uiManager.render(g);
		
	}

}
