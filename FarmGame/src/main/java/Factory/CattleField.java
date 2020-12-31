package Factory;

public class CattleField extends LivestockField {

    public CattleField(String type, int numOfCattle) {
        super(type, numOfCattle);
        addToLivestock(new Bull(60));
        addToLivestock(new Cow(36));
        addToLivestock(new Cow(24));
        addToLivestock(new Cow(12));
        addToLivestock(new Cow(24));
        setChanceOfDisease(4); // 40%
    }
}