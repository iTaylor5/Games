package Factory;

import java.util.LinkedList;
import java.util.Random;

public class CropField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
	private int cyclesTillHarvest;
	private int cyclesSincePlanted;
	private final int amountOfCyclesBeforeHarvest;
	private boolean harvest;
	private boolean diseased;
	boolean destroyed = false;
	private double account;
	private Random random;

	public CropField(String type, int cyclesBeforeHarvest){
		this.type = type;
		this.amountOfCyclesBeforeHarvest = cyclesBeforeHarvest;
		harvest = false;
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

//	@Override
//	public void cycleInfo() {
//
//	}

	@Override
	public boolean canHarvest() {
		return harvest;
	}

	@Override
	public void setHarvest(boolean pHarvest) {
		harvest = pHarvest;
	}

	@Override
	public boolean isDiseased() {
		return diseased;
	}

	@Override
	public void setIsDiseased(boolean pDiseased) {
		this.diseased = pDiseased;
	}

	@Override
	public void catchDisease() {
		random = new Random();

		if(isDiseased()){
			System.out.println("This field has been diseased for a full cycle." +
					" It is now destroyed");
			destroyed = true;
		} else {
			int numb = random.nextInt(10 - 1 + 1) + 1;
			if (numb <= getChanceOfDisease()) {
				setIsDiseased(true);
				System.out.println("\n-------------------------------------- \n");
				System.out.print("\nField:\t" + getType());
				System.out.println(" --> Crop has caught disease during the night.\n");
				System.out.println("\n-------------------------------------- \n");
			}
		}
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public LinkedList<Livestock> getAnimalsToBeSlaughtered() {
		return null;
	}

	@Override
	public void addAnimalToBeSlaughtered(Livestock animal) {
		//TODO: Get rid of...
	}

	@Override
	public double getAccount() {
		return account;
	}

	@Override
	public void setAccount(double pAccount) {
		account = pAccount;
	}

	@Override
	public void cycleInfo() {
		System.out.print("Field type: " + getType());
		System.out.print(", days since planted: " + cyclesSincePlanted);
		System.out.println(", days till harvest: " + cyclesTillHarvest);
		System.out.println("Does this field have disease: " + diseased);
		System.out.println("Is this field destroyed: " + isDestroyed());
	}

	public void nightCycle(){
		setCyclesTillHarvest(getCyclesTillHarvest() - 1);
		setCycleSincePlanted(getCycleSincePlanted() + 1 );
		System.out.println("\t ~sunset~");
		catchDisease();
	}

	@Override
	public void printInfo() {
		//TODO: Get rid of...
	}

	@Override
	public LinkedList<Livestock> getLivestock() {
		//TODO: sort out....
		return null;
	}

	public int getCycleSincePlanted() {
		return cyclesSincePlanted;
	}

	public int getCyclesTillHarvest() {
		return cyclesTillHarvest;
	}

	public void setCyclesTillHarvest(int cyclesTillHarvest) {
		this.cyclesTillHarvest = cyclesTillHarvest;
	}

	public void setCycleSincePlanted(int daySincePlanted) {
		this.cyclesSincePlanted = daySincePlanted;
	}

	public int getAmountOfCyclesBeforeHarvest() {
		return amountOfCyclesBeforeHarvest;
	}
}