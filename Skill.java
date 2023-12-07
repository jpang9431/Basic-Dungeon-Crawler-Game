import java.util.Random;

class Skill {
  private Random rand = new Random();
  private double acc = 100.0;
  private boolean passive = false;
  private boolean offense = true;
  private boolean hurtSelf = false;
  private Damage dam = null;
  private final static int magic = 0, blunt = 1, sharp = 2;

  enum Type {
    MAGIC,
    BLUNT,
    SHARP,
    NORMAL
  }

  private Type type = null;

  // Called to create a skill instance
  Skill(double acc, boolean passive, boolean offense, Type type, Damage dam) {
    this.acc = acc;
    this.passive = passive;
    this.offense = offense;
    this.type = type;
    this.dam = dam;
    if (this.dam.getSelfDam() > 0) {
      hurtSelf = true;
    }
  }

  public double getBase(){
    return dam.getDam();
  }

  // Called to get the amount of damage this skill will note favor closer to 1
  // means more liekly to hit max damage
  public double getDam(double[] resistances, double favor, boolean notSelf) {
    double accCheck = rand.nextDouble() * 100;
    if (acc <= accCheck) {
      return 0;
    }
    if (!this.getOffense()) {
      return dam.getDam();
    } else if (this.passive){
      return dam.getDam();
    }
    double randNum = rand.nextDouble() + .5;
    randNum = randNum + favor;
    if (notSelf) {
      if (randNum > 1.5) {
        randNum = 1.5;
      }
      randNum = randNum * dam.getDam();
    } else if (!notSelf) {
      randNum = randNum * dam.getSelfDam();
    }
    switch (type) {
      case MAGIC:
        if (resistances[magic] > 0) {
          randNum = randNum / resistances[magic];
        }
      case BLUNT:
        if (resistances[blunt] > 0) {
          randNum = randNum / resistances[blunt];
        }
      case SHARP:
        if (resistances[sharp] > 0) {
          randNum = randNum / resistances[sharp];
        }
      case NORMAL:
        return randNum;
    }
    return randNum;
  }

  // Overloading method to call to get amount of damage without favor
  public double getDam(double[] resistances, boolean notSelf) {
    return this.getDam(resistances, 0.0, notSelf);
  }

  // Call to get wether or not this skill is a passive skill
  public boolean getPassive() {
    return passive;
  }

  // Call to get wether or not this skill is an offensive skill
  public boolean getOffense() {
    return offense;
  }

  // Call to see wether or not this skill hurts the mob using the skill
  public boolean getHurtSelf() {
    return hurtSelf;
  }

  // Call to get the type of the skill, see enum Type to see types
  public Type getType() {
    return type;
  }
}
