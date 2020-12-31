package Factory;

// Cattle
public class Cow extends FemaleLivestock {

//    private final double chanceToBeImpregnated = 6; // 60%
//    private final int pregnantCycle = 6; // the time the cow is pregnant
//    private final int coolingPeriod = 12; // time after dropping calf till it can be impregnated
//    private boolean inCoolingPeriod; // TODO: Implement after cow has given birth.
//    private int timePregnant;


//    public Cow(){
//        super("cow");
//    }

    public Cow(int age){
        super("cow", 36, 72, 36, 9);
        this.setAgeInMonths(age);
    }

    @Override
    public double getCurrentCostOfAnimal() {

        double currentPrice;

        if(getAgeInMonths() < 36){
            currentPrice = (getAgeInMonths()/12) * 7;
        } else {
            currentPrice = 21;
        }
        return currentPrice;
    }

}
