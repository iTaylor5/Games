package Factory;

public class LivestockField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;

	public LivestockField(String type){
		this.type = type;
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

}