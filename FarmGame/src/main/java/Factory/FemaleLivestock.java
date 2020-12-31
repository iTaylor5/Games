package Factory;

public class FemaleLivestock extends Livestock {

    private final double chanceToBeImpregnated = 6; // 60%
    private final int gestationLength; // the time the cow is pregnant
    private boolean inCoolingPeriod; // TODO: Implement after cow has given birth.
    private final int coolingPeriodLength; // time after dropping calf till it can be impregnated
    private int timeInGestation;
    private int timeInCoolingPeriod;


    public FemaleLivestock(String pType, int timeTillFat, int lifeSpan,
                           int pCoolingPeriod, int pGestationLen) {

        super(pType, timeTillFat, lifeSpan);
        coolingPeriodLength = pCoolingPeriod; // time after being pregnant
        inCoolingPeriod = false;
        gestationLength = pGestationLen;
    }

    @Override
    public double getCurrentCostOfAnimal() {
        return 0;
    }

    @Override
    public boolean isInCoolingPeriod() {
        return inCoolingPeriod;
    }

    @Override
    public void setInCoolingPeriod(boolean inCoolingPeriod) {
        this.inCoolingPeriod = inCoolingPeriod;
    }

    public int getCoolingPeriodLength() {
        return coolingPeriodLength;
    }
    
    public double getChanceToBeImpregnated() {
        return chanceToBeImpregnated;
    }

    public int getGestationLength() {
        return gestationLength;
    }

    public int getTimeInGestation() {
        return timeInGestation;
    }

    public void setTimeInGestation(int pTimeInGestation) {
        this.timeInGestation = pTimeInGestation;
    }

    public int getTimeInCoolingPeriod() {
        return timeInCoolingPeriod;
    }

    public void setTimeInCoolingPeriod(int timeInCoolingPeriod) {
        this.timeInCoolingPeriod = timeInCoolingPeriod;
    }
}
