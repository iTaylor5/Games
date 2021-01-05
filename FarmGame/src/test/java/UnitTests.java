import Decorator.Farm;
import Decorator.FarmImpl;
import Decorator.FarmLevelTwo;
import Factory.*;
import org.junit.Test;

import java.util.ListIterator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UnitTests {

    @Test
    public void testFarm(){

        PlayGame game = new PlayGame();

        FarmImpl farm1 = new FarmImpl(game, "McDsFarm");
        farm1.addField(new MaizeField("maize"));
        farm1.addField(new SheepField("sheep", 30));

        FarmImpl farm2 = new FarmImpl(game, "TaylorFarm");
        farm2.addField(new WheatField("wheat"));
        farm2.addField(new CattleField("sheep", 15));

        assertEquals(2, game.getFarms().size());

        assertEquals(2, farm2.getMaxNumOfFields());

        assertEquals(1,farm2.getBonus(), 0.1);

        assertEquals(0,farm2.getBank(), 0.0);

        assertEquals(1,farm1.getFarmLevel(), 0.1);

        assertEquals("TaylorFarm", farm2.getName());

        farm1.setName("NewFarmName");

        assertEquals("NewFarmName", farm1.getName());

        assertEquals(2, farm1.getNumberOfFields());

        assertEquals(1, farm1.getFarmLevel());

        farm1.setFarmLevel(2);

        assertEquals(2, farm1.getFarmLevel());

        farm1.setFarmLevel(1);

        farm1.setMaxNumOfFields(3);

        assertEquals(3, farm1.getMaxNumOfFields());

        farm1.setMaxNumOfFields(2);

    }

    @Test
    public void testFarmLevelTwo(){

        PlayGame game = new PlayGame();

        FarmImpl farm2 = new FarmImpl(game, "TaylorFarm");
        farm2.addField(new WheatField("wheat"));
        farm2.addField(new CattleField("sheep", 15));

        Farm farm3 = new FarmLevelTwo(farm2);

        assertEquals(3, farm3.getMaxNumOfFields());

        assertEquals(1.1,farm3.getBonus(), 0.1);

        farm3.setBank(100);

        assertEquals(0.0,100, farm3.getBank());

        assertEquals(2, farm3.getFarmLevel());

        farm3.addField(new MaizeField("maize"));

        assertEquals(3, farm3.getNumberOfFields());

        assertEquals("TaylorFarm",farm3.getName());
        farm3.setName("TayFarm");
        assertEquals("TayFarm",farm3.getName());

        farm3.setFarmLevel(3);

        assertEquals(3, farm3.getFarmLevel());

        farm3.setMaxNumOfFields(4);

        assertEquals(4, farm3.getMaxNumOfFields());

    }

    @Test
    public void checkFarmCycle(){
        PlayGame game = new PlayGame();

        FarmImpl farm = new FarmImpl(game, "McDonald");

        farm.addField(new WheatField("wheat"));
        farm.addField(new MaizeField("maize"));

        farm.cycle();

        assertEquals(8, farm.getFields().get(0).getCyclesTillHarvest());
        assertEquals(7, farm.getFields().get(1).getCyclesTillHarvest());
    }

    @Test
    public void disease(){

        Field maize = new MaizeField("maize");

        maize.setIsDiseased(true);

        assertTrue(maize.isDiseased());

    }

    @Test
    public void testCycleLivestockField(){

        SheepField sheepField = new SheepField("sheep", 15);

        assertEquals("sheep", sheepField.getType());

        assertEquals(5, sheepField.getLivestock().size());

        assertEquals(36, sheepField.getLivestock().get(1).getAgeInMonths());

        sheepField.getLivestock().get(1).setImpregnated(true);

        assertTrue(sheepField.getLivestock().get(1).isImpregnated());

        assertFalse(sheepField.canHarvest());

        assertEquals(45, sheepField.getProfit(), 0.1);
        sheepField.getLivestock().remove(0);
        assertEquals(30, sheepField.getProfit(), 0.1);

        assertEquals(30, sheepField.getProfit(), 0.1);



    }

    @Test
    public void cropField(){
        WheatField wheat = new WheatField("wheat");
        MaizeField maize = new MaizeField("maize");

        assertNull(wheat.getLivestock());
        assertNull(wheat.getAnimalsToBeSlaughtered());

        assertFalse(wheat.canHarvest());
        assertFalse(maize.canHarvest());

        assertEquals(0, wheat.getCycleSincePlanted());
        assertEquals(0, maize.getCycleSincePlanted());

        assertEquals(9, wheat.getCyclesTillHarvest());
        assertEquals(8, maize.getCyclesTillHarvest());

        wheat.setCycleSincePlanted(3);
        maize.setCycleSincePlanted(3);

        assertEquals(3, wheat.getCycleSincePlanted());
        assertEquals(3, maize.getCycleSincePlanted());

        wheat.setHarvest(true);
        assertTrue(wheat.canHarvest());
        wheat.setHarvest(false);

        maize.setHarvest(true);
        assertTrue(maize.canHarvest());
        maize.setHarvest(false);


        assertEquals(75, wheat.getProfit(), 0.1);
        assertEquals(50, maize.getProfit(), 0.1);

        assertEquals(0, wheat.getAccount(), 0.1);
        assertEquals(0, maize.getAccount(), 0.1);

    }

    @Test
    public void livestockSheep(){

        SheepField sheepField = new SheepField("sheep", 3);

        Ewe sheep = new Ewe(10);

        sheepField.addToLivestock(sheep);

        assertEquals("ewe", sheep.getType());

        assertEquals(10, sheep.getAgeInMonths());

        sheep.setAgeInMonths(48);

        assertEquals(48, sheep.getMaxAge());

        assertEquals(48, sheep.getAgeInMonths());

        assertEquals( 48, sheep.getMaxAge(), 0.01);

        assertEquals(6, sheepField.getLivestock().size());

        sheepField.getLivestock().get(0).setAgeInMonths(47);
        sheepField.getLivestock().get(1).setAgeInMonths(47);

        sheepField.nightCycle();
        sheepField.nightCycle();

        assertEquals(4, sheepField.getLivestock().size());

    }

    @Test
    public void livestockFieldCycleForPregnantSheep(){
        SheepField sheepField = new SheepField("sheep", 15);
        assertEquals("sheep", sheepField.getType());

        ListIterator<Livestock> iter = sheepField.getLivestock().listIterator();

        Ewe ewe = (Ewe) sheepField.getLivestock().get(1);
        assertEquals(6, ewe.getGestationLength());

        //Check pregnancy
        assertFalse(ewe.isImpregnated());
        ewe.setImpregnated(true);
        assertTrue(ewe.isImpregnated());
        assertEquals(0, ewe.getTimeInGestation());

        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        assertEquals(1, ewe.getTimeInGestation());
        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        assertEquals(2, ewe.getTimeInGestation());
        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        assertEquals(5, ewe.getTimeInGestation());
        sheepField.cycleForPregnantAnimal(iter, sheepField.getLivestock().get(1));
        assertEquals(0, ewe.getTimeInGestation());
        assertFalse(ewe.isImpregnated());

        assertTrue(ewe.isInCoolingPeriod());
        assertEquals(24, ewe.getCoolingPeriodLength());

        assertEquals(0, ewe.getTimeInCoolingPeriod());
        sheepField.femLivestock(ewe);
        assertEquals(1, ewe.getTimeInCoolingPeriod());
        sheepField.femLivestock(ewe);

        ewe.setTimeInCoolingPeriod(23);
        sheepField.femLivestock(ewe);
        assertFalse(ewe.isInCoolingPeriod());
    }

    @Test
    public void livestockFieldCycleForPregnantCows(){
        CattleField cf = new CattleField("cattle", 15);
        assertEquals("cattle", cf.getType());

        ListIterator<Livestock> iter = cf.getLivestock().listIterator();

        Cow cow = (Cow) cf.getLivestock().get(1);
        assertEquals(9, cow.getGestationLength());

        //Check pregnancy
        assertFalse(cow.isImpregnated());
        cow.setImpregnated(true);
        assertTrue(cow.isImpregnated());
        assertEquals(0, cow.getTimeInGestation());

        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        assertEquals(1, cow.getTimeInGestation());
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        assertEquals(2, cow.getTimeInGestation());
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        assertEquals(5, cow.getTimeInGestation());
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        cf.cycleForPregnantAnimal(iter, cf.getLivestock().get(1));
        assertEquals(0, cow.getTimeInGestation());
        assertFalse(cow.isImpregnated());

        assertTrue(cow.isInCoolingPeriod());
        assertEquals(36, cow.getCoolingPeriodLength());

        assertEquals(0, cow.getTimeInCoolingPeriod());
        cf.femLivestock(cow);
        assertEquals(1, cow.getTimeInCoolingPeriod());
        cf.femLivestock(cow);

        cow.setTimeInCoolingPeriod(35);
        cf.femLivestock(cow);
        assertFalse(cow.isInCoolingPeriod());
    }

    @Test
    public void fieldDiseaseCheck(){
        CattleField cattleField = new CattleField("sheep", 15);
        assertFalse(cattleField.isDiseased());
        cattleField.setIsDiseased(true);
        assertTrue(cattleField.isDiseased());
        cattleField.catchDisease();
        assertTrue(cattleField.isDestroyed());
    }

    @Test
    public void getCurrentCostOfAnimal(){
        Ewe ewe = new Ewe(10);
        assertEquals(2.5, ewe.getCurrentCostOfAnimal(), 0.1);
        ewe.setAgeInMonths(37);
        assertEquals(15, ewe.getCurrentCostOfAnimal(), 0.1);

        Ram ram = new Ram(37);
        assertEquals(15, ram.getCurrentCostOfAnimal(), 0.1);
        ram.setAgeInMonths(37);
        assertEquals(15, ram.getCurrentCostOfAnimal(), 0.1);

        Cow cow = new Cow(24);
        assertEquals(14, cow.getCurrentCostOfAnimal(), 0.1);
        cow.setAgeInMonths(37);
        assertEquals(21, cow.getCurrentCostOfAnimal(), 0.1);


        Bull bull = new Bull(36);
        assertEquals(21, bull.getCurrentCostOfAnimal(), 0.1);
        cow.setAgeInMonths(37);
        assertEquals(21, bull.getCurrentCostOfAnimal(), 0.1);

    }

    @Test
    public void testSheepFemale(){
        Ewe ewe = new Ewe(36);
        Cow Cow = new Cow(36);

    }

    @Test
    public void testLivestock(){
        Livestock cattle = new Cattle("cow");

        assertFalse(cattle.isFullySized());

        assertEquals(36, cattle.getTillFullyFat());

        assertFalse(cattle.isInCoolingPeriod());
        cattle.setInCoolingPeriod(true);
        assertTrue(cattle.isInCoolingPeriod());

    }

    @Test
    public void runGame(){
        PlayGame pg = new PlayGame();

        assertEquals(200, pg.getGold(), 0.1);
        pg.setGold(250);
        assertEquals(250, pg.getGold(), 0.1);
    }


}
