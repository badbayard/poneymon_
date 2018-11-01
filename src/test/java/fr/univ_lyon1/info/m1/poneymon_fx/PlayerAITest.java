package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PlayerAITest {

    private PoneyModel poneyAi;

    /**
     * initialisation PoneyModel.
     */
    @Before
    public void setUp() {
        poneyAi = new PoneyModel("green", 0, true, 5);
    }

    /**
     * teste l'activation du NianMode de l'IA.
     */
    @Test
    public void aiTriggerNianModeCorrectly() {

        // final int lapsLeft = NB_LAPS - nbLap - 1;

        poneyAi.setNbLap(0);
        poneyAi.setBoostCapacity(true);
        poneyAi.setSpeed(0);

        poneyAi.boostIfNecessary();

        assert (poneyAi.isBoosted());

        poneyAi.boostIfNecessary();

        poneyAi.setX(0.999);
        poneyAi.setNbLap(0);
        poneyAi.setSpeed(1);

        poneyAi.update(500);

        assert (!poneyAi.canBoost());
        assert (!poneyAi.isBoosted());

        poneyAi.setX(0);
        poneyAi.setNbLap(1);
        poneyAi.setSpeed(0.5);

        poneyAi.boostIfNecessary();

        assert (!poneyAi.isBoosted());

    }

    /**
     * Teste qu'une Ia qui boost au bon moment effectue un meilleur temps qu'un IA qui boost a un
     * tour aléatoire.
     */
    @Test
    public void testAiSkills() {
        // Given a dummy AI and ours
        double dummyTime = 0;
        int dummyPoints = 0;
        int randomLap;
        double speed;

        int numberOfRaces = 1000;

        double count = numberOfRaces;

        PoneyModel randomAi = new PoneyModel("blue", 0, true, 5); // Champion!
        double aiTime = 0.0;
        int aiPoints = 0;

        Random randomGenerator = new Random();

        // When AI vs Dummy 1000 times
        for (int i = 0; i < numberOfRaces; i++) {
            // Dummy chooses the boost lap randomly
            randomLap = randomGenerator.nextInt(5);

            // Attributes initialization
            poneyAi.setNbLap(0);
            poneyAi.setBoostCapacity(true);
            aiTime = 0.0;

            randomAi.setNbLap(0);
            randomAi.setBoostCapacity(true);
            dummyTime = 0.0;

            // Start of race
            for (int j = 0; j < 5; j++) {
                // Speed randomly chosen
                speed = randomGenerator.nextFloat();
                poneyAi.setSpeed(speed);
                randomAi.setSpeed(speed);

                // Dummy boost choice
                if (randomLap == j) {
                    randomAi.turnIntoNianPoney();
                }

                // Our AI boost choice
                poneyAi.boostIfNecessary();

                // Dummy time
                dummyTime += PoneyModel.MINIMAL_TIME / randomAi.getSpeed();
                // AI time
                aiTime += PoneyModel.MINIMAL_TIME / poneyAi.getSpeed();

                // Increment of nbLap
                poneyAi.setNbLap(poneyAi.getNbLap() + 1);
                randomAi.setNbLap(randomAi.getNbLap() + 1);
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
        System.out.println("Last run Time (AI): " + aiTime + "\nAI points :  " + aiPoints + "\n");
        System.out.println("Last run Time (retarded AI) : " + dummyTime + "\ndummy points "
                + dummyPoints + "\n");

        System.out.println("AI Success Ratio : " + (aiPoints / count));
        assertTrue(aiPoints > dummyPoints);
    }

}
