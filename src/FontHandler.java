import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class FontHandler {
	//Space is 9 pixels long
	
	/** draw.
	 * @param rect The Rectangle
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String toe to center the text in.
	 */
	public static void drawFont(Graphics g, String text, Rectangle rect, Font font) {
//	    // Get the FontMetrics
//	    FontMetrics metrics = g.getFontMetrics(font);
//	    // Determine the X coordinate for the text
//	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
//	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
//	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
//	    //Set Font
//	    g.setFont(font);
//	    // Draw the String
//	    g.drawRect(rect.x, rect.y, rect.width, rect.height);
//	    g.drawString(text, x, y);
//		
//		int spaceWidth = 9;
//		
//		String words[] = text.split(" ");
//		
//		int spaceCount = 0;
//		for(int i = 0; i < text.length(); i++) {
//			if(text.charAt(i) == ' ') {
//				spaceCount++;
//			}
//		}
//		
//		int wrapAmount = 0;
//		FontMetrics metrics = g.getFontMetrics(font);
//	
//		if(metrics.stringWidth(text) > rect.width) {
//			wrapAmount = (int) Math.ceil((double)metrics.stringWidth(text) / (double)rect.width);
//			
//		}
//		
//		int x = (rect.x);
//		int y = (rect.y);
//		
//		if(wrapAmount > 0) {
//			
//			String lines[] = new String[wrapAmount];
//			
//			int previous = 0;
//			
//			for(int i = 0; i < lines.length; i++) {
//				
//				String tempString = "";
//				int total = 0;
//				int q = 0;
//				
//				while(total < rect.width) {
//
//					total += metrics.stringWidth(words[q + previous] + spaceWidth);
//					q++;
//					
//					
//				}
//				
//				for(int z = 0; z < q; z++) {
//					tempString = tempString + words[z + previous] + " ";
//				}
//				
//				previous = q + previous;
//				
//				lines[i] = (tempString);
//			}
//			
//			for(int i = 0; i < lines.length; i++) {
//				g.drawString(lines[i], x, y + 20 * i + metrics.getAscent());
//			}
//			
//		}
//		
//		
//		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		
//		while(running) {
//			
//			System.out.println(tempW);
//			tempW = tempW + (words.get(z) + " ");
//			z++;
//			
//			if(z == lines.length - 1) {
//				
//				break;
//			}
//			
//			if(metrics.stringWidth(tempW + words.get(z) + " ") > rect.width) {
//				running = false;
//			}
//			
//			
//			
//			
//			
//			
//		}
		
		FontMetrics metrics = g.getFontMetrics(font);
		ArrayList<String> words = new ArrayList<String>();
		String temp[] = text.split(" ");
		
		for(int i = 0; i < temp.length; i++) {
			words.add(temp[i]);
		}
		
		//lance was here
	    int wrapAmount = 0;
		
		if(metrics.stringWidth(text) > rect.width) {
			wrapAmount = (int) Math.ceil((double)metrics.stringWidth(text) / (double)rect.width);	
		}
		
		int newStarting = 0;
		
		String lines[] = new String[wrapAmount];
		
		for(int i = 0; i < lines.length; i++) {
			
			String tempW = "";
			
			for(int z = 0; z < words.size(); z++) {
				
				if(z + newStarting < words.size() && metrics.stringWidth(tempW + words.get(z + newStarting)) < rect.width) {
					
					tempW = tempW + words.get(z + newStarting) + " ";
					
				} else {
					newStarting = z + newStarting;
					break;
				}
				
				
			}
			
			lines[i] = tempW;
			
		}
		for(int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], rect.x, ( rect.y + 20 * i + metrics.getAscent() ) );
		}
		
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		
	}

}
