package Decorator;

import Factory.Field;

import java.util.List;

public class FarmLevelTwo extends FarmDecorator{

    public FarmLevelTwo(Farm homeFarm){
        super(homeFarm);
        homeFarm.incrementLevel();
    }

    @Override
    public double harvest(){
        return  this.homeFarm.harvest() * 1.1;
    }

    @Override
    public void addField(Field field) {
        this.homeFarm.addField(field);
    }

    @Override
    public List<Field> getFields() {
        return this.homeFarm.getFields();
    }

    @Override
    public String getName(){
        return this.homeFarm.getName();
    }

    @Override
    public void setName(String name) {
        this.homeFarm.setName(name);
    }

    @Override
    public int getNumberOfFields() {
        return this.homeFarm.getNumberOfFields();
    }

    @Override
    public void incrementLevel() {
        this.homeFarm.incrementLevel();
    }

    @Override
    public int getFarmLevel() {
        return this.homeFarm.getFarmLevel();
    }

    @Override
    public void setFarmLevel(int farmLevel) {
        this.homeFarm.setFarmLevel(farmLevel);
    }
}