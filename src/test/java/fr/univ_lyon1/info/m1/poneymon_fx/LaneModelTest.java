package fr.univ_lyon1.info.m1.poneymon_fx;

import org.junit.Before;
import org.junit.Test;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LaneEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObstacleModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

public class LaneModelTest {

    private LaneEntityModel lane;
    private FieldModel field;

    /**
     * Setting up.
     */
    @Before
    public void setUp() {
        field = new FieldModel(1);
        PoneyModel poney = new PoneyModel("green", 0, true, 5);
        lane = new LaneEntityModel(0, poney);

        field.getLanes()[0] = lane;

    }

    @Test
    public void testAddEntityRightLane() {
        FixedEntityModel mur = new FixedEntityModel(0, 0.5, 1);
        lane.addFixedEntity(mur);

        assert (field.getLanes()[0].getFixedEntities().contains(mur));
    }

    @Test
    public void removeEntityTheRightLane() {

        ObstacleModel[] murs = { new ObstacleModel(4, 0.9, 3), new ObstacleModel(0, 0.8, 0),
            new ObstacleModel(2, 0.6, 2), new ObstacleModel(1, 0.5, 4),
            new ObstacleModel(3, 0.1, 1), };

        field = new FieldModel(5);
        for (int j = 0; j < field.getLanes().length; j++) {
            for (int i = 0; i < murs.length; i++) {
                if (murs[i].getRow() == j) {
                    field.getLanes()[j].addFixedEntity(murs[i]);
                    assert (field.getLanes()[j].getFixedEntities().contains(murs[i]));
                }
            }
        }

        for (int j = 0; j < field.getLanes().length; j++) {
            for (int i = 0; i < murs.length; i++) {
                if (murs[i].getRow() == j) {
                    field.getLanes()[j].removeFixedEntity(murs[i]);
                    assert (!field.getLanes()[j].getFixedEntities().contains(murs[i]));
                }
            }
        }
    }

}
