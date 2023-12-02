class LevelUp extends Event {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] choices = { "Increase health", "Increase strength", "increase magic" };
  private static double[] odds = { 100.0, 100.0, 100.0 };
  private int choice = 0;

  LevelUp() {
    super("leveled up", "images/Image.jpg", "You leveled up", null, choices, odds, Skill.Type.NORMAL);
  }

  public void action(Entity user) {
    double[] stats = user.getStats();
    if (choice == 0) {
      Game.updateHP(2);
    } else if (choice == 1) {
      stats[3]++;
    } else if (choice == 2) {
      stats[4]++;
    }
    stats[2]++;
    user.setStats(stats);
  }

  public void setChoice(int choice) {
    this.choice = choice;
  }
}
