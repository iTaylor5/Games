package Decorator;

import Factory.Field;

import java.util.List;

public interface Farm {

    void harvest();

    void addField(Field field);

    List<Field> getFields();

    String getName();

    void setName(String name);

    int getNumberOfFields();

    void incrementLevel();

    int getFarmLevel();

    void setFarmLevel(int farmLevel);

    void setBonus(double bonus);

    double getBonus();

    double getBank();

    void setBank(double amount);

    void cycle();

    void incrementMaxNumOfFields();

    int getMaxNumOfFields();

    void setMaxNumOfFields(int maxLevelOfFields);

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