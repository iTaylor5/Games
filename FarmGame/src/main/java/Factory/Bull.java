package Factory;

public class Bull extends Cattle {

    public Bull() {
        super("bull");
    }

    public Bull(int age){
        super("bull");
        this.setAgeInMonths(age);
    }
}
