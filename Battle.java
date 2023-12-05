import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Use to battle two entities togteher
class Battle extends JPanel implements ActionListener {
  private JButton[] choices = new JButton[4];
  private JLabel title = new JLabel("", JLabel.CENTER);
  private JButton up = new JButton(new ImageIcon("images/Up.png")),
      down = new JButton(new ImageIcon("images/Down.png"));
  private JLabel mobHP = new JLabel("", JLabel.CENTER), userHP = new JLabel("", JLabel.CENTER),
      turnDetail = new JLabel("");
  private JLabel userImage = new JLabel(""), mobImage = new JLabel("");
  private Entity user, mob;
  private ArrayList<String> details = new ArrayList<String>();
  private String detail = "";
  private Font infoFont = new Font("Arial", Font.PLAIN, 20);
  private Font titleFont = new Font("Arial", Font.BOLD, 30);
  @SuppressWarnings("unused")
  private int pointer = 0;

  // Consturtor to start the battle
  Battle(Entity user, Entity mob, int index, int remain) {
    this.user = user;
    this.mob = mob;
    // first line detail
    details.add(user.getName() + " begins battle with " + mob.getName());
    details.add("This is monster number: " + index);
    // images
    userImage.setIcon(new ImageIcon(user.getImage()));
    mobImage.setIcon(new ImageIcon(mob.getImage()));
    userImage.setBounds(170, 150, 100, 100);
    mobImage.setBounds(Game.width - 270, 150, 100, 100);
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
  }

  // Called whever action listener is triggered
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    String command = e.getActionCommand();
    if (command.equals("up")) {

    } else if (command.equals("down")) {

    } else {
      int intCommand = Integer.valueOf(e.getActionCommand());
      user.setChoice(intCommand);
      user.action(mob);
      if (mob.getStats()[0] <= 0) {
        Game.next();
      } else if (user.getStats()[0] <= 0) {
        Game.end();
        System.out.println("end");
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
    
    
    mobHP.setText("<html>H: " + rounding(mob.getStats()[0]) + "<html>");
    userHP.setText("<html>H: " + rounding(user.getStats()[0]) + "<html>");
    mobHP.setFont(infoFont);
    userHP.setFont(infoFont);
    detail = "<html>";
    pointer = details.size() - 4;
    if (details.size() > 3) {
      for (int i = 0; i < details.size(); i++) {
        detail = details.get(details.size() - i - 1) + "<br/>" + detail;
      }
    } else {
      for (int i = 0; i < details.size(); i++) {
        detail = detail + details.get(i) + "<br/>";
      }
    }
    detail = detail + "<html>";
    turnDetail.setText(detail);
    if (user.getStats()[0] <= 0) {
      Game.end();
      System.out.println("end");
    }
  }
}