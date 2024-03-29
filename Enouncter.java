import javax.imageio.*;
import javax.swing.*;
import java.awt.Image.*;
import java.io.File;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.awt.Font;
import java.util.Random;

class Encounter extends JPanel implements ActionListener {
  private JButton[] buttons;
  private JButton userLabel;
  private JLabel title = new JLabel(""), eventLabel = new JLabel("");
  private Font titleFont = new Font("Arial", Font.BOLD, 30);
  private Entity event = null;
  private Entity user = null;
  private JLabel background = new JLabel(new ImageIcon("images/Wall.png"));
  Encounter(Entity user, Entity event) {
    int size = 460;
    this.event = event;
    this.user = user;
    title = new JLabel("<html><div style='text-align: center;'>" + event.getName()+"<html>", JLabel.CENTER);
    title.setBounds(170, 0, 460, 80);
    title.setFont(titleFont);
    this.add(title);
    buttons = new JButton[event.getChoices().length];
    size = size / event.getChoices().length;
    for (int i = 0; i < event.getChoices().length; i++) {
      buttons[i] = new JButton("<html><div style='text-align: center;'>" + event.getChoices()[i] + "<html>");
      buttons[i].addActionListener(this);
      buttons[i].setActionCommand(String.valueOf(i));
      buttons[i].setBounds(170 + size * i, 352, size, 70);
      this.add(buttons[i]);
    }
    userLabel = new JButton(new ImageIcon(user.getImage()));
    userLabel.addActionListener(this);
    userLabel.setActionCommand("user");
    userLabel.setBorder(null);
    userLabel.setContentAreaFilled(false);
    eventLabel.setIcon(new ImageIcon(event.getImage()));
    userLabel.setBounds(170, 150, 100, 100);
    eventLabel.setBounds(Game.width - 270, 150, 100, 100);
    this.add(userLabel);
    this.add(eventLabel);
    this.setLayout(null);
    background.setBounds(0,0,800,450);
    this.add(background);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("user")){
      Game.flipShow();
    } else {
      event.setChoice(Integer.valueOf(command));
      event.action(user);
      Game.next();
    }
  }
}