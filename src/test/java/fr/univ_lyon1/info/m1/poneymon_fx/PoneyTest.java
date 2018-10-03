package fr.univ_lyon1.info.m1.poneymon_fx.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

import org.junit.Test;
import java.util.Random;

/**
 * Unit test for the PoneyModel class.
 */
public class PoneyTest {

    /**
     * Simple example test for the Poney class.
     */
    @Test
    public void testMoveSpeed() {
        // Given
        PoneyModel p = new PoneyModel("green", 0, true);
        p.setSpeed(1);
        p.setX(0);
        PoneyModel p2 = new PoneyModel("blue", 1, true);
        p2.setSpeed(0.2);
        p2.setX(0);

        // When
        p.update(1000);
        p2.update(500);

        // Then
        assertEquals(p.getX(), p.getSpeed()/PoneyModel.MINIMAL_TIME, 0.001);
        assertEquals(p2.getX(), p2.getSpeed()/(2*PoneyModel.MINIMAL_TIME), 0.001);
    }

    /**
     * Test for the distance between poneys
     */
    @Test
    public void testDistance() {
        // Given
        PoneyModel pG = new PoneyModel("green", 0, true);
        pG.setNbLap(1);
        PoneyModel pB = new PoneyModel("blue", 0, true);
        pB.setSpeed(1);

        // When
        pB.update(1000);

        // Then
        assertEquals(pG.getRelativeDistanceTo(pB), 1 - pB.getSpeed()/PoneyModel.MINIMAL_TIME, 0.001);
        assertEquals(pB.getRelativeDistanceTo(pG), - 1 + pB.getSpeed()/PoneyModel.MINIMAL_TIME, 0.001);
    }

    /**
     * Test for the boost
     */
    @Test
    public void testBoost() {
        // Given
        PoneyModel p = new PoneyModel("green", 0, true);
        p.setSpeed(0.5);

        // When
        p.turnIntoNianPoney();

        // Then
        assertEquals(p.getSpeed(), 1, 0.001);
        assertTrue(!p.canBoost());
    }

    /**
     * Test the return to the left of the screen at the end of lap
     */
    @Test
    public void testEndLap() {
        // Given
        PoneyModel p = new PoneyModel("green", 0, true);
        p.setSpeed(0.5);
        p.setX(1);
        p.setNbLap(1);

        // When
        p.update(1000);

        // Then
        assertEquals(p.getNbLap(), 2, 0.001);
        assertEquals(p.getX(), 0, 0.001);
        assertTrue(p.getSpeed() != 0.5);
    }

    /**
     * Test AI
     */
    @Test
    public void testAISkills() {
        // Given a dummy AI and ours
        double dummyTime = 0.0;
        int dummyPoints = 0;
        int randomLap;
        double dummySpeed;

        int numberOfRaces = 1000;

        double count = numberOfRaces;

        PoneyModel ourAI = new PoneyModel("blue", 0, true); // Champion!
        double aiTime = 0.0;
        int aiPoints = 0;

        Random randomGenerator = new Random();

        // When AI vs Dummy 1000 times
        for (int i = 0; i < numberOfRaces; i++) {
            // Dummy chooses the boost lap randomly
            randomLap = randomGenerator.nextInt(5);

            // Attributes initialization
            ourAI.setNbLap(0);
            dummyTime = 0.0;
            aiTime = 0.0;
            ourAI.setBoostCapacity(true);

            // Start of race
            for (int j = 0; j < 5; j++) {
                // Speed randomly chosen
                dummySpeed = randomGenerator.nextFloat();
                ourAI.setSpeed(dummySpeed);

                // Dummy boost choice
                if (randomLap == j) {
                    dummySpeed *= 2.0;
                }

                // Our AI boost choice
                ourAI.boostIfNecessary();

                // Dummy time
                dummyTime += PoneyModel.MINIMAL_TIME/dummySpeed;
                // AI time
                aiTime += PoneyModel.MINIMAL_TIME/ourAI.getSpeed();

                // Increment of nbLap
                ourAI.setNbLap(ourAI.getNbLap()+1);
            }

            if (aiTime < dummyTime) {
                aiPoints++;
            } else if (dummyTime < aiTime) {
                dummyPoints++;
            } else {
                count--;
            }
        }

        // Then
        System.out.println("AI Success Ratio : " + (aiPoints / count));
        assertTrue(aiPoints > dummyPoints);
    }
}
