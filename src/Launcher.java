import java.io.Serializable;

public class Launcher implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7175885768341968821L;
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	
	public static void main(String[] args) {
		
		Game game = new Game("Place Holder", 1024, 768);
		game.start();
		
	}

}
