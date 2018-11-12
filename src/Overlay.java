import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Overlay {

	private Handler handler;
	private boolean active;
	private ArrayList<BufferedImage> hearts = new ArrayList<BufferedImage>();
	private Rectangle coinRect = new Rectangle(924, 705, 200, 0);

	public Overlay(Handler handler) {
		this.handler = handler;
		active = false;
		
		for(int i = 0; i < 4; i++) {
			hearts.add(Assets.heartFull);
		}
	}
	
	public void tick() {
		checkHearts();
		checkActive();
		
	}
	
	private void checkHearts() {

		int heartDown = handler.getWorld().getEntityManager().getPlayer().getMaxHealth() - handler.getWorld().getEntityManager().getPlayer().health;
		
		for(int i = 0; heartDown > 0; i++) {
			
			if(i > 0) {
				hearts.remove(i - 1);
				hearts.add(i - 1, Assets.heartEmpty);
			}
			
			if(heartDown == 1) {
				hearts.remove(i);
				hearts.add(i, Assets.heartThreeFourths);
				
			} else if(heartDown == 2) {
				hearts.remove(i);
				hearts.add(i, Assets.heartHalf);
				
			} else if(heartDown == 3) {
				hearts.remove(i);
				hearts.add(i, Assets.heartFourth);
				
			} else if(heartDown == 4) {
				hearts.remove(i);
				hearts.add(i, Assets.heartEmpty);
				
			}
			
			heartDown -= 4;
		}
		
	}	
	
	private void checkActive() {
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
		
		for(int i = 0; i < hearts.size(); i++) {
			g.drawImage(hearts.get(i), 148 - 48 * i, 0, Assets.heartEmpty.getWidth() * 4, Assets.heartEmpty.getHeight() * 4, null);
		}		
		
		g.drawImage(Assets.expBar, 0, Launcher.SCREEN_HEIGHT - Assets.expBar.getHeight() * 4, Assets.expBar.getWidth() * 4, Assets.expBar.getHeight() * 4, null);
		g.drawImage(Assets.face, 28, Launcher.SCREEN_HEIGHT - 180, Assets.face.getWidth() * 4, Assets.face.getHeight() * 4, null);
		
		if(handler.getWorld().getEntityManager().getPlayer().getExp() > 0) {
			g.drawImage(Assets.expLeft, 88, 712, Assets.expLeft.getWidth() * 4, Assets.expLeft.getHeight() * 4, null);
			
			for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getExp() && handler.getWorld().getEntityManager().getPlayer().getExp() <= 50; i++) {
				g.drawImage(Assets.expMid, 96 + i * 4, 712, Assets.expMid.getWidth() * 4, Assets.expMid.getHeight() * 4, null);
			}
			
			if(handler.getWorld().getEntityManager().getPlayer().getExp() == 50) {
				g.drawImage(Assets.expRight, 296, 712, Assets.expRight.getWidth() * 4, Assets.expRight.getHeight() * 4, null);
			}
		}
		
		g.setColor(Color.yellow);
		g.drawString(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getExpL()), 47, 712);
		
		
		g.setColor(Color.BLACK);
		FontHandler.drawFont(g, Integer.toString(handler.getWorld().getEntityManager().getPlayer().getInventory().coins),
				coinRect, FontLoader.bigF2);
		
		g.setColor(Color.WHITE);
		g.drawImage(Assets.coin, 850, 710, Assets.coin.getWidth() * 4, Assets.coin.getHeight() * 4, null);
		FontHandler.drawFont(g, Integer.toString(handler.getWorld().getEntityManager().getPlayer().getInventory().coins),
				coinRect, FontLoader.bigF);
		
	}
	
}
