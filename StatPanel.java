import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.*;
import javax.swing.border.BevelBorder;

class StatPanel extends JPanel implements ActionListener {
  private static final String iconPath = "images/BLUE.png";
  private final SkillDict dict = new SkillDict();
	private JFrame statShow;

  StatPanel() {
    this.setSize(20, 20);
    this.setLayout(null);
    Icon icon = new ImageIcon(iconPath);
    JButton image = new JButton(icon);
    image.setBounds(0, 0, 20, 20);
    image.addActionListener(this);
    this.add(image);
  }

  public void actionPerformed(ActionEvent e) {
		if(statShow!=null){
			statShow.setVisible(false);
			statShow = null;
			return;
		}
  	statShow = new JFrame("Stats");
		statShow.setAlwaysOnTop(true);
    statShow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    updateText();
    statShow.setVisible(true);
  }
	
	public void updateText(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		User user = Game.getUser();
		double[] userStats = user.getStats();
		String[] skillNames = user.getSkillNames();
		Skill[] skills = user.getSkills();
		panel.add(new JLabel("Stats"));
		panel.add(new JLabel("Level: " + userStats[2]));
		panel.add(new JLabel("Max Health: " + Game.getMaxHP()));
		panel.add(new JLabel("Strength: " + userStats[3]));
		panel.add(new JLabel("Magic: " + userStats[4]));
		panel.add(new JLabel("------------------------"));
		panel.add(new JLabel("Skills"));
		for (int i = 0; i < skillNames.length; i++) {
			if (skillNames[i] != null) {
				panel.add(new JLabel("<html>" + skillNames[i] + ": " 
														 + dict.getDiscrpt(skillNames[i]) + " for " + skills[i].getBase() +"<html>"));
			}
		}
		if(statShow!=null){
			statShow.setContentPane(panel);
			statShow.pack();
		}
	}
}