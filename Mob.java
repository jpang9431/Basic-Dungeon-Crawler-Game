import java.util.Random;
abstract class Mob extends Entity{
	private Skill[] skills = {null, null, null, null, null};
	protected Random rand = new Random();
	private String[] skillNames = {null, null, null, null, null};
	//{"Health", "Shield", "Level", "Strength", "Magic"};
	private double[] stats = {0,0,0,0,0};
	private double favor = 0.0;
	//{"Magic","Blunt","Sharp"};
	private double[] resistances = {0,0,0};

	Mob(String[] skillNames, double[] stats, double[] resistances, String name, String image){
		super(name, true, image);
		this.skillNames = skillNames;
		this.stats = stats;
		for (int i=0; i<skillNames.length; i++) {
			if (!skillNames.equals(null)) {
				skills[i] = Game.dict.getSkill(this.skillNames[i]);
			}
		}
		this.resistances = resistances;
	}

	public void action(Entity otherEntity){
		int index = skillChoice(otherEntity);
		Skill skill = skills[index];
		if (skill.getOffense()) {
			double stat = 0;
			if (skill.getType().equals(Skill.Type.MAGIC)) {
				stat = stats[4];
			} else {
				stat = stats[3];
			}
			otherEntity.dam(stat*skill.getDam(otherEntity.getResistances(), favor, true));
			if (skill.getHurtSelf()) {
				this.dam(stat*skill.getDam(this.getResistances(), 0.0, false));
			}
		} else if (!skill.getOffense()) {
			this.stats[1] = skill.getDam(this.getResistances(), 0.0, false);
		}
	}

	abstract int skillChoice(Entity curEntity);

	public void dam(double damage) {
		Skill skill = skills[0];
		if (!skill.getOffense()&&skill.getPassive()) {
			this.stats[1] = skill.getDam(this.getResistances(), 0.0, true);
		}
		this.stats[0] = damage-this.stats[1];
		this.stats[1] = 0;
	}

	public double[] getResistances() {
		return resistances;
	}

	public String getName() {
		return super.getName();
	}

	public double[] getStats(){
		return stats;
	}

	public void resetShield() {
		this.stats[1] = 0.0;
	}

	public void setStats(double[] stats) {
		this.stats = stats;
	}

	public String[] getnames() {
		return skillNames;
	}

}