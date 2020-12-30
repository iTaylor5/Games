package Factory;

public class Cow extends Cattle {

    private final double chanceToBeImpregnated = .6; // 60%
    private final int pregnantCycle = 6; // the time the cow is pregnant
    private boolean impregnated;
    private final int coolingPeriod = 12; // time after dropping calf till it can be impregnated

    public Cow(){
        super("cow", 21);
    }

    public Cow(int age){
        super("cow", 21);
        this.setAge(age);
    }

    public boolean isImpregnated() {
        return impregnated;
    }

    public void setImpregnated(boolean impregnated) {
        this.impregnated = impregnated;
    }
}
