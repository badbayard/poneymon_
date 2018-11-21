package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import org.junit.Before;
import org.junit.Test;

public class PoneyModelTest {

    private PoneyModel poney;

    /**
     * initialisation PoneyModel.
     */
    @Before
    public void setUp() {
        poney = new PoneyModel("green", 0, true, 5);
    }

    /**
     * Simple example test for the Poney class.
     */
    @Test
    public void testMoveSpeed() {

        poney.setSpeed(1);
        poney.setX(0);
        poney.update(1000);

        assert (poney.getX() == 0.2);

        poney.setSpeed(0.2);
        poney.setX(0);
        poney.update(500);

        assert (poney.getX() == 0.02);

    }

    /**
     * Test for the distance between poneys.
     */
    @Test
    public void testDistance() {

        poney.setNbLap(1);
        poney.setX(0);
        PoneyModel anotherPonney = new PoneyModel("blue", 0, true, 5);

        anotherPonney.setX(0.1);
        anotherPonney.setNbLap(1);
        anotherPonney.setSpeed(1);
        

        double returnDist;
        returnDist = (poney.getX() + poney.getNbLap())
                - (anotherPonney.getNbLap() + anotherPonney.getX());

        assert (poney.getRelativeDistanceTo(anotherPonney) == returnDist);

        poney.update(1000);

        returnDist = (poney.getX() + poney.getNbLap())
                - (anotherPonney.getNbLap() + anotherPonney.getX());

        assert (poney.getRelativeDistanceTo(anotherPonney) == returnDist);

    }

    /**
     * Test the return to the left of the screen at the end of lap.
     */
    @Test
    public void testEndLap() {

        poney = new PoneyModel("green", 0, true, 5);
        poney.setSpeed(0.6);
        poney.setX(0.99);
        poney.setNbLap(1);

        // When
        poney.update(1000);

        assert (poney.getX() == 0);
        assert (poney.getNbLap() == 2);
        assert (poney.getSpeed() != 0.6);
    }

    /**
     * verifie la vitesse generée.
     */
    @Test
    public void testRandomSpeed() {
        assert (poney.getSpeed() <= 1);
        assert (poney.getSpeed() >= 0);
    }

    /**
     * verrifie que la progression totale ne puisse jamais dépasser 5.
     */
    @Test
    public void testProgressionTotale() {
        poney.setX(0.99999);
        poney.setNbLap(4);
        poney.setSpeed(1);

        poney.update(500);

        assert (poney.getTotalProgress() <= 5);
    }

}
