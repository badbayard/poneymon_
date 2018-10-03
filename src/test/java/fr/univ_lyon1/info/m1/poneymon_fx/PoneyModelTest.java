package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PoneyModelTest {

    private PoneyModel poney;

    /**
     * initialisation PoneyModel
     */
    @Before
    public void setUp() {
        poney = new PoneyModel("green", 0, true);
    }


    /**
     * Simple example test for the Poney class.
     */
    @Test
    public void testMoveSpeed() {

        poney.setSpeed(1);
        poney.setX(0);
        poney.update(1000);

        assert(poney.getX() == 0.2);

        poney.setSpeed(0.2);
        poney.setX(0);
        poney.update(500);

        assert(poney.getX() == 0.02);

    }


    /**
     * Test for the distance between poneys
     */
    @Test
    public void testDistance() {

        double returnDist;
        poney.setNbLap(1);
        poney.setX(0);
        PoneyModel anotherPonney = new PoneyModel("blue", 0, true);

        anotherPonney.setX(0.1);
        anotherPonney.setNbLap(1);
        anotherPonney.setSpeed(1);


        returnDist =  ( poney.getX() +  poney.getNbLap())
                - (anotherPonney.getNbLap() + anotherPonney.getX());

        assert(poney.getRelativeDistanceTo(anotherPonney) == returnDist);

        poney.update(1000);

        returnDist =  ( poney.getX() +  poney.getNbLap())
                - (anotherPonney.getNbLap() + anotherPonney.getX());

        assert(poney.getRelativeDistanceTo(anotherPonney) == returnDist);


    }


    /**
     * Test for the boost
     */
    @Test
    public void testBoost() {

        poney = new PoneyModel("green", 0, true);
        poney.setSpeed(0.25);


        poney.turnIntoNianPoney();
        assert(poney.getSpeed() == 0.5);
        assert(poney.isNianPoney());
        assertTrue(!poney.canBoost());

        poney.turnIntoNianPoney();

        assert(poney.getSpeed() == 0.5);
        assert(poney.isNianPoney());
        assertTrue(!poney.canBoost());

    }




}
