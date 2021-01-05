package Decorator;

import Factory.*;
import Mediator.Mediator;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FarmImpl implements Farm {

    private Mediator mediator;
    private List<Field> farmFields;
    private String name;
    private double bank;
    private double bonus;
    private Scanner reader;

    private int farmLevel;
    private int maxNumOfFields;

    public FarmImpl(Mediator newMediator, String farmName){
        farmFields = new LinkedList<>();
        this.name = farmName;
        this.mediator = newMediator;
        mediator.addFarm(this);
        farmLevel = 1;
        maxNumOfFields = 2;
        setBonus(1);
        reader = new Scanner(System.in, StandardCharsets.UTF_8);

    }

    public void harvest(){

        double turnOver = 0;
        double fromFieldsAccount = 0;

        for(Field field : farmFields){
            if(field.canHarvest() && (field.getType().equals("wheat") ||
                    field.getType().equals("maize"))){

                turnOver += field.getProfit();
                field.setHarvest(false);
            }else if(field.canHarvest() && (field.getType().equals("cattle") ||
                    field.getType().equals("sheep"))){

                System.out.println("Slaughtering....");
                System.out.println("Number of animals to be slaughtered is: " +
                        field.getAnimalsToBeSlaughtered().size());

                for(Livestock animal : field.getAnimalsToBeSlaughtered()) {
                    turnOver += animal.getCurrentCostOfAnimal();
                }
                field.getAnimalsToBeSlaughtered().clear();

                field.setHarvest(false);
            }
            fromFieldsAccount += field.getAccount();
            field.setAccount(0);
        }

        turnOver = turnOver * bonus;

        turnOver += fromFieldsAccount;

        setBank(getBank() + turnOver);

        mediator.harvest(this);
    }

    /**
     * This method runs through all the fields of the farm.
     * Sorts out if they are diseased.
     * For live stock fields you have the chance to buy or slaughter animals.
     */
     public void cycle(){
        int index = 0;

         System.out.println("\t ~Sunrise~");

        for(Field field : getFields()){

            if (field.isDiseased()){

                if(mediator.getGold() >= 12){
                    mediator.setGold(mediator.getGold() - 8);
                    field.setIsDiseased(false);
                    System.out.println("You have cured the field of disease");
                    field.cycleInfo();
                } else {
                    System.out.println(field.getType() + " is diseased!");
                    System.out.println("(yes/no) would you like to repair it?");
                    String response = reader.nextLine();
                    if(response.equalsIgnoreCase("yes")) {
                        if (mediator.getGold() >= 8 ){
                            mediator.setGold(mediator.getGold() - 8);
                            field.setIsDiseased(false);
                            System.out.println("You have cured the field of disease");
                            field.cycleInfo();
                        } else {
                            System.out.println("\n !!! You do not have enough money !!!");
                            getFields().remove(index);
                        }
                    } else{
                        System.out.println("This crop will be dead tomorrow.");
                        getFields().remove(index);
                    }
                }
            } else {
                field.cycleInfo();

                if(field.getType().equals("wheat") || field.getType().equals("maize")){
                    if (field.getCyclesTillHarvest() == 0 && !field.isDestroyed()) {
                        System.out.println("Time to harvest!!!");
                        field.setHarvest(true);
                        field.setCyclesTillHarvest(field.getAmountOfCyclesBeforeHarvest());
                        field.setCycleSincePlanted(0);
                    }
                }else {
                    System.out.println("(1) Would you like to buy more " + field.getType() + "?");
                    System.out.println("(2) Would you like to slaughter " + field.getType() + "?");
                    System.out.println("(3) Continue");
                    int response = reader.nextInt();
                    if(response == 1){
                        buyAnimals(field);
                    } else if(response == 2){
                        slaughterAnimals(field);
                    }
                }
                System.out.println("\t ~sunset~");
            }
            field.nightCycle();
            index++;
        }
    }

    public void buyAnimals(Field field){

        boolean continueBuying = false;

        if(mediator.getGold() >= 21){
            continueBuying = true;
        } else {
            System.out.println("You do not have enough money!");
        }

        while(continueBuying){

            System.out.println("\n+++++ Welcome to Harrods market places +++++");
            System.out.println("(-1) to exit");
            if(field.getType().equals("sheep")){
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
                System.out.println("You chose to exit.");
                continueBuying = false;
            } else if(field.getType().equals("sheep")){
                if(response == 1){
                    field.getLivestock().add(new Ewe(15));
                    mediator.setGold(mediator.getGold() - 21);
                    System.out.println("Congrats on  you new purchase of a: " +
                            "new 15 month old ewe.");
                } else if(response == 2){
                    if(mediator.getGold() >= 25){
                        field.getLivestock().add(new Ewe(25));
                        mediator.setGold(mediator.getGold() - 25);
                        System.out.println("Congrats on  you new purchase of a: " +
                                "new 25 month old ewe.");
                    }else{
                        System.out.println("You do not have enough gold.");
                    }
                } else if(response == 3){
                    field.getLivestock().add(new Ram(15));
                    mediator.setGold(mediator.getGold() - 21);
                    System.out.println("Congrats on  you new purchase of a: " +
                            "new 15 month old ram.");
                } else {
                    if(mediator.getGold() >= 25){
                        field.getLivestock().add(new Ram(25));
                        mediator.setGold(mediator.getGold() - 25);
                        System.out.println("Congrats on  you new purchase of a: " +
                                "new 25 month old ram.");
                    }else{
                        System.out.println("You do not have enough gold.");
                    }
                }
            }else {
                if(response == 1 ){
                    field.getLivestock().add(new Cow(15));
                    mediator.setGold(mediator.getGold() - 21);
                    System.out.println("Congrats on  you new purchase of a: " +
                            "new 15 month old cow.");
                } else if(response == 2){
                    if(mediator.getGold() >= 25){
                        field.getLivestock().add(new Cow(25));
                        mediator.setGold(mediator.getGold() - 25);
                        System.out.println("Congrats on  you new purchase of a: " +
                                "new 25 month old cow.");
                    }else{
                        System.out.println("You do not have enough gold.");
                    }
                } else if(response == 3){
                    field.getLivestock().add(new Bull(15));
                    mediator.setGold(mediator.getGold() - 21);
                    System.out.println("Congrats on  you new purchase of a: " +
                            "new 21 month old bull.");
                } else {
                    if(mediator.getGold() >= 25){
                        field.getLivestock().add(new Bull(25));
                        mediator.setGold(mediator.getGold() - 25);
                        System.out.println("Congrats on  you new purchase of a: " +
                                "new 25 month old bull.");
                    }else{
                        System.out.println("You do not have enough gold.");
                    }
                }
            }
            if(mediator.getGold() < 21){
                continueBuying = false;
            }
        }
    }

    public void slaughterAnimals(Field field){
        while(true){
            System.out.println("\nEnter the number corresponding to the livestock you would " +
                    "like to sell or -1 to exit.");
            field.printInfo();
            int num = reader.nextInt();
            if(num == -1){
                break;
            }else if(num >= 0 && num <= field.getLivestock().size()-1){
                Livestock animal = field.getLivestock().get(num);
                field.getLivestock().remove(num);

                System.out.println("We will slaughter this " + animal.getType() +
                        " and the price is " + animal.getCurrentCostOfAnimal());

                field.addAnimalToBeSlaughtered(animal);
                field.setHarvest(true);
            }else {
                System.out.println("Invalid input.");
            }
        }
    }


    public void addField(Field field){
        farmFields.add(field);
    }

    @Override
    public List<Field> getFields() {
        return farmFields;
    }

    public int getNumberOfFields(){
        return farmFields.size();
    }

    @Override
    public void incrementLevel() {
        setFarmLevel(getFarmLevel()+1);
    }

    @Override
    public void incrementMaxNumOfFields() {
        setMaxNumOfFields(getMaxNumOfFields() + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFarmLevel() {
        return farmLevel;
    }

    public void setFarmLevel(int farmLevel) {
        this.farmLevel = farmLevel;
    }

    public double getBank() {
        return bank;
    }

    public void setBank(double amount) {
        this.bank = amount;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getMaxNumOfFields() {
        return maxNumOfFields;
    }

    public void setMaxNumOfFields(int maxLevelOfFields) {
        this.maxNumOfFields = maxLevelOfFields;
    }
}
