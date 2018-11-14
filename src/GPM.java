
public class GPM {
	//Stands for game progression manager
	
	private String gameStage = "tutorial";
	private Handler handler;
	
	public GPM(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().contains(Item.blueSword)) {
			gameStage = "sword stage";
		} else if(!handler.getWorld().getEntityManager().getPlayer().getCompletedQuests().isEmpty()) {
			gameStage = "post quest stage";
		}  
	}

	public String getGameStage() {
		return gameStage;
	}

	public void setGameStage(String gameStage) {
		this.gameStage = gameStage;
	}
	
	

}
