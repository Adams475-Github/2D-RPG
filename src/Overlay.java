import java.awt.Graphics;

public class Overlay {

	private Handler handler;
	private boolean active;

	public Overlay(Handler handler) {
		
		this.handler = handler;
		active = false;
	}
	
	public void tick() {
		if(!handler.getWorld().getEntityManager().getPlayer().getEscapeMenu().isActive() && !handler.getWorld().getEntityManager().getPlayer().getInventory().isActive()) {
			active = true;
		} else {
			active = false;
		}
		
	}
	
	public void render(Graphics g) {
		
		if(!active) {
			return;
		}
		
		if(handler.getWorld().getEntityManager().getPlayer().health == 12) {
			g.drawImage(Assets.heartFull, 20, 30, 48, 44, null);
		} else if(handler.getWorld().getEntityManager().getPlayer().health == 11) {
			g.drawImage(Assets.heartThreeFourths, 20, 30, 48, 44, null);
		}
		
	}
	
}
