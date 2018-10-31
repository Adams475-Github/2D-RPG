import java.awt.image.BufferedImage;

public class FloorTile extends Tile {

	public FloorTile(BufferedImage texture, int id) {
		super(texture, id);
		
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

}