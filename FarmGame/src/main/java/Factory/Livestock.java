package Factory;

public abstract class Livestock {

    private final String type;
    private final int priceAtSlaughter;
    private int age;
    private final int tillFullyFat;
    private boolean fullySized;
    private final int livestockLifeSpan;

    public Livestock(String pType, int slaughterPrice, int timeTillFat, int lifeSpan){
        this.type = pType;
        this.priceAtSlaughter = slaughterPrice;
        tillFullyFat = timeTillFat;
        livestockLifeSpan = lifeSpan;
    }

    public String getType() {
        return type;
    }
//    public void setType(String pType) {
//        this.type = pType;
//    }
//    public void setPriceAtSlaughter(int price) {
//        this.priceAtSlaughter = price;
//    }
    public int getPriceAtSlaughter() {
        return priceAtSlaughter;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public abstract double getCurrentCostOfAnimal();
}
