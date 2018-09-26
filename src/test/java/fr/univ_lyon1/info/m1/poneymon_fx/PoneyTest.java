package fr.univ_lyon1.info.m1.poneymon_fx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for the Poney class.
 */
public class PoneyTest {
    /**
     * Not really a test.
     */
    @Test
    public void testTrueIsTrue() {
        assertTrue(true);
    }

    /**
     * Simple example test for the Poney class.
     */
    @Test
    public void testMoveSpeed() {
        // Given
        Poney p = new Poney(null, "green", 0, false);
        p.speed = 0.42;
        p.x = 0;

        // When
        p.move();

        // Then
        assertEquals(p.x, 0.42, 0.001);
    }
}
