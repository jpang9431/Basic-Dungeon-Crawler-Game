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
class Battle extends JPanel implements ActionListener{
  private JButton[] choices = new JButton[4];
  private String userName="", mobName="";
  private File userFile = null, mobFile = null;
  private JLabel title = new JLabel("",JLabel.CENTER);
  private JButton up = new JButton(new ImageIcon("images/Up.png")), down = new JButton(new ImageIcon("images/Down.png"));
  private JLabel mobHP = new JLabel("",JLabel.CENTER), userHP = new JLabel("", JLabel.CENTER), turnDetail = new JLabel(""), userLabel, mobLabel;
  private JLabel userImage = new JLabel(""), mobImage = new JLabel("");
  private Entity user, mob;
  private ArrayList<String> details = new ArrayList<String>();
  private String detail = "";
  private int pointer = 0;
  private Font infoFont = new Font("Arial", Font.PLAIN, 20);
  private Font titleFont = new Font("Arial", Font.BOLD, 30);
  //100 ,100
  Battle(Entity user, Entity mob){
    //x,y,width,height
    this.user = user;
    this.mob = mob;
    details.add(user.getName() + " begins battle with " + mob.getName());
    userImage.setIcon(new ImageIcon(user.getImage()));
    mobImage.setIcon(new ImageIcon(mob.getImage()));
    userImage.setBounds(170, 150, 100, 100);
    mobImage.setBounds(Game.width-270,150,100,100);
    this.add(userImage);
    this.add(mobImage);
    //panel
    this.setSize(Game.width, Game.height);
    this.setLayout(null);
    //title
    title.setBounds(170, 0, 460, 80);
    this.add(title);
    title.setText(user.getName()+" vs "+mob.getName());
    title.setFont(titleFont);
    //text above images
    userHP.setBounds(170, 90, 100, 50);
    this.add(userHP);
    mobHP.setBounds(Game.width-270, 90, 100, 50);
    this.add(mobHP);
    
    
    
    //Turn by turn detail
    turnDetail.setBounds(170,305,396,32);
    this.add(turnDetail);
    //Up down button
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
    //Choice buttons
    for (int i=1; i<user.getSkillNames().length; i++){
      choices[i-1] = new JButton("<html>"+user.getSkillNames()[i]+"<html>");
      choices[i-1].setHorizontalAlignment(SwingConstants.CENTER);
      choices[i-1].setBounds(170+(115*(i-1)), 352, 115, 70);
      this.add(choices[i-1]);
      choices[i-1].addActionListener(this);
      choices[i-1].setActionCommand(String.valueOf(i));
      choices[i-1].setFocusPainted(false);
    }
    updateText();
  }

  private void beginBattle(){
    
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    System.out.println(e.getActionCommand());
    String command = e.getActionCommand();
    if (command.equals("up")){
      
    } else if (command.equals("down")){
      
    } else {
      int intCommand = Integer.valueOf(e.getActionCommand());
      user.setChoice(intCommand);
      user.action(mob);
    }
  }
  
  public void updateText(){
    mobHP.setText("<html>S: "+mob.getStats()[1]+"<br/>H: "+mob.getStats()[0]+"<html>");
    userHP.setText("<html>S: "+user.getStats()[1]+"<br/>H: "+user.getStats()[0]+"<html>");
    mobHP.setFont(infoFont);
    userHP.setFont(infoFont);
    detail = "<html>";
    pointer = details.size()-4;
    if (details.size()>3){
      for (int i=0; i<details.size(); i++){
        detail = details.get(details.size()-i-1) + "<br/>" + detail;
      }
    } else{
      for (int i=0; i<details.size(); i++){
        detail = detail + details.get(i) + "<br/>";
      }
    }
    detail = detail + "<html>";
    turnDetail.setText(detail);
  }
}