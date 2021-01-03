package Decorator;

import Factory.Field;

import java.util.List;

public class FarmLevelThree extends FarmDecorator {

    public FarmLevelThree(Farm homeFarm) {
        super(homeFarm);
        incrementLevel();
        setBonus(1.25);
        incrementMaxNumOfFields();
    }

    @Override
    public void harvest() {
        this.homeFarm.harvest();
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
    public String getName() {
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
    public int getFarmLevel() {
        return this.homeFarm.getFarmLevel();
    }

    @Override
    public void setFarmLevel(int farmLevel) {
        this.homeFarm.setFarmLevel(farmLevel);
    }

    @Override
    public void setBonus(double bonus) {
        this.homeFarm.setBonus(bonus);
    }

    @Override
    public double getBonus() {
        return this.homeFarm.getBonus();
    }

    @Override
    public double getBank() {
        return this.homeFarm.getBank();
    }

    @Override
    public void setBank(double amount) {
        this.homeFarm.setBank(amount);
    }

    @Override
    public void cycle() {
        this.homeFarm.cycle();
    }

    @Override
    public void incrementLevel() {
        this.homeFarm.incrementLevel();
    }

    @Override
    public void incrementMaxNumOfFields() {
        this.homeFarm.incrementMaxNumOfFields();
    }

    @Override
    public int getMaxNumOfFields() {
       return this.homeFarm.getMaxNumOfFields();
    }

    @Override
    public void setMaxNumOfFields(int maxLevelOfFields) {
        this.homeFarm.setMaxNumOfFields(maxLevelOfFields);
    }
}
