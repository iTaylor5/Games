package Factory;

public class Cattle {
    private final int livestockLifeSpan = 72; //72 cycles
    private boolean fullySized;
    private final int tillFullyFat = 36;
    private final String type;
    private final int priceAtSlaughter;
    private int age;
    private double currentCost;

    public Cattle(String pType, int slaughterPrice){
        this.type = pType;
        this.priceAtSlaughter = slaughterPrice;
    }

//    public Cattle(String pType, int slaughterPrice, int age){
//        this.type = pType;
//        this.priceAtSlaughter = slaughterPrice;
//    }

    public String getType() {
        return type;
    }

    public int getPriceAtSlaughter() {
        return priceAtSlaughter;
    }

    public boolean isFullySized() {
        return fullySized;
    }

    public void setFullySized(boolean fullySized) {
        this.fullySized = fullySized;
    }

    public int getTillFullyFat() {
        return tillFullyFat;
    }

    public int getLivestockLifeSpan() {
        return livestockLifeSpan;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getCurrentCostOfAnimal() {
        double currentPrice = 0;

        if(age < 3){
           currentPrice = age * 7;
        } else {
            currentPrice = 21;
        }
        return currentPrice;
    }

//    public void setCurrentCost(double currentCost) {
//        this.currentCost = currentCost;
//    }
}
