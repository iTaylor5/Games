package Factory;

public class Ewe extends FemaleLivestock {
//    private final double chanceToBeImpregnated = 7; // 70%
//    private final int pregnantCycle = 6; // the time the cow is pregnant
//    private final int coolingPeriod = 12; // time after dropping calf till it can be impregnated
//    private boolean inCoolingPeriod;
//    private int timePregnant;

    public Ewe(int age){
        super("ewe", 36, 72, 24, 6);
        this.setAgeInMonths(age);
        this.setAgeInMonths(age);
    }

//    public double getChanceToBeImpregnated() {
//        return chanceToBeImpregnated;
//    }

//    public int getTimePregnant() {
//        return timePregnant;
//    }

//    public void setTimePregnant(int timePregnant) {
//        this.timePregnant = timePregnant;
//    }

//    public int getPregnantCycle() {
//        return pregnantCycle;
//    }

    @Override
    public double getCurrentCostOfAnimal() {
        double currentPrice;

        if(getAgeInMonths() < 36){
            currentPrice = (getAgeInMonths()/12) * 3;
        } else {
            currentPrice = 15;
        }
        return currentPrice;
    }
}
