class Damage {
  private double dam = 0.0;
  private double selfDam = 0.0;

  Damage(double dam, double selfDam) {
    this.dam = dam;
    this.selfDam = selfDam;
  }

  public double getDam() {
    return dam;
  }

  public double getSelfDam() {
    return selfDam;
  }
}