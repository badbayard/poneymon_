package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LaneEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LaneModelTest {


    private LaneEntityModel lane;
    private PoneyModel poney;
    private FieldModel field;

    @Before
    public void setUp() {
        field = new FieldModel(1);
        poney = new PoneyModel("green", 0, true, 5);
        lane = new LaneEntityModel(0, poney);

        field.getLanes()[0] = lane;

    }

    @Test
    public void testAddEntityRightLane (){
        FixedEntityModel mur = new FixedEntityModel( 0,0.5,1);
        lane.addFixedEntity(mur);

        assert(field.getLanes()[0].getFixedEntities().contains(mur));
    }

    @Test
    public void removeEntityTheRightLane (){

        ArrayList<FixedEntityModel> fixedEntitiesExpected = new ArrayList<>();

        FixedEntityModel [] murs = {
            new FixedEntityModel( 4,0.5,1),
            new FixedEntityModel( 0,0.5,0),
            new FixedEntityModel( 2,0.5,0),
            new FixedEntityModel( 1,0.5,1),
            new FixedEntityModel( 3,0.5,1),
        };


        field = new FieldModel(5);

        for(FixedEntityModel obs : murs) {
            lane.addFixedEntity(obs);
        }

        for(LaneEntityModel lan : field.getLanes()) {
            assert(lan.getFixedEntities().size() == 1);
        }



        for(LaneEntityModel lan : field.getLanes()) {
            for(FixedEntityModel m : lan.getFixedEntities()) {
                lane.removeFixedEntity(m);
                //assert(lan.getFixedEntities().equals(fixedEntitiesExpected));
            }
        }
    }


}
