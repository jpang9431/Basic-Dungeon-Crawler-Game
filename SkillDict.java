import java.util.HashMap;

//Contains all the skills possible
class SkillDict {
  private HashMap<String, Skill> skillObjects = new HashMap<String, Skill>();
  private HashMap<String, String> skillDiscrpt = new HashMap<String, String>();
  private String[] skillNames = { "Magic Ball", "Shield Bash", "Sword Slash", "Block", "Tackle", "Slime Body" };
  private String[] skillDiscrpts = { "Fire a mana ball at an enemy", "Bash enemy with a shield",
      "Slice enemy with a sword", "Raise shield to prepare to block an attack", "Tackle the enemy",
      "Chance to negate any attack" };
  private double[] acc = { 100, 100, 100, 100, 100, 10 };
  private boolean[] passive = { false, false, false, false, false, true };
  private boolean[] offense = { true, true, true, false, true, false };
  // Type of each skill
  private Skill.Type[] type = { Skill.Type.MAGIC, Skill.Type.BLUNT, Skill.Type.SHARP, Skill.Type.NORMAL,
      Skill.Type.BLUNT, Skill.Type.NORMAL };
  private double[] dam = { 3, 2, 3, 2, 2, Double.MAX_VALUE };
  private double[] self = { 0, 0, 0, 0, 0, 0 };

  // Call to create dictionary of skills
  SkillDict() {
    for (int i = 0; i < skillNames.length; i++) {
      skillObjects.put(skillNames[i], new Skill(acc[i], passive[i], offense[i], type[i], new Damage(dam[i], self[i])));
      skillDiscrpt.put(skillNames[i], skillDiscrpts[i]);
    }
  }

  // Get skill instace based on the skill name
  public Skill getSkill(String name) {
    return skillObjects.get(name);
  }

  // Get skill description based on skill name
  public String getDiscrpt(String name) {
    return skillDiscrpt.get(name);
  }
}