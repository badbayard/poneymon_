package fr.univ_lyon1.info.m1.poneymon_fx.Model;

import static org.junit.Assert.assertEquals;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

import org.junit.Test;

/**
 * Unit test for the FieldModel class.
 */
 public class FieldTest {

    /**
     * Compare the number of poneys with the expected value
     */
    @Test
    public void testCount() {
        // Given
        FieldModel f1 = new FieldModel(1);
        FieldModel f2 = new FieldModel(2);
        FieldModel f3 = new FieldModel(4);
        FieldModel f4 = new FieldModel(12);

        //When

        // Then
        assertEquals(f1.countPoneys(), 5);
        assertEquals(f2.countPoneys(), 2);
        assertEquals(f3.countPoneys(), 4);
        assertEquals(f4.countPoneys(), 5);
    }
}
