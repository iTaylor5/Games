package Factory;

public class FactoryProvider {

    /**
     * Method to branch the factory depending on users choice.
     * @param choice - users input.
     * @return - factory to create a farm.
     */
    public static AbstractFactory<Field> getFactory(String choice) {

        if ("crop".equals(choice)) {
            System.out.println("Setting crop");
            return new CropFactory();
        } else if ("livestock".equals(choice)) {
            return new LivestockFactory();
        }
        return null;
    }
}
