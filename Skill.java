import java.util.Random;
class Skill{
  private Random rand = new Random();
  private double acc = 100.0;
  private boolean passive = false;
  private boolean offense = true;
  private boolean hurtSelf = false;
  //If false heal mob else add to shield
  private boolean shieldAdd = false; 
  private Damage dam = null;
  private final static int magic = 0, blunt = 1, sharp = 2; 
  enum Type{
    MAGIC,
    BLUNT,
    SHARP,
    NORMAL
  }
  private Type type = null;
  Skill(double acc, boolean passive, boolean offense, Type type, Damage dam, boolean shieldAdd){
    this.acc = acc;
    this.passive = passive;
    this.offense = offense;
    this.type = type;
    this.dam = dam;
    this.shieldAdd = shieldAdd;
    if (this.dam.getSelfDam()>0) {
    	hurtSelf = true;
    }
  }
  public double getDam(double[] resistances, double favor, boolean notSelf){
    double accCheck = rand.nextDouble()*100;
    if (acc<=accCheck){
      return 0;
    }
    double randNum = rand.nextDouble()+.5;
    randNum = randNum + favor;
    if (notSelf){
      if (randNum>1.5){
        randNum = 1.5;
      }
      randNum = randNum*dam.getDam();
    } else if(!notSelf){
      randNum = randNum*dam.getSelfDam();
    }
    switch(type){
      case MAGIC:
        randNum = randNum/resistances[magic];
      case BLUNT:
        randNum = randNum/resistances[blunt];
      case SHARP:
        randNum = randNum/resistances[sharp];
      case NORMAL:
        
    }
    return randNum;
  }
  public double simGetDam(double[] resistances, boolean notSelf){
    return getDam(resistances, 0.0, notSelf);
  }
  public boolean getPassive() {
	  return passive;
  }
  public boolean getOffense() {
	  return offense;
  }
  public boolean getShieldAdd () {
	  return shieldAdd;
  }
  public boolean getHurtSelf () {
	  return hurtSelf;
  }
  public Type getType() {
	  return type;
  }
}
