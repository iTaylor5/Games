package Factory;

public class Sheep extends Livestock {

    public Sheep(String pType){
        super(pType, 24, 48);
    }
    @Override
    public double getCurrentCostOfAnimal() {
        double currentPrice;

        if(getAgeInMonths() < 36){
            double age = getAgeInMonths();
            currentPrice = age * 3;
        } else {
            currentPrice = 15;
        }
        return currentPrice;
    }
}
