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
		System.out.println("This fields livestock information:\n");
		for(Livestock animal : livestock){
			//boolean nearingOldAge = false;
			System.out.print("Number: " + count);
			System.out.print(", type: " + animal.getType());
			System.out.print(", price at slaughter: " + animal.getCurrentCostOfAnimal());
			System.out.print(", fully sized: " + animal.isFullySized());
			System.out.print(", age in months: " + animal.getAgeInMonths());
			System.out.print(", pregnant: " + animal.isImpregnated());
			System.out.println(", in cooling period: " + animal.isInCoolingPeriod());

			if(animal.isImpregnated()){
				if(animal.getType().equals("cow") || animal.getType().equals("ewe")){
					FemaleLivestock femLiv = (FemaleLivestock) animal;
					System.out.println("Time pregnant, : " + femLiv.getTimeInGestation());
				}
			}

			// TODO: implement random could die any time...
			if( animal.getMaxAge() - animal.getAgeInMonths() < 6 ) {
				System.out.println(" ** This animal is showing signs of old age! **");
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
		printLivestockInfo();

		System.out.println("Does this field have disease: " + diseased);
		System.out.println("Is this field destroyed: " + isDestroyed());

		dayCycle();
		nightCycle();

	}

	public void dayCycle(){
		System.out.println("\t ~Sunrise~");
		System.out.println("(1) Would you like to buy more " + getType() + "?");
		System.out.println("(2) Would you like to slaughter " + getType() + "?");
		System.out.println("(3) Continue");
		int response = reader.nextInt();
		if(response == 1){
			buyAnimals();
		} else if(response == 2){
			slaughterAnimals();
		} else {
			System.out.println("Good night");
		}
	}

	public void nightCycle(){

		ListIterator<Livestock> iter = getLivestock().listIterator();

		while(iter.hasNext()){

			Livestock animal = iter.next();

			incrementAnimalsAge(animal);

			if(animal.getAgeInMonths() == animal.getMaxAge()){
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

		System.out.println("\t ~sunset~");
		catchDisease();
	}

	public void buyAnimals(){

//		if(getType().equals("sheep")){
//			System.out.println("(1) A Ewe or (2) Ram");
//		} else {
//			System.out.println("(1) A Cow or (2) Bull");
//		}
//
//		int response = reader.nextInt();
//
		boolean continueBuying = true;
		while(continueBuying){
			System.out.println("\n+++++ Welcome to Harrods market places +++++");
			System.out.println("(-1) to exit");
			if(getType().equals("sheep")){
				System.out.println("Ewe prices are: ");
				System.out.println("(1) 12-24 months is 21 gold");
				System.out.println("(2) 24-36 months is 25 gold");
				System.out.println("Ram prices are: ");
				System.out.println("(3) 12-24 months is 21 gold");
				System.out.println("(4) 24-36 months is 30 gold");
			} else {
				System.out.println("Cow prices are: ");
				System.out.println("(1) 12-24 months is 21 gold");
				System.out.println("(2) 24-36 months is 25 gold");
				System.out.println("Bull prices are: ");
				System.out.println("(3) 12-24 months is 21 gold");
				System.out.println("(4) 24-36 months is 30 gold");
			}

			int response = reader.nextInt();

			if(response == -1){
				continueBuying = false;
			} else if(getType().equals("sheep")){
				if(response == 1){
					getLivestock().add(new Ewe(15));
					setAccount(getAccount() - 21);
				} else if(response == 2){
					getLivestock().add(new Ewe(25));
					setAccount(getAccount() - 25);
				} else if(response == 3){
					getLivestock().add(new Ram(15));
					setAccount(getAccount() - 21);

				} else {
					getLivestock().add(new Ram(25));
					setAccount(getAccount() - 25);
				}

			}else {
				if(response == 1 ){
					getLivestock().add(new Cow(15));
					setAccount(getAccount() - 21);
				} else if(response == 2){
					getLivestock().add(new Cow(25));
					setAccount(getAccount() - 25);
				} else if(response == 3){
					getLivestock().add(new Bull(15));
					setAccount(getAccount() - 21);

				} else {
					getLivestock().add(new Bull(25));
					setAccount(getAccount() - 25);
				}
			}

		}

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
				//calfBorn = true;
				cow.setTimeInGestation(0);
				cow.setImpregnated(false);
				//numOfCalvesBorn++;
				cow.setInCoolingPeriod(true);
				pIter.add(new Cow(0));
			}
		}else if(animal.getType().equals("ewe")){
			Ewe ewe = (Ewe) animal;
			ewe.setTimeInGestation(ewe.getTimeInGestation() + 1);

			if(ewe.getTimeInGestation() == ewe.getGestationLength()){
				System.out.println("A ewe has been born.");
				//eweBorn = true;
				ewe.setTimeInGestation(0);
				ewe.setImpregnated(false);
				//numOfEweBorn++;
				ewe.setInCoolingPeriod(true);
				pIter.add(new Ewe(0));
			}
		}
	}

	public void femLivestock(Livestock animal){
//		boolean calfBorn = false;
//		int numOfCalvesBorn = 0;
//
//		boolean eweBorn = false;
//		int numOfEweBorn = 0;
//		int count = 0;
//		int numOfDeaths = 0;
//		boolean death = false;


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

	public void impregnate(Livestock animal){

		// TODO: Ensure there is a RAM or BULL in herd

		Random random = new Random();

		if(animal.getType().equals("cow")){
			Cow cow = (Cow) animal;
			int numb = random.nextInt(10 - 1 + 1) + 1;
			if (numb <= cow.getChanceToBeImpregnated()) {
				System.out.println("Impregnated");
				cow.setImpregnated(true);
				cow.setTimeInGestation(0);
			}
		}else if(animal.getType().equals("ewe")){
			Ewe ewe = (Ewe) animal;

			int numb = random.nextInt(10 - 1 + 1) + 1;
			if (numb <= ewe.getChanceToBeImpregnated()) {
				System.out.println("Impregnated");
				ewe.setImpregnated(true);
				ewe.setTimeInGestation(0);
			}
		}else{
			System.out.println("This animal type cannot be impregnated.");
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

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}
}