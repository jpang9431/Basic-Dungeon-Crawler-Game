abstract class Entity {
  private String name = "";
  private boolean isMob = true;
  private String image = "";
  protected int choice = 1;

  // Root abstract class
  Entity(String name, boolean isMob, String image) {
    this.name = name;
    this.isMob = isMob;
    this.image = image;
  }

  // Called to get the action of an entity based on the other entity
  abstract void action(Entity otherEntity);

  // Called to geth the name of this entity
  public String getName() {
    return name;
  }

  // Called to see if this entity is a mob
  public boolean getMob() {
    return isMob;
  }

  // Called to get the string path to the image of this mob
  public String getImage() {
    return image;
  }

  // Stuff so we can use entity for interactions instead of mob see mob for more
  // details
  public double[] getResistances() {
    return null;
  }

  public void dam(double damage) {
    return;
  };

  public double[] getStats() {
    return null;
  }

  public void setStats(double[] inStats) {
    return;
  }

  public Skill[] getSkills() {
    return null;
  }

  public String[] getSkillNames() {
    return null;
  }

  public void setChoice(int choice) {
    this.choice = choice;
  }
}