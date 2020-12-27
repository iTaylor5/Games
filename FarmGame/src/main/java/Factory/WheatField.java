package Factory;

public class WheatField extends CropField {
    public WheatField(String type) {
        super(type);
        this.setProfit(75);
        this.setChanceOfDisease(60);
    }
}
