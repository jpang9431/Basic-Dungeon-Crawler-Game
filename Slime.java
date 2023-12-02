class Slime extends Mob {
  // {"Health", "Shield", "Level", "Strength", "Magic"};
  // {"Magic","Blunt","Sharp"};
  private static String[] names = { "Slime Body", "Tackle" };
  private static double[] stats = { 5.0, 0.0, 1.0, 1.0, 0.0 };
  private static double[] resist = { 0.0, 1.5, 0.5 };

  Slime() {
    super(names, stats, resist, "Slime", "images/Slime.png");
  }

  public int skillChoice(Entity curEntity) {
    return 1;
  }

  public void action(Entity otherEntity) {
    super.action(otherEntity);
  }

}