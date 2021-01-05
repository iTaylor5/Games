package Mediator;

import Decorator.Farm;
import Factory.Field;

import java.util.List;

public interface Mediator {

    void harvest(Farm farm);

    double getGold();

    void setGold(double gold);

    void addFarm(Farm farm);

    List<Farm> getFarms();

    void cycle();

    //public void buyAnimals();
}
