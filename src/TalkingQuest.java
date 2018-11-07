import java.awt.Rectangle;

public class TalkingQuest extends Quest {

	private Creature creature;
	private Item reward;
	private String description;
	private String type;
	
	public TalkingQuest(Handler handler, String title, String description, Creature creature, Item reward, String type, Player player) {
		super(handler, title, description, type, player);
		this.creature = creature;
		this.reward = reward;
		System.out.println(creature);
	}

	@Override
	public void completeQuest() {
		System.out.println("removing " + title);
		player.removeQuest(this);
		
		giveReward();
	}

	@Override
	public void giveReward() {
		player.getInventory().addItem(reward);
		
	}

	@Override
	public void checkCompleted() {
		
		if(creature.isTalkedTo) {
			System.out.println("quest complete");
			player.setDisplayBox(reward, this);
			completeQuest();
		}
		
		
		
	}

}
