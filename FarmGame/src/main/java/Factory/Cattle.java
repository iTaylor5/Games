package Factory;

public class Cattle extends Livestock {

    public Cattle(String pType){
        super(pType, 36, 72);
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
