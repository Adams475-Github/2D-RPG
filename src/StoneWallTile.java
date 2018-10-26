import java.awt.image.BufferedImage;

public class StoneWallTile extends Tile {

	public StoneWallTile(BufferedImage texture, int id) {
		super(texture, id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}


}
