package Factory;

public class SheepField extends LivestockField {

    public SheepField(String type, int numOfSheep) {
        super(type, numOfSheep);
        addToLivestock(new Ram(38));
        addToLivestock(new Ewe(36));
        addToLivestock(new Ewe(24));
        addToLivestock(new Ewe(12));
        addToLivestock(new Ewe(24));
        setChanceOfDisease(4); // 40%
    }
}
