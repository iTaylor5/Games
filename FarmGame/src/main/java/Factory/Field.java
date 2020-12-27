package Factory;

public interface Field {
    String getType();
    double getProfit();
    void setProfit(double profit);
    double getChanceOfDisease();
    void setChanceOfDisease(double chanceOfDisease);
}