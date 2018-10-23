import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class FontHandler {
	/** draw.
	 * @param rect The Rectangle
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String toe to center the text in.
	 */
	public static void drawFont(Graphics g, String text, Rectangle rect, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		ArrayList<String> words = new ArrayList<String>();
		String temp[] = text.split(" ");
		
		for(int i = 0; i < temp.length; i++) {
			words.add(temp[i]);
		}
		
		//lance was here
	    int wrapAmount = 0;
		
		if(metrics.stringWidth(text) > rect.width) {
			wrapAmount = (int) Math.ceil( (double) metrics.stringWidth(text) / (double) rect.width );	
		}
		
		int newStarting = 0;
		String lines[] = new String[wrapAmount];
		
		for(int i = 0; i < lines.length; i++) {
			String tempW = "";
			
			for(int z = 0; z < words.size(); z++) {
				if(z + newStarting < words.size() && metrics.stringWidth(tempW + words.get(z + newStarting)) < rect.width + 40) { 
					//the +40 is there because some words that should fit don't get added so this make the width a little bigger to let them sneak in.
					tempW = tempW + words.get(z + newStarting) + " ";
					
				} else {
					newStarting = z + newStarting;
					break;
					
				}
			}
			
			lines[i] = tempW;
			
		}
		for(int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], rect.x, ( rect.y + 24 * i + metrics.getAscent() ) );
		}
		
	}
}
