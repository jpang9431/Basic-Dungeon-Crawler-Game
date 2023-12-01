import javax.swing.*;

class Game {
  public final static SkillDict dict = new SkillDict();
  public final static int width = 800, height = 450;
  private JFrame frame = new JFrame("Game");

  // Call to start the program
  Game() {
    testSlimeBattle();
  }

  // Call this to test a battle between test user and a slime
  private void testSlimeBattle() {
    TestUser user = new TestUser();
    Slime slime2 = new Slime();
    frame.setContentPane(new Battle(user, slime2));
    frame.setSize(width, height);
    frame.setVisible(true);
  }

  // Main method
  public static void main(String[] args) {
    System.out.println("Project has started");
    new Game();
  }
}