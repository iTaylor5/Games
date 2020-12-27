package Factory;

public class LivestockFactory implements AbstractFactory {

    @Override
    public Field create(String farmType) {
        if ("cattle".equals(farmType)) {
            return new CattleField(farmType);
        } else if ("sheep".equals(farmType)) {
            return new SheepField(farmType);
        }
        return null;
    }



}
