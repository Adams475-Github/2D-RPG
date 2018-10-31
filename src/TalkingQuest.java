
public class TalkingQuest extends Quest {

	private Entity creature;
	private Item reward;
	private String description;
	private String type;
	
	public TalkingQuest(Handler handler, String title, String description, Entity creature, Item reward, String type, Player player) {
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
		if(Math.abs(player.x - creature.x) < 20 && Math.abs(player.y - creature.y) < 20) {
			System.out.println("quest complete");
			completeQuest();
		}
		
	}

	
	
	

	
	
	

}
