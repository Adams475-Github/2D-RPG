import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {
	
	public static final Font highTower = new Font("High Tower Text", Font.BOLD, 28);
	public static final Font highTowerSmall = new Font("High Tower Text", 0, 22);
	public static final Font highTowernBold = new Font("High Tower Text", 0, 28);
	
	
	
	public static Font loadFont(String path, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
