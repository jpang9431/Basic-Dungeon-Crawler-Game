import java.util.Random;

class MagicSlime extends Mob {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] names = { "Slime Body", "Magic Ball", "Tackle" };
  private static double[] resist = { 1.5, 1.5, 0.5 };
  private Random rand = new Random();

  MagicSlime() {
    super(names, resist, "Magic Slime", "images/Slime.png");
    double[] tempStats = { 5.0, 0.0, 2.0, 1.0, 0.7 };
    stats = tempStats;
    multi();
  }

  public int skillChoice(Entity curEntity) {
    return rand.nextInt(2)+1;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }
}