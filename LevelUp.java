class LevelUp extends Event{
	//{"Health", "Shield", "Level", "Strength", "Magic"};
	//{"Magic","Blunt","Sharp"};
	private static String[] choices = {"Increase health", "Increase strength", "increase magic"};
	private static double[] odds = {100.0,100.0,100.0};
	LevelUp() {
		super("levelUp", "images/LevelUp.png", "You leveled up", null, choices, odds, Skill.Type.NORMAL);
	}
	
	public void action(Entity otherEntity) {
		double[] stats = otherEntity.getStats();
		if (choice==0) {
			stats[0] = stats[0]+2;
		} else if (choice==1) {
			stats[1]++;
		} else if (choice==2) {
			stats[2]++;
		}
	}
	
	

}
