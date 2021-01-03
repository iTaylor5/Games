import Decorator.Farm;
import Decorator.FarmImpl;
import Decorator.FarmLevelThree;
import Decorator.FarmLevelTwo;
import Factory.*;
import Mediator.Mediator;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayGame implements Mediator {

    private double gold = 200;
    private List<Farm> farms;
    private Scanner reader;

    public PlayGame(){
        farms = new LinkedList<>();
    }

    public void runGame(Farm farm) {

        reader = new Scanner(System.in, StandardCharsets.UTF_8);  // Reading from System.in

        System.out.println("\n\t...Running the Game...\n");

        System.out.println("PLEASE NOTE: The system will auto repair until you have less " +
                "than 12 gold remaining");

        int cycleNUmber = 1;

        boolean gameContinue = true;

        while(gameContinue){
            System.out.print("\n---> Beginning cycle number: " + cycleNUmber);
            System.out.println("\t :: Total gold: " + getGold() + " <---\n");

            for (Farm f : farms) {

                /* Print out farm information */
                System.out.println("FARM NAME: " + f.getName() +
                        " -> Level: " + f.getFarmLevel());
                if (farm.getFields().size() == 1) {
                    System.out.println("This farm has " + f.getNumberOfFields() +
                            " field");
                } else {
                    System.out.println("This farm has " + f.getNumberOfFields() +
                            " fields");
                }

                /* Check amount of fields */
                if ((f.getFields().size() < f.getMaxNumOfFields()) && (getGold() >= 50)) {
                    System.out.println("(yes/no) This farm has room to add another field " +
                            "would you like to do this?");
                    reader.nextLine();
                    String response = reader.nextLine();

                    if (response.equalsIgnoreCase("yes")) {
                        createField(f);
                    } else {
                        System.out.println("Maybe next time...");
                    }
                }

                System.out.println(" %% Summary of this farm's fields: %% ");

                for (Field field : f.getFields()) {
                    System.out.println("\n************************************** \n");
                    System.out.println("Field type: " + field.getType());
                    System.out.println("Profit of a harvest is: " + field.getProfit() *
                            f.getBonus());
                    System.out.println("Chance of a disease is: " +
                            field.getChanceOfDisease() * 10 + "%");
                    System.out.println();
                }
            }

            if(getGold() > 80){
                System.out.println("## You have enough money to upgrade one of your farms. ##\n");
                int count = 0;
                for(Farm f : farms){
                    System.out.print("(" + count + ")");
                    System.out.print(" -> " + f.getName());
                    System.out.print("-> Current level: "+ f.getFarmLevel());
                    count++;
                }
                System.out.println(". It will cost you 200 gold coins.\n");

                System.out.println("Enter the corresponding number for the farm you wish " +
                        "to upgrade or -1 to skip.");

                int  number = reader.nextInt();
                if(number == -1){
                    System.out.println("OK maybe next time.");
                }else if(number >= 0 && number < farms.size()){
                    Farm newFarm = upGradeFarm(farms.get(number));
                    farms.remove(number);
                    farms.add(number, newFarm);
                    setGold(getGold() - 80);
                    System.out.println("~~~ Congratulations your upgrade was successful ~~~");
                }else {
                    System.out.println("Invalid input.");
                }

            }

            System.out.println("\n************************************** \n");
            System.out.println("Beginning a cycle...");
            cycle();
            System.out.println("\n************************************** \n");
            // TODO: need to implement
            /* Everything 10th go give option to buy farm. */
            if ((cycleNUmber % 10) == 0 && (gold >= 200)){
                System.out.println("(yes/no) Would you like to buy a farm and " +
                        "extend the empire.");
                String response = reader.nextLine();
                if(response.equalsIgnoreCase("yes")){
                    createFarm();
                    setGold(getGold()-200);
                }
            }
            gameContinue = checkIfGameOver();
            cycleNUmber++;

//            if (getGold() > 200) {
//                System.out.println("(yes/no) Would you like to invest in a new farm?");
//                String repsonse = reader.nextLine();
//
//                if(repsonse.equalsIgnoreCase("yes"))
//
//            }
        }
        System.out.println("! ! ! ! ! ! ! ! ! ! THE IS GAME OVER ! ! ! ! ! ! ! ! ! !");
    }

    /**
     * Simply checks if there are any  fields where the money can be generated.
     * @return ture to continue game and false for game over.
     */
    public boolean checkIfGameOver(){

        boolean gameContinue = false;

        for(Farm farm : farms){
            if(farm.getFields().size() > 0){
                gameContinue = true;
            }
        }
        return gameContinue;
    }

    public void cycle(){

        // Each farm has its own cycle.
        // It calls the dayCycle() and the nightCycle() in the farm class
        for ( Farm obj : farms){
            obj.cycle();
            obj.harvest();
        }

        System.out.println("\n\t$$$ Your gold is: " + gold + " $$$\n");
    }

    @Override
    public void buyAnimals() {
        // TODO: fix
    }


    public void buyFarm(){

    }

    public Farm upGradeFarm(Farm farmToUpgrade){
        Farm newFarm = null;
        if(farmToUpgrade.getFarmLevel() == 1){
            System.out.println("Upgrading....");
            newFarm = new FarmLevelTwo(farmToUpgrade);
            System.out.println("+++++");
            System.out.println(newFarm.getMaxNumOfFields());
            System.out.println(newFarm.getFarmLevel());
            System.out.println("+++++");
        } else if(farmToUpgrade.getFarmLevel() == 2) {
            System.out.println("Upgrading....");
            newFarm = new FarmLevelThree(farmToUpgrade);
        }

        return newFarm;
    }

    public void createFarm(){
        Scanner reader = new Scanner(System.in, "UTF-8");  // Reading from System.in

        System.out.println("Lets create a Farm");
        System.out.println("Please enter a name for the farm...");
        String farmName = reader.nextLine();
        Farm originalFarm = new FarmImpl(this, farmName);
        System.out.println("Your farm " + originalFarm.getName() + " has been created!\n");
        System.out.println("~~~ Farm Level One is allowed 2 fields ~~~");
        System.out.println("It will cost you 100 gold to prepare these two fields.\n");
        System.out.println("---------------------------------------- ");

        for (int i = 0; i < 2; i++) {
            createField(originalFarm);
        }


        System.out.println("---------------------------------------- ");
        System.out.println("Farm has been create and your two fields chosen, " +
                "lets begin the game.");
        runGame(originalFarm);
    }

    public void createField(Farm farm){
        Scanner reader = new Scanner(System.in, "UTF-8");  // Reading from System.in

        System.out.println("Lets create a field for -> " + farm.getName());

        String farmType = "";
        String farmSpecialty = "";
        boolean incorrect = true;

        while (incorrect) {

            System.out.println("Please choose a field type: ");
            System.out.println("crop or livestock");

            farmType = reader.nextLine();

            if (farmType.equals("crop")) {
                System.out.println("Choose type of crop: wheat - maize ");

                farmSpecialty = reader.nextLine().toLowerCase();

                if (farmSpecialty.equals("wheat") || farmSpecialty.equals("maize")) {
                    incorrect = false;
                }

            } else if (farmType.equals("livestock")) {
                System.out.println("Choose type of livestock: cattle - sheep");

                farmSpecialty = reader.nextLine().toLowerCase();

                if (farmSpecialty.equals("cattle") || farmSpecialty.equals("sheep")) {
                    incorrect = false;
                }

            } else {
                System.out.println("Invalid input try again...");
            }
        }

        /*Farms can be of different types. */
        Field field = FactoryProvider.getFactory(farmType).create(farmSpecialty);
        //System.out.println("The field that was created is: " + field.getType());
        farm.addField(field);

        System.out.println( farm.getName() + " now has " + farm.getNumberOfFields() + " field.\n");
        setGold(getGold()-50);

    }

    @Override
    public double getGold() {
        return gold;
    }

    @Override
    public void setGold(double gold) {
        this.gold = gold;
    }

    @Override
    public List<Farm> getFarms() {
        return farms;
    }

    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }

    @Override
    public void harvest(Farm farm) {
        setGold(getGold() + farm.getBank());
        farm.setBank(0);
    }

    @Override
    public void addFarm(Farm farm) {
        farms.add(farm);
    }
}
