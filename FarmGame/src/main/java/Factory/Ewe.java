package Factory;

public class Ewe extends FemaleLivestock {

    public Ewe(int age){
        super("ewe", 36, 48, 24, 6);
        this.setAgeInMonths(age);
    }

    @Override
    public double getCurrentCostOfAnimal() {
        double currentPrice;

        if(getAgeInMonths() < 36){
            double age = getAgeInMonths();
            currentPrice = (age/12) * 3;
        } else {
            currentPrice = 15;
        }
        return currentPrice;
    }
}
