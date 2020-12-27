package Factory;

public class CropField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
	private int daysSincePlanted;

	public CropField(String type){
		this.type = type;
	}

	@Override
	public String getType(){
		return type;
	}

	@Override
	public double getProfit(){
		return profit;
	}

	@Override
	public void setProfit(double profit){
		this.profit = profit;
	}

	@Override
	public double getChanceOfDisease() {
		return chanceOfDisease;
	}

	@Override
	public void setChanceOfDisease(double chanceOfDisease) {
		this.chanceOfDisease = chanceOfDisease;
	}

	public int getDaysSincePlanted() {
		return daysSincePlanted;
	}

	public void setDaysSincePlanted(int daysSincePlanted) {
		this.daysSincePlanted = daysSincePlanted;
	}
}