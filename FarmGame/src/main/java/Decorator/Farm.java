package Decorator;

import Factory.Field;

import java.util.List;

public interface Farm {
    double harvest();
    //void harvest();

    void addField(Field field);

    List<Field> getFields();

    String getName();

    void setName(String name);

    int getNumberOfFields();

    void incrementLevel();

    int getFarmLevel();

    void setFarmLevel(int farmLevel);

}
//public abstract class Farm {
//    double harvest();
//    void addField(Field field);
//    List<Field> getFields();
//    String getName();
//    void setName(String name);
//    int getNumberOfFields();
//
//}