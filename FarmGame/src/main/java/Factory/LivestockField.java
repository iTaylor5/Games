package Factory;

import java.util.*;

public class LivestockField implements Field {

	private final String type;
	private double profit;
	private double chanceOfDisease;
	private final int maxNumberOfLivestock;
	boolean destroyed = false;
	private boolean diseased;
	private boolean harvest;
	private Scanner reader;
	private double account;

	private LinkedList<Livestock> livestock;
	private LinkedList<Livestock> animalsToBeSlaughtered;

	public LivestockField(String type, int numOfAnimals){
		this.type = type;
		this.maxNumberOfLivestock = numOfAnimals;
		livestock = new LinkedList<>();
		animalsToBeSlaughtered = new LinkedList<>();
		reader = new Scanner(System.in, "UTF-8");
	}

	public void printInfo(){

		int count = 0;
		System.out.println("\n---------------------------------------- ");
		System.out.println("This fields livestock information:\n");
		for(Livestock animal : livestock){
			System.out.print(count);
			System.out.print(", " + animal.getType());
			System.out.printf(", price at slaughter: %.2f", animal.getCurrentCostOfAnimal());
			System.out.print(" in gold, is the animal fully sized: " + animal.isFullySized());
			System.out.print(", age in months: " + animal.getAgeInMonths());

			if(animal.isImpregnated()){
				if(animal.getType().equals("cow") || animal.getType().equals("ewe")){
					FemaleLivestock femLiv = (FemaleLivestock) animal;
					if(animal.isImpregnated()){
						System.out.print(". It is pregnant");
						System.out.print(", " + femLiv.getTimeInGestation() + " cycle into pregnancy. ");
					} else if (animal.isInCoolingPeriod()){
						System.out.print(". It is in a cooling period while nursing its young.");
					}

				}
			}
			System.out.println("");

			if( animal.getMaxAge() - animal.getAgeInMonths() < 6 ) {
				System.out.print(" ** This animal is showing signs of old age! ");
				System.out.println("Animals has roughly " + (animal.getMaxAge() - animal.getAgeInMonths())
				+ " months to live. But it could die any cycle. **");
				// TODO: implement random could die any time...
			}

			count++;
		}
		System.out.println("");
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
		printInfo();

		System.out.println("Does this field have disease: " + diseased);
		System.out.println("Is this field destroyed: " + isDestroyed());

	}

	public void nightCycle(){

		ListIterator<Livestock> iter = getLivestock().listIterator();

		while(iter.hasNext()){

			Livestock animal = iter.next();

			incrementAnimalsAge(animal);

			if(animal.getAgeInMonths() == animal.getMaxAge()){
				System.out.println("\n*** This animal has died ***\n");
				System.out.println("This animal has died." + animal.getType());
				iter.remove();
				continue;
			}

			if(animal.isImpregnated()){
				cycleForPregnantAnimal(iter, animal);
			}

			if(animal.getType().equals("cow") || animal.getType().equals("ewe")){
				femLivestock(animal);
			}
		}
		catchDisease();
	}


	public void incrementAnimalsAge(Livestock animal){
		animal.setAgeInMonths(animal.getAgeInMonths() + 1);

		if(animal.getAgeInMonths() >= 36)
			animal.setFullySized(true);

	}

	public void cycleForPregnantAnimal(ListIterator<Livestock> pIter, Livestock animal){
		if(animal.getType().equals("cow")){
			Cow cow = (Cow) animal;
			cow.setTimeInGestation(cow.getTimeInGestation() + 1);

			if(cow.getTimeInGestation() == cow.getGestationLength()){
				System.out.println("A calf has been born.");
				cow.setTimeInGestation(0);
				cow.setImpregnated(false);
				cow.setInCoolingPeriod(true);
				pIter.add(new Cow(0));
			}
		}else if(animal.getType().equals("ewe")){
			Ewe ewe = (Ewe) animal;
			ewe.setTimeInGestation(ewe.getTimeInGestation() + 1);

			if(ewe.getTimeInGestation() == ewe.getGestationLength()){
				System.out.println("A ewe has been born.");
				ewe.setTimeInGestation(0);
				ewe.setImpregnated(false);
				ewe.setInCoolingPeriod(true);
				pIter.add(new Ewe(0));
			}
		}
	}

	public void femLivestock(Livestock animal){
		if(animal.isInCoolingPeriod()){
			FemaleLivestock fem = (FemaleLivestock) animal;
			fem.setTimeInCoolingPeriod(fem.getTimeInCoolingPeriod() + 1);

			if(fem.getCoolingPeriodLength() == fem.getTimeInCoolingPeriod()){
				fem.setInCoolingPeriod(false);
			}
		} else if((animal.getAgeInMonths() > 36) && !animal.isImpregnated()){
			impregnate(animal);
		}

	}

	public void impregnate(Livestock animal) {

		boolean ramPresent = false;

		for( Livestock a : getLivestock()){
			if (a.getType().equals("ram") && a.getAgeInMonths() > 23 ||
					a.getType().equals("bull") && a.getAgeInMonths() > 23){
				ramPresent = true;
				break;
			}
		}

		Random random = new Random();

		if(ramPresent){
			if(animal.getType().equals("cow")){
				Cow cow = (Cow) animal;
				int numb = random.nextInt(10 - 1 + 1) + 1;
				if (numb <= cow.getChanceToBeImpregnated()) {
					cow.setImpregnated(true);
					cow.setTimeInGestation(0);
				}
			}else if(animal.getType().equals("ewe")){
				Ewe ewe = (Ewe) animal;

				int numb = random.nextInt(10 - 1 + 1) + 1;
				if (numb <= ewe.getChanceToBeImpregnated()) {
					ewe.setImpregnated(true);
					ewe.setTimeInGestation(0);
				}
			}else{
				System.out.println("This animal type cannot be impregnated.");
			}
		} else {
			if(animal.getType().equals("ewe"))
				System.out.println("\n Your flock is unable to expand without a " +
						"ram old enough in it.");
			else
				System.out.println("\n Your herd is unable to expand without a " +
						"bull old enough in it.");
		}
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
		return destroyed;
	}

	public LinkedList<Livestock> getLivestock() {
		return livestock;
	}

	@Override
	public int getCyclesTillHarvest() {
		return 0;
	}

	@Override
	public void setCyclesTillHarvest(int cyclesTillHarvest) {
	}

	@Override
	public int getAmountOfCyclesBeforeHarvest() {
		return 0;
	}

	@Override
	public void setCycleSincePlanted(int daySincePlanted) {
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

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}
}