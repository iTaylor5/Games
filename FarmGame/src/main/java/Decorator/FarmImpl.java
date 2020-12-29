package Decorator;

import Factory.Field;
import Mediator.Mediator;

import java.util.LinkedList;
import java.util.List;

public class FarmImpl implements Farm {

    private Mediator mediator;
    private List<Field> farmFields;
    private String name;
    private int farmLevel;
    private double bank;
    private double bonus;

    public FarmImpl(Mediator newMediator, String farmName){
        farmFields = new LinkedList<>();
        this.name = farmName;
        this.mediator = newMediator;
        mediator.addFarm(this);
        farmLevel = 1;
        setBonus(1);
    }

//    @Override
//    public double harvest() {
//
//        mediator.harvest(this);
////        double turnOver = 0;
////
////        for(Field field : farmFields){
////            if(field.canHarvest()){
////                turnOver += field.getProfit();
////                field.setHarvest(false);
////            }
////        }
////
////        return turnOver;
//    }

    public void harvest(){

        double turnOver = 0;

        for(Field field : farmFields){
            if(field.canHarvest()){
                turnOver += field.getProfit();
                field.setHarvest(false);
            }
        }
        System.out.println("In harvest turn over is now: " + turnOver);
        turnOver = turnOver * bonus;
        System.out.println("In harvest turn over is now: " + turnOver);

        setBank(getBank() + turnOver);

        System.out.println("&& Bank is now: " + getBank());
        mediator.harvest(this);
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
}
