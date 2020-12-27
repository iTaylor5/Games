package Factory;

public class CropFactory implements AbstractFactory {

    @Override
    public Field create(String farmType) {
        if ("wheat".equals(farmType)) {
            return new WheatField(farmType);
        } else if ("maize".equals(farmType)) {
            System.out.println("Creating maize farm.");
            return new MaizeField(farmType);
        }
        return null;
    }


}
