import Decorator.Farm;
import Decorator.FarmImpl;
import Decorator.FarmLevelTwo;
import Factory.FactoryProvider;
import Factory.Field;
import Mediator.Mediator;

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

        reader = new Scanner(System.in, "UTF-8");  // Reading from System.in
        System.out.println("\n\tRunning the Game...");

        int cycleNUmber = 0;

        boolean gameContinue = true;

        while(gameContinue){
            System.out.println("\n---> This is cycle number: " + cycleNUmber + " <---\n");

            for ( Farm obj : farms){
                System.out.println("FARM NAME: " + obj.getName() + ", farm level is Level " +
                        obj.getFarmLevel());

                if(farm.getFields().size() == 1 ){
                    System.out.println("This farm has " + obj.getNumberOfFields() + " field");
                } else {
                    System.out.println("This farm has " + obj.getNumberOfFields() + " fields");
                }

                for(Field field : obj.getFields()){
                    System.out.println("\n************************************** \n");
                    System.out.println("Field type: " + field.getType());
                    System.out.println("Profit of a harvest is: " + field.getProfit());
                    System.out.println("Chance of a disease is: " + field.getChanceOfDisease()*10 +
                            "%");
                    System.out.println();
                }

                if((obj.getFields().size() < 2) && (getGold() >= 50)){
                    System.out.println("(yes/no) This farm only has room to add another field. " +
                            "Would you like to do this?");

                    String response = reader.nextLine();

                    if(response.equalsIgnoreCase("yes")){
                        createField(obj);
                    }
                }
            }

            System.out.println("\n************************************** \n");
            cycle();
            System.out.println("\n************************************** \n");
            // TODO: need to implement
            /* Everything 10th go give option to buy farm. */
            if ((cycleNUmber % 10) == 0 && (gold >= 200)){
                System.out.println("(yes/no) Would you like to buy a farm and " +
                        "extend the empire.");
                String response = reader.nextLine();
                if(response.equalsIgnoreCase("yes")){
                    // TODO: need to implement
                    break;
                }
            }
            gameContinue = checkIfGameOver();
            cycleNUmber++;
        }
        System.out.println("! ! ! ! ! ! ! ! ! ! THE IS GAME OVER ! ! ! ! ! ! ! ! ! !");
//        Farm upGradedFarm = new FarmLevelTwo(farm);
//        System.out.println("Farm level is: " + upGradedFarm.getFarmLevel());
//        System.out.println("Harvest of upgraded farm is: " + upGradedFarm.harvest());
    }

    /**
     * Simply checks if there are any  fields where the money can be generated.
     * @return
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
        for ( Farm obj : farms){
            int index = 0;
            for(Field field : obj.getFields()){
                if (field.isDiseased()){
                    System.out.println(field.getType() + " is diseased!");
                    System.out.println("(yes/no) would you like to repair it?");
                    String response = reader.nextLine();
                    if(response.equalsIgnoreCase("yes")) {
                        if (gold >= 8 ){
                            gold = gold - 8;
                            field.setIsDiseased(false);
                            System.out.println("You have cured the disease");
                            field.cycleInfo();
                        }else {
                            System.out.println("\n !!! You do not have enough money !!!");
                            obj.getFields().remove(index);
                        }
                    } else{
                        System.out.println("This crop will be dead tomorrow.");
                        obj.getFields().remove(index);
                    }
                } else {
                    field.cycleInfo();
                }
                index++;
            }
            gold += obj.harvest();
        }
        System.out.println("\n\t$$$ Your gold is: " + gold + " $$$\n");
    }

    public void buyFarm(){
        // TODO: need to implement
    }

    public void upGradeFarm(Farm farmToUpgrade){
        // TODO: need to implement
    }

    public void createFirstFarm(){
        Scanner reader = new Scanner(System.in, "UTF-8");  // Reading from System.in

        System.out.println("We shall beginning the game with 200 gold!!!\n");

        System.out.println("Lets create a Farm");
        System.out.println("Please enter a name for the farm...");
        String farmName = reader.nextLine();
        Farm originalFarm = new FarmImpl(this, farmName);
        System.out.println("Your farm " + originalFarm.getName() + " has been created!\n");
        System.out.println("~~~ Farm Level One is allowed 2 fields ~~~");
        System.out.println("It will cost you 100 gold to prepare these two fields.\n");
        System.out.println("---------------------------------------- ");


        for (int i = 0; i < 2; i++){
            System.out.println("Let's chose the next field type.");
            createField(originalFarm);
        }

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

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public List<Farm> getFarms() {
        return farms;
    }

    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }

    /** Added with the implements Mediator*/
    @Override
    public double harvest() {
        return 0;
    }

    @Override
    public void addField(Field field) {

    }

    @Override
    public List<Field> getFields() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public int getNumberOfFields() {
        return 0;
    }

    @Override
    public void addFarm(Farm farm) {
        farms.add(farm);
    }

}
