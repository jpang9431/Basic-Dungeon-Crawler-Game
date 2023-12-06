class Slime extends Mob {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] names = { "Slime Body", "Tackle" };
  private static double[] resist = { 0.0, 1.5, 0.5 };

  Slime() {
    super(names, resist, "Slime", "images/Slime.png");
    double[] tempStats = { 5.0, 0.0, 1.0, 1.0, 0.0 };
    stats = tempStats;
    multi();
  }

  public int skillChoice(Entity curEntity) {
    return 1;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }

}