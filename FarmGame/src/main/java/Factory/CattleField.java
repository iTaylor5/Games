package Factory;

import java.util.LinkedList;

public class CattleField extends LivestockField {

//    private LinkedList<Cow> cows;
//    private LinkedList<Bull> bulls;

    public CattleField(String type, int numOfCattle) {
        super(type, numOfCattle);

//        cows = new LinkedList<>();
//        bulls = new LinkedList<>();

//        addBull(new Bull(5));
//        addCow(new Cow(3));
//        addCow(new Cow(3));
//        addCow(new Cow(3));
//        addCow(new Cow(3));

        addToLivestock(new Bull(5));
        addToLivestock(new Cow(3));
        addToLivestock(new Cow(3));
        addToLivestock(new Cow(3));
        addToLivestock(new Cow(3));

        //setProfit( cows.size() * 20 + bull.size() * 20);
        setChanceOfDisease(4); // 40%
    }

//    public LinkedList<Cow> getCows() {
//        return cows;
//    }
//
//    public void addCow(Cow pCows) {
//        cows.add(pCows);
//    }
//
//    public LinkedList<Bull> getBulls() {
//        return bulls;
//    }
//
//    public void addBull(Bull pBull) {
//        bulls.add(pBull);
//    }
}