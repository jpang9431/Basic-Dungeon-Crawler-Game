class User extends Mob {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] names = { null, "Magic Ball", "Shield Bash", "Sword Slash", "Block" };
  private static double[] resist = { 0.0, 0.0, 0.0 };

  User() {
    super(names, resist, "User", "images/User.png");
    double[] tempStats = { 5.0, 0.0, 1.0, 1.0, 1.0 };
    stats = tempStats;
  }

  public int skillChoice(Entity curEntity) {
    return choice;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }

}