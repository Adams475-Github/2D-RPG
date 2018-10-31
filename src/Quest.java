
public abstract class Quest {
	
	protected String title;
	protected String description;
	protected String type;
	protected Player player;
	protected Handler handler;
	
	public Quest(Handler handler, String title, String description, String type, Player player) {
		
		this.title = title;
		this.description = description;
		this.type = type;
		this.player = player;
	}
	
	public abstract void checkCompleted();
	
	public abstract void completeQuest();
	
	public abstract void giveReward();
	
	

}