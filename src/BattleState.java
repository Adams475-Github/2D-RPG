import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BattleState extends State {
	
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
