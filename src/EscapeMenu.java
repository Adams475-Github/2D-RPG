import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class EscapeMenu implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2124627859290612395L;
	private int pX = 275, pY = 125, pW = 512, pH = 512;
	Rectangle saveBounds = new Rectangle(pX + 512/2 - 278/2, pY + 130, 278, 54);
	Rectangle settingsBounds = new Rectangle(pX + 512/2 - 278/2, pY + 230, 278, 54);
	Rectangle quitBounds = new Rectangle(pX + 512/2 - 278/2, pY + 330, 278, 54);
	private boolean active = false;
	
	private transient Handler handler;

	public EscapeMenu(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && !handler.getWorld().getEntityManager().getPlayer().getInventory().isActive()) {
			active = !active;
		}
		
		if(!active) {
			return;
		}
		
		if(saveBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()) && handler.getMouseManager().isLeftPressed()) {
			//Fill code here
		} 
		
		if(settingsBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()) && handler.getMouseManager().isLeftPressed()) {
			State.setState(handler.getGame().settingState);
		} 
		
		if(quitBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()) && handler.getMouseManager().isLeftPressed()) {
			SaveGame s = new SaveGame(handler);
			s.saveWorlds();
			System.exit(0);
		} 
	}
	
	public void render(Graphics g) {
		if(!active || handler.getWorld().getEntityManager().getPlayer().getInventory().isActive()) {
			return;
		}
		
		g.drawImage(Assets.parchment, pX, pY, pW, pH, null);
		if(saveBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
			g.drawImage(Assets.btn_blank[1], pX + 512/2 - 278/2, pY + 130, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.BLACK);
			g.drawString("Save", pX + 512/2 - 278/2 + 110, pY + 130 + 33);
		} else {
			g.drawImage(Assets.btn_blank[0], pX + 512/2 - 278/2, pY + 130, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.WHITE);
			g.drawString("Save", pX + 512/2 - 278/2 + 110, pY + 130 + 33);
		}
		
		if(settingsBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
			g.drawImage(Assets.btn_blank[1], pX + 512/2 - 278/2, pY + 230, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.BLACK);
			g.drawString("Settings", pX + 512/2 - 278/2 + 90, pY + 230 + 33);
		} else {
			g.drawImage(Assets.btn_blank[0], pX + 512/2 - 278/2, pY + 230, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.WHITE);
			g.drawString("Settings", pX + 512/2 - 278/2 + 90, pY + 230 + 33);
		}
		
		if(quitBounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
			g.drawImage(Assets.btn_blank[1], pX + 512/2 - 278/2, pY + 330, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.BLACK);
			g.drawString("Quit", pX + 512/2 - 278/2 + 110, pY + 330 + 33);
		} else {
			g.drawImage(Assets.btn_blank[0], pX + 512/2 - 278/2, pY + 330, 278, 54, null);
			g.setFont(Assets.font28);
			g.setColor(Color.WHITE);
			g.drawString("Quit", pX + 512/2 - 278/2 + 110, pY + 330 + 33);
		}
		
		
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean b) {
		active = b;
	}

}
