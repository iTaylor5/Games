package Factory;

public class Bull extends Cattle {

    public Bull() {
        super("bull", 20);
    }

    public Bull(int age){
        super("bull", 20);
        this.setAge(age);
    }
}
