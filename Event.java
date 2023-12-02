import java.util.Random;

abstract class Event extends Entity {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private String sucess = "";
  private String fail = "";
  private String[] choices = null;
  private double[] odds = null;
  protected int choice = 0;
  private Random rand = new Random();
  private Skill.Type type = null;

  Event(String name, String image, String sucess, String fail, String[] choices, double[] odds, Skill.Type type) {
    super(name, false, image);
    this.sucess = sucess;
    this.fail = fail;
    this.choices = choices;
    this.type = type;
  }

  public String getSucess() {
    return sucess;
  }

  public String getfail() {
    return fail;
  }

  public String[] getChoices() {
    return choices;
  }

  public double[] getOdds() {
    return odds;
  }

  public Skill.Type getType() {
    return type;
  }

  public void setChoice(int choice) {
    this.choice = choice;
  }

  public boolean sucessFail(Entity user, int choice) {
    double random = rand.nextDouble() * 100;
    if (type.equals(Skill.Type.BLUNT)) {
      random = random * (user.getResistances()[1]);
    } else if (type.equals(Skill.Type.MAGIC)) {
      random = random * (user.getResistances()[0]);
    } else if (type.equals(Skill.Type.SHARP)) {
      random = random * (user.getResistances()[2]);
    }
    if (odds[choice] >= random) {
      return true;
    } else {
      return false;
    }
  }
}