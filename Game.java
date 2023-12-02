import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

class Game {
  public final static SkillDict dict = new SkillDict();
  public final static int width = 800, height = 450;
  private JFrame frame = new JFrame("Game");
  private String[] images = { "images/Start.png", "images/Settings.png", "images/Tutorial.png" };
  private Integer randomNumber = 3;
  private static User user = new User();
  private String[] mobList = { "slime" };
  private int[] mobLevels = { 1 };
  private Random rand = new Random();
  private static double userMaxHP = Game.user.getStats()[0];

  // Call to start the program
  Game() {
    frame.setSize(width, height);
    frame.setVisible(true);
    // setMenu();
    testEvent();
  }

  // Call this to test a battle between test user and a slime
  private void testSlimeBattle() {
    Game.user = new User();
    Slime slime2 = new Slime();
    frame.setContentPane(new Battle(Game.user, slime2));

  }

  private void testEvent() {
    Game.user = new User();
    LevelUp event = new LevelUp();
    frame.setContentPane(new Encounter(Game.user, event));
  }

  public static void setScreen(Integer choice) {

  }

  private void StartGame() {
    ArrayList<Entity> wave = getWave();
    while (Game.user.getStats()[0] > 0) {
      for (int i = 0; i < wave.size(); i++) {
        Entity curEntity = wave.get(i);
        if (curEntity.getMob()) {
          while (curEntity.getStats()[0] > 0 && Game.user.getStats()[0] > 0) {
            frame.setContentPane(new Battle(Game.user, curEntity));
          }
          if (Game.user.getStats()[0] <= 0) {
            break;
          }
        }
      }
    }
  }

  public static void updateHP() {
    userMaxHP = user.getStats()[0];
  }

  private ArrayList<Entity> getWave() {
    ArrayList<Entity> wave = new ArrayList<Entity>();
    double maxValue = user.getStats()[2] * 2;
    int curValue = 0;
    int tempValue = mobLevels.length;
    while (curValue < maxValue) {
      while (!checkEnemey(tempValue)) {
        tempValue = rand.nextInt(mobLevels.length);
      }
      curValue = curValue + tempValue;
      wave.add(getEntity(mobList[tempValue]));
    }
    wave.add(new LevelUp());
    return wave;
  }

  private boolean checkEnemey(int index) {
    if (index >= mobLevels.length) {
      return false;
    } else if (user.getStats()[2] < mobLevels[1]) {
      return true;
    } else {
      return false;
    }
  }

  private Entity getEntity(String entityName) {
    if (entityName.equals("slime")) {
      return new Slime();
    } else {
      return null;
    }
  }

  private void setMenu() {
    frame.setContentPane(new Menu(images, Game::setScreen));
    frame.setSize(width, height);
    frame.setVisible(true);
  }

  // Main method
  public static void main(String[] args) {
    System.out.println("Project has started");
    new Game();
  }
}