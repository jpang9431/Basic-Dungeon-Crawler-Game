import java.util.Random;

class RandomStatDown extends Event {
  private static String[] choices = { "Confirm" };
  private static double[] odds = { 100.0 };
  private int choice = 0;
  private Random rand = new Random();

  RandomStatDown() {
    super("Random stat down", "images/Image.jpg", "One of your stats was decreased", null, choices, odds,
        Skill.Type.NORMAL);
  }

  public void action(Entity user) {
    double[] stats = user.getStats();
    choice = rand.nextInt(3);
    if (choice == 0) {
      Game.updateHP(-1);
      stats[0] = stats[0] - 1;
    } else if (choice == 1) {
      stats[3]--;
    } else if (choice == 2) {
      stats[4]--;
    }
    user.setStats(stats);
  }
}