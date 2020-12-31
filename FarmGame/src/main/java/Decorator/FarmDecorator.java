package Decorator;

public abstract class FarmDecorator implements Farm {

    public Farm homeFarm;

    public FarmDecorator(Farm homeFarm){
        this.homeFarm = homeFarm;
    }
}