import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.*;
import java.io.File;
import java.awt.Point;
import java.util.ArrayList;
class Game {
  public final static SkillDict dict = new SkillDict();
  public final static int width = 800, height = 450;
  private JFrame frame = new JFrame("Game");
  private String[] images = { "images/Start.png", "images/Settings.png", "images/Tutorial.png" };
  private static String[] endImages = {"images/Start.png", "images/Image.jpg"};
  private User user = new User();
  private String[] mobList = { "slime", "magicSlime", "lavaTurtle" };
  private int[] mobLevels = { 1, 2, 10 };
  private Random rand = new Random();
  private ArrayList<Entity> wave = new ArrayList<Entity>();
  private int index = 0;
  private static Game game = null;
  private File alive = new File("images/ALIVE.png");
  private StatPanel statPanel = new StatPanel();
  private double userMaxHP;
  private static double multi = 1; 
  private static ArrayList<String> text = new ArrayList<String>();
  private static int pointer = 0;
  private static boolean isUserStatShow = false;
  private static ShowStat userStats;
  // Call to start the program
  Game() {
    frame.setSize(width, height);
    frame.setContentPane(new Menu(images, Game::start));
    frame.setVisible(true);
    statPanel.setBounds(width-20,0,20,20);
    try{
      frame.setCursor(frame.getToolkit().createCustomCursor(ImageIO.read(alive), new Point(), ""));
    } catch(Exception e){
      e.printStackTrace();
    }
    game = this;
    userMaxHP =  Game.getUser().getStats()[0];
    // next();
  }

  public static void flipShow(){
    isUserStatShow = !isUserStatShow;
    if (isUserStatShow){
      userStats = new ShowStat(Game.getUser());
    } 
  }

  public static void setShow(boolean show){
    isUserStatShow = show;
    if (isUserStatShow){
      userStats = new ShowStat(Game.getUser());
    } 
  }

  public static void upate(){
    if (isUserStatShow){
      userStats.update();
    }
  }
  
  public static void updateText(String inText){
    text.add(inText);
    pointer++;
  }

  public String[] upDown(boolean up){
    if (up){
      pointer--;
    } else {
      pointer++;
    }
    if (pointer < 1){
      pointer++;
    } else if (pointer>=text.size()){
      pointer--;
    }
    String[] returnText = new String[2];
    returnText[0] = text.get(pointer-1);
    returnText[1] = text.get(pointer);
    return returnText;
  }

  public static double getMulti(){
    return multi;
  }

  public static User getUser(){
    return game.nonStaticGetUser();
  }

  public User nonStaticGetUser(){
    return user;
  }

  public static double getMaxHP(){
    return game.max();
  }

  public double max(){
    return userMaxHP;
  }

  public static void end(){
    game.frame.setContentPane(new Menu(endImages, Game::end));
    game.frame.revalidate();
    game.frame.repaint();
  }
  
  public static void end(Integer choice) {
    if (choice == 0){
      game.frame.setVisible(false);
      game.frame.dispose();
      new Game();
    }
  }

  public static void start(Integer choice) {
    if (choice == 0) {
      next();
    }
  }

  public static void next() {
    game.nonStaticNext();
  }

  public void nonStaticNext() {
		if(statPanel!=null){
			statPanel.updateText();
		}
		
    index++;
    if (isUserStatShow){
      userStats.update();
    }
    if (index >= wave.size()) {
      index = 0;
      double[] stats = user.getStats();
      stats[0] = userMaxHP;
      user.setStats(stats);
      getWave();
    }
    Entity curEntity = wave.get(index);
    if (curEntity.getMob()) {
      JPanel panel = new Battle(user, curEntity, index+1, wave.size()-index-1);
      panel.add(statPanel);
      frame.setContentPane(panel);
    } else if (!curEntity.getMob()) {
      JPanel panel = new Encounter(user, curEntity);
      panel.add(statPanel);
      frame.setContentPane(panel);
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
    boolean hadStatChange = false;
    int goodBad = rand.nextInt(2);
    Entity statChange = null;
    if (goodBad==0){
      statChange = new RandomStat();
    } else if (goodBad==1){
      statChange = new RandomStatDown();
    }
    while (curValue < maxValue) {
      if (!hadStatChange){
        int randomValue = rand.nextInt(5);
        if (randomValue==0){
          wave.add(statChange);
          hadStatChange = true;
        }
      }
      while (checkEnemey(tempValue)) {
        tempValue = rand.nextInt(mobLevels.length);
      }
      curValue = curValue + mobLevels[tempValue];
      wave.add(getEntity(mobList[tempValue]));
      tempValue = mobLevels.length;
    }
    if (!hadStatChange){
      wave.add(statChange);
    }
    if (maxValue==10){
      wave.add(getEntity(mobList[2]));
    }
    wave.add(new LevelUp());
  }

  private boolean checkEnemey(int index) {
    if (index >= mobLevels.length) {
      return true;
    } else if (user.getStats()[2] >= mobLevels[index]) {
      return false;
    } else {
      return true;
    }
  }

  private Entity getEntity(String entityName) {
    if (entityName.equals("slime")) {
      return new Slime();
    } else if (entityName.equals("magicSlime")) {
      return new MagicSlime();
    } else if (entityName.equals("lavaTurtle")){
      return new LavaTurtle();
    } else {
      return null;
    }
  }

  // Main method
  public static void main(String[] args) {
    System.out.println("Project has started");
    new Game();
  }
}