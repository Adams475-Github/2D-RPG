import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class RainDrop {
	
	int x;
	int y;
	int yspeed;
	int length;
	Random r;
	
	public RainDrop() {
		
		r = new Random();
		
		length = r.nextInt(5) + 5;
		x = r.nextInt(Launcher.SCREEN_WIDTH);
		y = -500 + r.nextInt(500);
		yspeed = r.nextInt(5) + 6;
		
	}
	
	public void tick() {
		
		y = y + yspeed;
		
		if(y > Launcher.SCREEN_HEIGHT) {
			y = -200 + r.nextInt(100);
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawLine(x, y, x, y + length);
		
	}

}
