package Factory;

public class Ewe extends FemaleLivestock {

    public Ewe(int age){
        super("ewe", 36, 72, 24, 6);
        this.setAgeInMonths(age);
        this.setAgeInMonths(age);
    }

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
