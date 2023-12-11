import java.util.Random;
class LavaTurtle extends Mob{
  private static String[] names = { "Healing Flames", "Tail Whip", "Fire Breath", "Lava Crack","Tackle" };
  private static double[] resist = { 0.0, 2.0, 2.0 };
  private int[] baseCooldown = {2,2,2,0};
  private int[] coolDown = {2,2,2,0};
  private Random rand = new Random();

  LavaTurtle() {
    super(names, resist, "Lava Turtle", "images/Turt.png");
    double[] tempStats = { 40.0, 0.0, 10.0, 5.0, 5.0 };
    stats = tempStats;
    multi();
  }

  public int skillChoice(Entity curEntity) {
    int choice = rand.nextInt(4)+1;
    for (int i=0; i<coolDown.length; i++){
      if (coolDown[i]>0){
        coolDown[i]--;
      }
    }
    while (coolDown[choice-1]!=0){
      choice = rand.nextInt(4)+1;
    }
    coolDown[choice-1] = baseCooldown[choice-1];
    return choice;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }
  
}