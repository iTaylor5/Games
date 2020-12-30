package Factory;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class LivestockField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
	private final int maxNumberOfLivestock;
	boolean destroyed = false;
	private boolean diseased;
	private boolean harvest;
	private Scanner reader;

	private LinkedList<Cow> cows;
	private LinkedList<Bull> bulls;
	private LinkedList<Livestock> livestock;
	private LinkedList<Livestock> animalsToBeSlaughtered;

	public LivestockField(String type, int numOfAnimals){
		this.type = type;
		this.maxNumberOfLivestock = numOfAnimals;
		cows = new LinkedList<>();
		bulls = new LinkedList<>();
		livestock = new LinkedList<>();
		animalsToBeSlaughtered = new LinkedList<>();
		reader = new Scanner(System.in, "UTF-8");
	}

	public void printLivestockInfo(){

		int count = 0;
		System.out.println("\n---------------------------------------- ");

		for(Livestock animal : livestock){
			System.out.print("Number: " + count);
			System.out.print(", type: " + animal.getType());
			System.out.print(", price at slaughter: " + animal.getPriceAtSlaughter());
			System.out.print(", fully sized: " + animal.isFullySized());
			System.out.println(", and age: " + animal.getAge());
			System.out.println("");
			count++;
		}
	}

	@Override
	public String getType(){
		return type;
	}

	@Override
	public double getProfit() {

		double totalProfit = 0;

		for(Livestock animal : livestock){
			totalProfit += animal.getCurrentCostOfAnimal();
		}

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
		System.out.println("(yes/no) Would you like to slaughter?");
		String response = reader.nextLine();
		if(response.equalsIgnoreCase("yes")){
			slaughterAnimals();
		}
	}

	public void slaughterAnimals(){

		while(true){
			System.out.println("\nEnter the number corresponding to the livestock you would " +
					"like to sell or -1 to exit.");
			printLivestockInfo();
			int num = reader.nextInt();
			if(num == -1){
				break;
			}else if(num >= 0 && num <= getLivestock().size()-1){
				Livestock animal = getLivestock().get(num);
				getLivestock().remove(num);

				System.out.println("We will slaughter this " + animal.getType() +
						" and the price is " + animal.getCurrentCostOfAnimal());

				addAnimalToBeSlaughtered(animal);
				setHarvest(true);
			}else {
				System.out.println("Invalid input.");
			}
		}
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

	public LinkedList<Livestock> getLivestock() {
		return livestock;
	}

	public void addToLivestock(Livestock animal) {
		livestock.add(animal);
	}

	public LinkedList<Livestock> getAnimalsToBeSlaughtered() {
		return animalsToBeSlaughtered;
	}

	public void addAnimalToBeSlaughtered(Livestock animal) {
		animalsToBeSlaughtered.add(animal);
	}
}