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

    private double gold = 100;
    private List<Farm> farms;

    public PlayGame(){
        farms = new LinkedList<>();
    }

    public void runGame(Farm farm){

        System.out.println("In runGame");

        System.out.println("~~" + farm.getName() + "~~");

        for ( Farm obj : farms){
            for(Field field : obj.getFields()){
                System.out.println("*************************");
                System.out.println(field.getType());
                System.out.println("Profit of a harvest is: " + field.getProfit());
                System.out.println("Chance of a disease is: " + field.getChanceOfDisease());

                System.out.println();
            }
        }

        System.out.println("Harvest of current farm is: " + farm.harvest());

        System.out.println("\n ----- Let's test the upGrade -----\n");

        Farm upGradedFarm = new FarmLevelTwo(farm);

        System.out.println("Harvest of upgraded farm is: " + upGradedFarm.harvest());

//        for(Field field : upGradedFarm.getFields()){
//            System.out.println("*************************");
//            System.out.println(field.getType());
//            System.out.println("Profit of a harvest is: " + field.getCycleProfit());
//            System.out.println("Chance of a disease is: " + field.getChanceOfDisease());
//
//            System.out.println();
//        }

    }

    public void upGradeFarm(Farm farmToUpgrade){


    }

    public void createFirstFarm(){
        Scanner reader = new Scanner(System.in, "UTF-8");  // Reading from System.in


        System.out.println("Beginning the game!!!");

        System.out.println("Lets create a Farm");
        System.out.println("Please enter a name for the farm...");
        String farmName = reader.nextLine();
        Farm originalFarm = new FarmImpl(this, farmName);
        System.out.println("Your farm " + originalFarm.getName() + " has been created!\n");
        System.out.println("~~~ Farm Level One has 2 fields, chose your field types ~~~");

        for (int i = 0; i < 2; i++){
            createField(originalFarm);
            System.out.println("Let's chose the next field type.");
        }

        System.out.println("Farm has been create and your two fields chosen, lets begin the game.");
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
        System.out.println("The field that was created is: " + field.getType());
        farm.addField(field);

        System.out.println( farm.getName() + " now has " + farm.getNumberOfFields() + " field.\n");

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
