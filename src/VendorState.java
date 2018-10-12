import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class VendorState extends State {

	private UIManager uiManager;
	private Inventory inventory;
	private int centerScreenX = 1024/2, centerScreenY = 768/2;
	private int topLeftX = centerScreenX - (Assets.vendorScreen.getWidth() * 4 / 2), topLeftY = centerScreenY - (Assets.vendorScreen.getHeight() * 4 / 2);
	private Rectangle clickBounds = new Rectangle(centerScreenX, 280, 340, 190);
	private int highX = 0, highY = 0;
	
	public VendorState(Handler handler, Inventory inventory) {
		super(handler);
		this.inventory = inventory;
		inventory.init();
		inventory.setActive(true);
		handler.getWorld().getEntityManager().getPlayer().getInventory().setActive(true);
		inventory.setCurrentInv(inventory.getInventoryAttack());
		
		this.inventory.setyOffset(88);
		this.inventory.setxOffset(178);	
		
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
		
		uiManager.addObject(new UIImageButton( (centerScreenX + 368), (topLeftY + 156), 14 * 4, 18 * 4, Assets.btn_sword, new ClickListener() {

			@Override
			public void onClick() {
				inventory.setDisplay(0);
				inventory.setCurrentInv(inventory.getInventoryAttack());
			}}));
		
		uiManager.addObject(new UIImageButton( (centerScreenX + 368), (topLeftY + 156 + 18 * 4), 14 * 4, 18 * 4, Assets.btn_armor, new ClickListener() {

			@Override
			public void onClick() {
				inventory.setDisplay(1);
				inventory.setCurrentInv(inventory.getInventoryArmor());
			}}));
		
		uiManager.addObject(new UIImageButton( (centerScreenX + 368), (topLeftY + 156 + 18 * 4 + 18 * 4), 14 * 4, 18 * 4, Assets.btn_potions, new ClickListener() {

			@Override
			public void onClick() {
				inventory.setDisplay(2);
				inventory.setCurrentInv(inventory.getInventoryPotions());
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
		
		if(handler.getMouseManager().isLeftPressed()) {
			if(clickBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
				highX = (int) ( Math.floor( (handler.getMouseManager().getMouseX() - clickBounds.x) / (17 * 4) ) );
				highY = (int) ( Math.floor( (handler.getMouseManager().getMouseY() - clickBounds.y)  / (17 * 4) ) );
				
				System.out.println(handler.getMouseManager().getMouseX());
				System.out.println(highX + ", " + highY);
				
			}
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
	
		if(inventory.getDisplay() == 0) {
			g.drawImage(Assets.btn_sword[1], centerScreenX + 368, topLeftY + 156, 14 * 4, 18 * 4, null);
		} else if(inventory.getDisplay() == 1) {
			g.drawImage(Assets.btn_armor[1], centerScreenX + 368, topLeftY + 156 + 18 * 4, 14 * 4, 18 * 4, null);
		} else {
			g.drawImage(Assets.btn_potions[1], centerScreenX + 368, topLeftY + 156 + 18 * 8, 14 * 4, 18 * 4, null);
		}
		
		inventory.render(g);
		
		for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryAttack().size(); i++) {
			g.drawImage(handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryAttack().get(i).texture, 20 * i, 20 * i, 40, 40, null);
		}
		
		g.drawRect(clickBounds.x, clickBounds.y, clickBounds.width, clickBounds.height);
		
		g.drawImage(Assets.itemHighlighter, ( clickBounds.x + inventory.invSlotDist * highX + 4 ), ( clickBounds.y + inventory.invSlotDist * highY + 2) , 14*4, 14*4, null);
		
		
	}

}
