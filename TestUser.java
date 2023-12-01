class TestUser extends Mob {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] names = { null, "Magic Ball", "Shield Bash", "Sword Slash", "Block" };
  private static double[] stats = { 5.0, 0.0, 1.0, 1.0, 1.0 };
  private static double[] resist = { 0.0, 0.0, 0.0 };

  TestUser() {
    super(names, stats, resist, "TestUser", "images/User.png");
  }

  public int skillChoice(Entity curEntity) {
    return choice;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }

}