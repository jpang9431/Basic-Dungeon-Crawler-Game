import java.util.Random;

abstract class Mob extends Entity {
  private Skill[] skills = { null, null, null, null, null };
  protected Random rand = new Random();
  private String[] skillNames = { null, null, null, null, null };
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  protected double[] stats = { 0, 0, 0, 0, 0 };
  protected double favor = 0.0;
  // {"Magic","Blunt","Sharp"};
  private double[] resistances = { 0, 0, 0 };
  private double maxHP = 0.0;

  // Use to create a mob entity
  Mob(String[] skillNames, double[] resistances, String name, String image) {
    super(name, true, image);
    this.skillNames = skillNames;
    double multi = Game.getMulti();
    for (int i = 0; i < skillNames.length; i++) {
      if (!(skillNames == null)) {
        skills[i] = Game.dict.getSkill(this.skillNames[i]);
      }
    }
    this.resistances = resistances;
  }

  protected void multi() {
    double multi = Game.getMulti();
    for (int i = 0; i < stats.length; i++) {
      stats[i] = stats[i] * multi;
    }
  }

  // Returns the number of actual skills this mob has
  public int getNumSkills() {
    int nonNull = 0;
    for (int i = 0; i < skills.length; i++) {
      if (skills[i] != null) {
        nonNull++;
      }
    }
    return nonNull;
  }

  // Overrides entity action, called to attack another eneitty defaults to index 1
  // skill
  public void action(Entity otherEntity) {
    int index = skillChoice(otherEntity);
		Game.updateText(this.getName() + " uses "+ this.getSkillNames()[index]);
    Skill skill = skills[index];
    Skill passive = skills[0];
    double multiplier = 1.0;
    if (skill !=null && skill.getOffense() && skill.getPassive()){
      Game.updateText(this.getName() + " uses its offensive passive abilitiy "+ this.getSkillNames()[index]);
      favor = passive.getDam(this.getResistances(), favor, true);
    }
    if (favor>1.0){
      multiplier = favor;
      favor = 0.0;
    }
    if (skill.getOffense()) {
      double stat = 1;
      if (skill.getType().equals(Skill.Type.MAGIC)) {
        stat = stats[4];
      } else {
        stat = stats[3];
      }
      otherEntity.dam(multiplier * stat * skill.getDam(otherEntity.getResistances(), favor, true));
      if (skill.getHurtSelf()) {
        this.dam(stat * skill.getDam(this.getResistances(), favor, false));
      }
    } else if (!skill.getOffense()) {
      this.stats[1] = skill.getDam(this.getResistances(), favor, true);
    }
  }

  // Override this to have the mob choose a specific skill to attack with
  abstract int skillChoice(Entity curEntity);

  // Called when this mob takes damage, takes a double, checks to trigger any
  // defensive passive
  // and damages mob based on damage and shield
  public void dam(double damage) {
		
    Skill skill = skills[0];
    if (skill != null && !skill.getOffense() && skill.getPassive()) {
      this.stats[1] = skill.getDam(this.getResistances(), favor, true);
      if (skill.getHurtSelf()){
        this.stats[0] = this.stats[0] - skill.getDam(this.getResistances(), favor, false);
      }
    }
    if (this.stats[1] > 1) {
      damage = damage - this.stats[1];
    } else if (this.stats[1] > 0) {
      damage = damage * this.stats[1];
    }
    if (damage > 0) {
      this.stats[0] = this.stats[0] - damage;
    } else {
      damage = 0;
    }
    Game.updateText(this.getName() + " was damaged for "+ rounding(damage) + " damage");
    this.stats[1] = 0;
  }

  public double rounding(double num) {
    num = num * 10;
    num = Math.round(num);
    num = num / 10;
    return num;
  }

  // Return array of resistances to damages {"Magic","Blunt","Sharp"};
  public double[] getResistances() {
    return resistances;
  }

  // Get the name of this mob
  public String getName() {
    return super.getName();
  }

  // Get the stats of this mob {"Health", "Shield", "Level", "Strength", "Magic"};
  public double[] getStats() {
    return stats;
  }

  // Resets the shield stat
  public void resetShield() {
    this.stats[1] = 0.0;
  }

  // Used to ste the stats of this mob {"Health", "Shield", "Level", "Strength",
  // "Magic"};
  public void setStats(double[] stats) {
    this.stats = stats;
  }

  // Get the skill instaces of this mob
  public Skill[] getSkills() {
    return skills;
  }

  // Get the names of the skills of this
  public String[] getSkillNames() {
    return skillNames;
  }
}