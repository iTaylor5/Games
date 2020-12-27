package Decorator;

import Factory.Field;
import Mediator.Mediator;

import java.util.LinkedList;
import java.util.List;

public class FarmImpl implements Farm {

    private Mediator mediator;
    private List<Field> farmFields;
    private String name;
    public int farmLevel;

    public FarmImpl(Mediator newMediator, String farmName){
        farmFields = new LinkedList<>();
        this.name = farmName;
        this.mediator = newMediator;

        mediator.addFarm(this);

        farmLevel = 1;
    }

    public FarmImpl(Mediator newMediator){
        farmFields = new LinkedList<>();
        this.name = "OriginalFarm";
        this.mediator = newMediator;

        mediator.addFarm(this);

        farmLevel = 1;

    }

    @Override
    public double harvest() {
        double turnOver = 0;

        for(Field field : farmFields){
            turnOver += field.getProfit();
        }

        return turnOver;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
