package Decorator;

import Factory.Field;

import java.util.List;

public interface Farm {
    double harvest();

    void addField(Field field);

    List<Field> getFields();

    String getName();

    void setName(String name);

    int getNumberOfFields();

}


//public abstract class Farm {
//    double harvest();
//
//    void addField(Field field);
//
//    List<Field> getFields();
//
//    String getName();
//
//    void setName(String name);
//
//    int getNumberOfFields();
//
//}
