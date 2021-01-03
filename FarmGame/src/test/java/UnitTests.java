import Factory.*;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class UnitTests {

    @Test
    public void testCycleLivestockField(){
        SheepField sheepField = new SheepField("sheep", 15);

        assertEquals("sheep", sheepField.getType());
        assertEquals(5, sheepField.getLivestock().size());

        assertEquals(36, sheepField.getLivestock().get(1).getAgeInMonths());

        sheepField.getLivestock().get(1).setImpregnated(true);

        assertTrue(sheepField.getLivestock().get(1).isImpregnated());

        for(int i = 0; i < 6; i++){
            assertEquals(36 + i, sheepField.getLivestock().get(1).getAgeInMonths());
            sheepField.nightCycle();
        }
        assertFalse(sheepField.getLivestock().get(1).isImpregnated());

        assertEquals(6, sheepField.getLivestock().size());

        assertEquals(36, sheepField.getLivestock().get(1).getAgeInMonths());

        //sheepField.getLivestock().get(1).setImpregnated(true);
    }

    @Test
    public void testSheepFemale(){
        Ewe ewe = new Ewe(36);
        Cow Cow = new Cow(36);

    }

    @Test
    public void testIncrementingNumOfFields(){


    }

}
