import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class VendorState extends State {

	private UIManager uiManager;
	private Inventory inventory;
	private int centerScreenX = 1024/2, centerScreenY = 768/2;
	private int topLeftX = centerScreenX - (Assets.vendorScreen.getWidth() * 4 / 2), topLeftY = centerScreenY - (Assets.vendorScreen.getHeight() * 4 / 2);
	
	public VendorState(Handler handler, Inventory inventory) {
		super(handler);
		this.inventory = inventory;
		inventory.init();
		inventory.setActive(true);
		handler.getWorld().getEntityManager().getPlayer().getInventory().setActive(true);
		inventory.setCurrentInv(inventory.getInventoryAttack());
		
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		//Sell Button
		uiManager.addObject(new UIImageButton( (topLeftX + 72 * 4) , (topLeftY + 111 * 4), 22 * 4, 11 * 4, Assets.btn_sell, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().getGameState());
			}}));
		
		//BuyButton
		uiManager.addObject(new UIImageButton( (topLeftX + 174 * 4), (topLeftY + 111 * 4), 22 * 4, 11 * 4, Assets.btn_buy, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				//SettingState settingState = new SettingState(handler);
				State.setState(handler.getGame().settingState);
			}}));
		
		uiManager.addObject(new UIImageButton(9000, 9000, 200, 50, Assets.btn_blank, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(0);
			}}));
		
	}

	

	public void tick() {
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			handler.getWorld().getEntityManager().getPlayer().getInventory().setActive(false);
			State.setState(handler.getGame().gameState);
		}
		
		uiManager.tick();
		inventory.tick();
		
	}



	@Override
	public void render(Graphics g) {
		
		//Base Inventory Screen
		g.drawImage(Assets.vendorScreen, centerScreenX - (Assets.vendorScreen.getWidth() * 4 / 2), centerScreenY - (Assets.vendorScreen.getHeight() * 4 / 2), Assets.vendorScreen.getWidth() * 4, Assets.vendorScreen.getHeight() * 4, null);
		
		//UI Buttons
		uiManager.render(g);
	
		
		inventory.render(g);
		
		for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryAttack().size(); i++) {
			g.drawImage(handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryAttack().get(i).texture, 20 * i, 20 * i, 40, 40, null);
		}
		
		
	}

}
