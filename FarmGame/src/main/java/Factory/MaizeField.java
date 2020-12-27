package Factory;

public class MaizeField extends CropField {

    public MaizeField(String type) {
        super(type);
        this.setProfit(50);
        this.setChanceOfDisease(40);
    }
}