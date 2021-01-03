package Factory;

import java.util.LinkedList;

public interface Field {

    String getType();
    double getProfit();
    void setProfit(double profit);
    double getChanceOfDisease();
    void setChanceOfDisease(double chanceOfDisease);
    void cycleInfo();
    boolean canHarvest();
    void setHarvest(boolean pHarvest);
    boolean isDiseased();
    void setIsDiseased(boolean pDiseased);
    void catchDisease();
    boolean isDestroyed();
    LinkedList<Livestock> getAnimalsToBeSlaughtered();
    void addAnimalToBeSlaughtered(Livestock animal);
    double getAccount();
    void setAccount(double account);
    //void dayCycle();
    void nightCycle();
    void printInfo();
    LinkedList<Livestock> getLivestock();

    int getCyclesTillHarvest();
    void setCyclesTillHarvest(int cyclesTillHarvest);
    int getAmountOfCyclesBeforeHarvest();
    void setCycleSincePlanted(int daySincePlanted);
}