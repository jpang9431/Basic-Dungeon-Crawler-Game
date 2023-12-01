import javax.swing.*;
class Game{
	public final static SkillDict dict = new SkillDict();
	public final static int width = 800, height = 450;
	private JFrame frame = new JFrame("Game");
	Game(){
		TestUser user = new TestUser();
		Slime slime2 = new Slime();
		frame.setContentPane(new Battle(user, slime2));
		frame.setSize(width, height);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new Game();
	}
}