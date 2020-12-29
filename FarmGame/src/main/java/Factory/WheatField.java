package Factory;

public class WheatField extends CropField {
    public WheatField(String type) {
        super(type, 9);
        this.setProfit(75);
        this.setChanceOfDisease(6); // 60%
        setCyclesTillHarvest(9);
        setCycleSincePlanted(0);
    }
}
