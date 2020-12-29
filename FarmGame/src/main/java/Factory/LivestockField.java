package Factory;

public class LivestockField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
//	private int livestockLifeSpan;
//	private final double chanceToImpregnated = .6; // 60%

	// , int lifeSpan
	public LivestockField(String type){
		this.type = type;
		//this.livestockLifeSpan = lifeSpan;
	}

	@Override
	public String getType(){
		return type;
	}

	@Override
	public double getProfit() {
		return profit;
	}

	public void setProfit(double amount){
		profit = amount;
	}

	@Override
	public double getChanceOfDisease() {
		return chanceOfDisease;
	}

	@Override
	public void setChanceOfDisease(double chanceOfDisease) {
		this.chanceOfDisease = chanceOfDisease;
	}

	@Override
	public void cycleInfo() {
		// TODO: Implement
		System.out.println("Nothing yet. Still to be implemented.");
	}

	@Override
	public boolean canHarvest() {
		//TODO: Need to implement
		return false;
	}

	@Override
	public void setHarvest(boolean pHarvest) {
		//TODO: Need to implement
	}

	@Override
	public boolean isDiseased() {
		//TODO: Need to implement
		return false;
	}

	@Override
	public void setIsDiseased(boolean pDiseased) {
		//TODO: Need to implement

	}

	@Override
	public void catchDisease() {
		//TODO: Need to implement
	}

	@Override
	public boolean isDestroyed() {
		//TODO: Need to implement
		return false;
	}

}