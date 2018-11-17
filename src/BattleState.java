import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class BattleState extends State implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9033691757489186046L;
	private Player player;
	private Creature enemy;
	private BufferedImage background;

	public BattleState(Handler handler, Player player, Creature enemy, BufferedImage background) {
		super(handler);
		
		this.player = player;
		this.enemy = enemy;
		this.background = background;
	}

	@Override
	public void tick() {
	
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Launcher.SCREEN_WIDTH, Launcher.SCREEN_HEIGHT, null);
		g.drawImage(Assets.player_up[1], 40, 40, null);
	}

	@Override
	public void init() {
		
		
	}

}
