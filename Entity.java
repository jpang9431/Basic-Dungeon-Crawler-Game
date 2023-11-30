abstract class Entity{
	private String name = "";
	private boolean isMob = true;
	private String image = "";
	Entity(String name, boolean isMob, String image){
		this.name = name;
		this.isMob = isMob;
		this.image = image;
	}
	abstract void action(Entity otherEntity);
	public String getName() {
		return name;
	}
	public boolean getMob() {
		return isMob;
	}
	public String getImage() {
		return image;
	}

	//Stuff so we can use entity for interactions instead of mob
	public double[] getResistances() {
		return null;
	}
	public void dam(double damage) {
		return;
	};
	public double[] getStats() {
		return null;
	}
	public void setStats(double[] inStats){
		return;
	}
	public String[] getnames() {
		return null;
	}
}