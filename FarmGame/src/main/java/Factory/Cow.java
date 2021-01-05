package Factory;

public class Cow extends FemaleLivestock {

    public Cow(int age){
        super("cow", 36, 72, 36, 9);
        this.setAgeInMonths(age);
    }

    @Override
    public double getCurrentCostOfAnimal() {

        double currentPrice;

        if(getAgeInMonths() < 36){
            double age = getAgeInMonths();
            currentPrice = age/12 * 7;
        } else {
            currentPrice = 21;
        }
        return currentPrice;
    }

}
