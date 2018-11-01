import java.awt.Graphics;

public class QuestDisplayState extends State {

	private Quest quest;

	public QuestDisplayState(Handler handler, Quest quest) {
		super(handler);
		this.quest = quest;
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		g.drawString(quest.title, 0, 20);
		g.drawString(quest.description, 0, 40);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
