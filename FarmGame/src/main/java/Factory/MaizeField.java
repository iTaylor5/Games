package Factory;

public class MaizeField extends CropField {

    public MaizeField(String type) {
        super(type, 8);
        this.setProfit(50);
        this.setChanceOfDisease(5); // 50%
        setCyclesTillHarvest(8);
        setCycleSincePlanted(0);
    }
}