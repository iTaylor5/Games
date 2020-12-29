package Factory;

import java.util.LinkedList;
import java.util.Random;

public class LivestockField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
	private final int maxNumberOfLivestock;
	boolean destroyed = false;
	private boolean diseased;

	private LinkedList<Cow> cows;
	private LinkedList<Bull> bulls;

	public LivestockField(String type, int numOfAnimals){
		this.type = type;
		this.maxNumberOfLivestock = numOfAnimals;

		cows = new LinkedList<>();
		bulls = new LinkedList<>();
	}

	public LinkedList<Cow> getCows() {
		return cows;
	}

	public void addCow(Cow pCows) {
		cows.add(pCows);
	}

	public LinkedList<Bull> getBulls() {
		return bulls;
	}

	public void addBull(Bull pBull) {
		bulls.add(pBull);
	}

	public void printLivestockInfo(){

		System.out.println("\n---------------------------------------- ");
		for(Bull b : bulls){
			System.out.println("Type: " + b.getType());
			System.out.println("Price at slaughter: " + b.getPriceAtSlaughter());
			System.out.println("Fully sized: " + b.isFullySized());
			System.out.println("Age: " + b.getAge());
		}

		System.out.println("\n---------------------------------------- ");
		for(Cow c : cows){
			System.out.println("Type: " + c.getType());
			System.out.println("Price at slaughter: " + c.getPriceAtSlaughter());
			System.out.println("Fully sized: " + c.isFullySized());
			System.out.println("Age: " + c.getAge());
		}
		System.out.println("---------------------------------------- ");
	}

	@Override
	public String getType(){
		return type;
	}

	@Override
	public double getProfit() {

		double totalProfit = 0;

		for(Bull b : bulls){
			totalProfit += b.getCurrentCostOfAnimal();
		}

		for(Cow c : cows){
			totalProfit += c.getCurrentCostOfAnimal();
		}
		// TODO: Implement Sheep

		return totalProfit;
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
		printLivestockInfo();

		System.out.println("Does this field have disease: " + diseased);
		System.out.println("Is this field destroyed: " + isDestroyed());

		dayCycle();
		nightCycle();

	}
	public void dayCycle(){
		System.out.println("\t ~Sunrise~");
//		if (getCyclesTillHarvest() == 0 && !destroyed) {
//			System.out.println("Time to harvest!!!");
//			harvest = true;
//			setCyclesTillHarvest(getAmountOfCyclesBeforeHarvest());
//			setCycleSincePlanted(0);
//		}
	}

	public void nightCycle(){
		for(Cow c : getCows()){
			c.setAge(c.getAge()+1);
		}

		for( Bull b : getBulls()){
			b.setAge(b.getAge()+1);
		}
		//TODO: Need to implement sheep
//		setCyclesTillHarvest(getCyclesTillHarvest() - 1);
//		setCycleSincePlanted(getCycleSincePlanted() + 1 );
		System.out.println("\t ~sunset~");
		catchDisease();
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
		return diseased;
	}

	@Override
	public void setIsDiseased(boolean pDiseased) {
		this.diseased = pDiseased;

	}

	@Override
	public void catchDisease() {
		Random random = new Random();
		if(isDiseased()){
			System.out.println("This field has been diseased for a full cycle." +
					" It is now destroyed");
			destroyed = true;
		} else {
			int numb = random.nextInt(10 - 1 + 1) + 1;
			if (numb <= getChanceOfDisease()) {
				setIsDiseased(true);
				System.out.println("\n-------------------------------------- \n");
				System.out.println("\nField:\t" + getType());
				System.out.println("Crop has caught disease during the night.\n");
				System.out.println("\n-------------------------------------- \n");
			}
		}
	}

	@Override
	public boolean isDestroyed() {
		//TODO: Need to implement
		return false;
	}
}