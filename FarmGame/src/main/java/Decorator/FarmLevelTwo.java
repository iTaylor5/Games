package Decorator;

import Factory.Field;

import java.util.List;

public class FarmLevelTwo extends FarmDecorator{

    public FarmLevelTwo(Farm homeFarm){

        super(homeFarm);
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
}