package Factory;

public class CattleField extends LivestockField {

    public CattleField(String type, int numOfCattle) {
        super(type, numOfCattle);
        addBull(new Bull(5));
        addCow(new Cow(3));
        addCow(new Cow(3));
        addCow(new Cow(3));
        addCow(new Cow(3));
        //setProfit( cows.size() * 20 + bull.size() * 20);
        setChanceOfDisease(4); // 40%
    }
}