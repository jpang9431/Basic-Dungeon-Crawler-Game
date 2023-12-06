import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.*;
import javax.swing.border.BevelBorder;
class ShowStat{
  private static final SkillDict dict = new SkillDict();
  private JFrame frame;
  private JPanel panel = new JPanel();
  private Entity ent;
  ShowStat(Entity ent){ 
    this.ent = ent;
    frame = new JFrame(ent.getName());
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    update();
  }
  public void update(){
    panel = new JPanel();
    panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    double[] stats = ent.getStats();
    String[] skillNames = ent.getSkillNames();
    Skill[] skills = ent.getSkills();
    panel.add(new JLabel("Name: "+ ent.getName()));
    panel.add(new JLabel("Stats"));
    panel.add(new JLabel("Level: " + stats[2]));
    panel.add(new JLabel("Health: " + stats[0]));
    panel.add(new JLabel("Strength: " + stats[3]));
    panel.add(new JLabel("Magic: " + stats[4]));
    panel.add(new JLabel("------------------------"));
    panel.add(new JLabel("Skills"));
    for (int i = 0; i < skillNames.length; i++) {
      if (skillNames[i] != null) {
        panel.add(new JLabel("<html>" + skillNames[i] + ": " 
                             + dict.getDiscrpt(skillNames[i]) + " for " + skills[i].getBase() +"<html>"));
      }
    }
    frame.setAlwaysOnTop(true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        if (ent.getName().equals(Game.getUser().getName())){
          Game.setShow(false);
        }
      }
    });
    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
  }
}