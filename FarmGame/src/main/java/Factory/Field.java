package Factory;

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

}