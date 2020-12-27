package Mediator;

import Decorator.Farm;
import Factory.Field;

import java.util.List;

public interface Mediator {
    double harvest();

    void addField(Field field);

    List<Field> getFields();

    String getName();

    void setName(String name);

    int getNumberOfFields();

    void addFarm(Farm farm);


}
