package Factory;

public abstract class Livestock {

    private final String type;
    private int ageInMonths;
    private final int tillFullyFat;
    private boolean fullySized;
    private final int maxAge;
    private boolean impregnated;
    private boolean inCoolingPeriod;

    public Livestock(String pType, int timeTillFat, int lifeSpan) {
        this.type = pType;
        tillFullyFat = timeTillFat;
        maxAge = lifeSpan;
    }

    public String getType() {
        return type;
    }

    public int getAgeInMonths() { return ageInMonths; }

    public void setAgeInMonths(int ageInMonths) { this.ageInMonths = ageInMonths; }

    public boolean isFullySized() { return fullySized; }

    public void setFullySized(boolean fullySized) { this.fullySized = fullySized; }

    public int getTillFullyFat() { return tillFullyFat; }

    public int getMaxAge() { return maxAge; }

    public abstract double getCurrentCostOfAnimal();

    public boolean isImpregnated() {
        return impregnated;
    }

    public void setImpregnated(boolean impregnated) {
        this.impregnated = impregnated;
    }

    public boolean isInCoolingPeriod() {
        return inCoolingPeriod;
    }

    public void setInCoolingPeriod(boolean inCoolingPeriod) {
        this.inCoolingPeriod = inCoolingPeriod;
    }
}
