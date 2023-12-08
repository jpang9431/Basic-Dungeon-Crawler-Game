import java.util.Random;
class RandomStat extends Event {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] choices = {"Confirm"};
  private static double[] odds = {100.0};
  private int choice = 0;
  private Random rand = new Random();
  RandomStat() {
    super("Random stat up", "images/Image.jpg", "One of your stats was increased", null, choices, odds, Skill.Type.NORMAL);
  }

  public void action(Entity user) {
    double[] stats = user.getStats();
    choice = rand.nextInt(3);
    if (choice == 0) {
      Game.updateHP(0.5);
      stats[0] = stats[0]+0.5;
    } else if (choice == 1) {
      stats[3] = stats[3]+0.5;
    } else if (choice == 2) {
      stats[4] = stats[4]+0.5;
    }
    user.setStats(stats);
  }
}
