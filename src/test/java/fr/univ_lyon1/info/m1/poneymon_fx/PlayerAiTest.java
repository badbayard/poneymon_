package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import org.junit.Before;
import org.junit.Test;

public class PlayerAiTest {

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
}
