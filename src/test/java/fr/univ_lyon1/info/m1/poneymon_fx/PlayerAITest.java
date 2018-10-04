package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PlayerAITest {

    private PoneyModel poneyAI;

    /**
     * initialisation PoneyModel
     */
    @Before
    public void setUp() {
        poneyAI = new PoneyModel("green", 0, true);
    }


    /**
     * teste l'activation du NianMode de l'IA
     */
    @Test
    public void AiTriggerNianModeCorrectly() {

        //final int lapsLeft = NB_LAPS - nbLap - 1;

        poneyAI.setNbLap(0);
        poneyAI.setBoostCapacity(true);
        poneyAI.setSpeed(0);

        poneyAI.boostIfNecessary();

        assert(poneyAI.isNianPoney());

        poneyAI.boostIfNecessary();

        poneyAI.setX(0.999);
        poneyAI.setNbLap(0);
        poneyAI.setSpeed(1);

        poneyAI.update(500);

        assert(!poneyAI.canBoost());
        assert(!poneyAI.isNianPoney());

        poneyAI.setX(0);
        poneyAI.setNbLap(1);
        poneyAI.setSpeed(0.5);

        poneyAI.boostIfNecessary();

        assert(!poneyAI.isNianPoney());

    }



    /**
     * Teste qu'une Ia qui boost au bon moment effectue un meilleur temps qu'un IA qui boost a un tour aléatoire.
     */
    @Test
    public void testAISkills() {
        // Given a dummy AI and ours
        double dummyTime = 0;
        int dummyPoints = 0;
        int randomLap;
        double speed;

        int numberOfRaces = 1000;

        double count = numberOfRaces;

        PoneyModel RandomAi = new PoneyModel("blue", 0, true); // Champion!
        double aiTime = 0.0;
        int aiPoints = 0;

        Random randomGenerator = new Random();

        // When AI vs Dummy 1000 times
        for (int i = 0; i < numberOfRaces; i++) {
            // Dummy chooses the boost lap randomly
            randomLap = randomGenerator.nextInt(5);

            // Attributes initialization
            poneyAI.setNbLap(0);
            poneyAI.setBoostCapacity(true);
            aiTime = 0.0;

            RandomAi.setNbLap(0);
            RandomAi.setBoostCapacity(true);
            dummyTime = 0.0;



            // Start of race
            for (int j = 0; j < 5; j++) {
                // Speed randomly chosen
                speed = randomGenerator.nextFloat();
                poneyAI.setSpeed(speed);
                RandomAi.setSpeed(speed);

                // Dummy boost choice
                if (randomLap == j) {
                    RandomAi.turnIntoNianPoney();
                }

                // Our AI boost choice
                poneyAI.boostIfNecessary();

                // Dummy time
                dummyTime += PoneyModel.MINIMAL_TIME/RandomAi.getSpeed();
                // AI time
                aiTime += PoneyModel.MINIMAL_TIME/poneyAI.getSpeed();

                // Increment of nbLap
                poneyAI.setNbLap(poneyAI.getNbLap()+1);
                RandomAi.setNbLap(RandomAi.getNbLap()+1);
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
        System.out.println("Last run Time (AI): "+ aiTime + "\nAI points :  " + aiPoints + "\n");
        System.out.println("Last run Time (retarded AI) : "+ dummyTime + "\ndummy points " + dummyPoints + "\n");

        System.out.println("AI Success Ratio : " + (aiPoints / count));
        assertTrue(aiPoints > dummyPoints);
    }


}
