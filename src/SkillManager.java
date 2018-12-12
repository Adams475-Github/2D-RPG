import java.util.ArrayList;

public class SkillManager {

	private Handler handler;
	private int skillPoints = 0;
	private ArrayList<Skill> skills = new ArrayList<Skill>();
	private SkillList s = new SkillList(handler);
	
	public SkillManager(Handler handler) {
		this.handler = handler;
		skills.add(s.melee1);
		
	}
	
	public void tick() {
		for(int i = 0; i < skills.size(); i++) {
			
			if(skills.get(i).type.equals("melee")) {
				handler.getWorld().getEntityManager().getPlayer().setSkillAttack(skills.get(i).damageInc);
			}
			
			if(skills.get(i).type.equals("crit")) {
				handler.getWorld().getEntityManager().getPlayer().setSkillCrit(skills.get(i).critInc);
			}
			
		}
	}
	
}
