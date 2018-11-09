
public class ItemQuest extends Quest{

	private Item item;
	private Item reward;

	public ItemQuest(Handler handler, String title, String description, Item item, Item reward, String type, Player player) {
		super(handler, title, description, type, player);
		this.item = item;
		this.reward = reward;
		
	}

	@Override
	public void checkCompleted() {
		if(player.getInventory().contains(item)) {
			completeQuest();
			player.setDisplayBox(reward, this);
		}
		
	}

	@Override
	public void completeQuest() {
		player.getCompletedQuests().add(this);
		player.removeQuest(this);
		giveReward();
		
	}

	@Override
	public void giveReward() {
		player.getInventory().addItem(reward);
		
		
	}

	

}
