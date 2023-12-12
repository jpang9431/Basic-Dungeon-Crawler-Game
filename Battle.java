import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Graphics;

import java.util.ArrayList;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

//Use to battle two entities togteher
class Battle extends JPanel implements ActionListener {
  private JButton[] choices = new JButton[4];
  private JLabel title = new JLabel("", JLabel.CENTER);
  private JButton up = new JButton(new ImageIcon("images/Up.png")),
      down = new JButton(new ImageIcon("images/Down.png"));
  private JLabel mobHP = new JLabel("", JLabel.CENTER), userHP = new JLabel("", JLabel.CENTER),
      turnDetail = new JLabel("");
  private JButton userImage, mobImage;
  private Entity user, mob;
  private ArrayList<String> details = new ArrayList<String>();
  private Font infoFont = new Font("Arial", Font.PLAIN, 20);
  private Font titleFont = new Font("Arial", Font.BOLD, 30);
  @SuppressWarnings("unused")
  private int pointer = 0;
  private JLabel background = new JLabel(new ImageIcon("images/Wall.png"));
  private static boolean hasAdjust = true;
  // Consturtor to start the battle
  Battle(Entity user, Entity mob, int index, int remain) {
    this.user = user;
    this.mob = mob;
    background.setBounds(0,0,800,450);
    // first line detail
    Game.updateText(user.getName() + " begins battle with " + mob.getName());
    Game.updateText("This is monster number: " + index);
    turnDetail.setText("<html>"+Game.getText()[0]+"<br>"+Game.getText()[1]+"<html>");
    // images
    userImage = new JButton(new ImageIcon(user.getImage()));
    userImage.addActionListener(this);
    userImage.setActionCommand("user");
    userImage.setBorder(null);
    userImage.setContentAreaFilled(false);
    mobImage = new JButton(new ImageIcon(mob.getImage()));
    mobImage.addActionListener(this);
    mobImage.setActionCommand("mob");
    userImage.setBounds(170, 150, 100, 100);
    mobImage.setBounds(Game.width - 270, 150, 100, 100);
    mobImage.setBorder(null);
    mobImage.setContentAreaFilled(false);
    this.add(userImage);
    this.add(mobImage);
    // set size of jpanel and layout
    this.setSize(Game.width, Game.height);
    this.setLayout(null);
    // title
    title.setBounds(170, 0, 460, 80);
    this.add(title);
    title.setText("<html><div style='text-align: center;'>" + user.getName() + " vs " + mob.getName() + "<br/>Entities remaining: " + remain + "<html>");
    title.setFont(titleFont);
    // text above images
    userHP.setBounds(170, 90, 100, 50);
    this.add(userHP);
    mobHP.setBounds(Game.width - 270, 90, 100, 50);
    this.add(mobHP);
    // Turn by turn detail
    turnDetail.setBounds(170, 305, 396, 32);
    this.add(turnDetail);
    // Up down button
    up.setBounds(566, 305, 32, 32);
    up.setOpaque(false);
    up.setContentAreaFilled(false);
    up.setBorderPainted(false);
    up.setActionCommand("up");
    up.addActionListener(this);
    this.add(up);
    down.setBounds(598, 305, 32, 32);
    down.setActionCommand("down");
    down.setOpaque(false);
    down.setContentAreaFilled(false);
    down.setBorderPainted(false);
    down.addActionListener(this);
    this.add(down);
    // Choice buttons
    for (int i = 1; i < user.getSkillNames().length; i++) {
      choices[i - 1] = new JButton("<html><div style='text-align: center;'>" + user.getSkillNames()[i] + "<html>");
      choices[i - 1].setHorizontalAlignment(SwingConstants.CENTER);
      choices[i - 1].setBounds(170 + (115 * (i - 1)), 352, 115, 70);
      this.add(choices[i - 1]);
      choices[i - 1].addActionListener(this);
      choices[i - 1].setActionCommand(String.valueOf(i));
      choices[i - 1].setFocusPainted(false);
    }
    // Updates text as needed
    updateText();
    this.add(background);
    if (hasAdjust){
      String[] text = Game.upDown(true);
      turnDetail.setText("<html>"+text[0]+"<br>"+text[1]+"<html>");
      hasAdjust = false;
    }
  }

  // Called whever action listener is triggered
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    String command = e.getActionCommand();
    if (command.equals("up")) {
      String[] text = Game.upDown(true);
      turnDetail.setText("<html>"+text[0]+"<br>"+text[1]+"<html>");
    } else if (command.equals("down")) {
      String[] text = Game.upDown(false);
      turnDetail.setText("<html>"+text[0]+"<br>"+text[1]+"<html>");
    } else if (command.equals("user")) {
      Game.flipShow();
    } else if (command.equals("mob"))  {
      new ShowStat(mob);
    } else {
      int intCommand = Integer.valueOf(e.getActionCommand());
      user.setChoice(intCommand);
      user.action(mob);
      if (mob.getStats()[0] <= 0) {
        Game.next();
      } else if (user.getStats()[0] <= 0) {
        Game.end();
      } else {
        mob.action(user);
        updateText();
      }
    }
  }

  // Takes a double in return a double rouded to 1 decimal point
  public double rounding(double num) {
    num = num * 10;
    num = Math.round(num);
    num = num / 10;
    return num;
  }

  // Call to update text display stats on user and mob
  public void updateText() {
    Game.upate();
    mobHP.setText("<html>H: " + rounding(mob.getStats()[0]) + "<html>");
    userHP.setText("<html>H: " + rounding(user.getStats()[0]) + "<html>");
    mobHP.setFont(infoFont);
    turnDetail.setText("<html>"+Game.getText()[0]+"<br>"+Game.getText()[1]+"<html>");
    pointer = details.size() - 4;
    if (user.getStats()[0] <= 0) {
      Game.end();
      System.out.println("end");
    }
  }
}