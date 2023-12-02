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
  private double userMaxHP = Game.user.getStats()[0];
  private ArrayList<Entity> wave = new ArrayList<Entity>();
  private int index = 0;
  private static Game game = null;

  // Call to start the program
  Game() {
    frame.setSize(width, height);
    frame.setVisible(true);
    game = this;
    next();
  }

  public static void end() {
    System.out.println("end");
  }

  public static void start(Integer choice) {
    System.out.println(choice);
  }

  public static void next() {
    game.nonStaticNext();
  }

  public void nonStaticNext() {
    index++;
    if (index >= wave.size()) {
      index = 0;
      double[] stats = user.getStats();
      stats[0] = userMaxHP;
      user.setStats(stats);
      getWave();
    }
    Entity curEntity = wave.get(index);
    if (curEntity.getMob()) {
      frame.setContentPane(new Battle(user, curEntity, index + 1));
    } else if (!curEntity.getMob()) {
      frame.setContentPane(new Encounter(user, curEntity));
    }
    frame.revalidate();
    frame.repaint();
  }

  public void printEntities() {
    for (int i = 0; i < wave.size(); i++) {
      System.out.println(wave.get(i).getName());
    }
  }

  public static void updateHP(int change) {
    game.nonStaticUpdateHP(change);
  }

  public void nonStaticUpdateHP(int change) {
    userMaxHP = userMaxHP + change;
  }

  private void getWave() {
    wave.clear();
    double maxValue = user.getStats()[2];
    int curValue = 0;
    int tempValue = mobLevels.length;
    while (curValue < maxValue) {
      // Infite loop lol
      while (checkEnemey(tempValue)) {
        tempValue = rand.nextInt(mobLevels.length);
      }
      curValue = curValue + tempValue;
      wave.add(getEntity(mobList[tempValue - 1]));
    }
    wave.add(new LevelUp());
  }

  private boolean checkEnemey(int index) {
    if (index >= mobLevels.length) {
      return false;
    } else if (user.getStats()[2] < mobLevels[index]) {
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
    frame.setContentPane(new Menu(images, Game::start));
    frame.setSize(width, height);
    frame.setVisible(true);
  }

  // Main method
  public static void main(String[] args) {
    System.out.println("Project has started");
    new Game();
  }
}