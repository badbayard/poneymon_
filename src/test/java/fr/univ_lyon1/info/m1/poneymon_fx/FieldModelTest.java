package fr.univ_lyon1.info.m1.poneymon_fx;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the FieldModel class.
 */
 public class FieldModelTest {



    private FieldModel field;


    /**
     * initialisation du terrain
     */
    @Before
    public void setUp() {
        field = new FieldModel(5);
    }


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


    /**
     * test de update() sur tous les poneys du terrain
     */
    @Test
    public void StepWorksOnEveryPonys()
    {
        PoneyModel[] poneys = field.getPoneyModels();
        double progression;
        for(int i =0 ; i< poneys.length ; i++)
        {
            progression = poneys[i].totalProgress();
            poneys[i].update(500);
            assert(poneys[i].totalProgress() >= progression);
        }
    }


    /**
     * test de partieTerminee() si un poney atteind 5 tours
     */
    @Test
    public void ReturnTrueIfGameIsOver()
    {
        PoneyModel[] poneys = field.getPoneyModels();

        poneys[0].setX(0.99999999999);
        poneys[0].setNbLap(4);
        poneys[0].setSpeed(0.5);

        poneys[1].setX(0.99999999999);
        poneys[1].setNbLap(4);
        poneys[1].setSpeed(0.5);

        poneys[2].setX(0);
        poneys[2].setNbLap(0);

        poneys[3].setX(0.5);
        poneys[3].setNbLap(2);

        poneys[4].setX(0.99999999999);
        poneys[4].setNbLap(3);

        for(int i = 0 ; i < poneys.length ; i++)
        {
            assertEquals(poneys[i].hasFinishedTheRace() , false);
        }

        field.update(500);
        assertEquals( poneys[0].hasFinishedTheRace() , true);
        assertEquals( poneys[1].hasFinishedTheRace() , true);
        assertEquals( poneys[2].hasFinishedTheRace() , false);
        assertEquals( poneys[3].hasFinishedTheRace() , false);
        assertEquals( poneys[4].hasFinishedTheRace() , false);

    }


    /**
     * la fonction de classement trie bien par progression totale
     */
    @Test
    public void FieldOrderByTotalProgressionForRankingView()
    {
        PoneyModel[] poneys = field.getPoneyModels();



        poneys[0].setX(0.99999999999);
        poneys[0].setNbLap(4);

        poneys[1].setX(0.99999999999);
        poneys[1].setNbLap(4);

        poneys[2].setX(0);
        poneys[2].setNbLap(5);

        poneys[3].setX(0.2);
        poneys[3].setNbLap(2);

        poneys[4].setX(0.499999999999);
        poneys[4].setNbLap(2);

        field.update(0);


        System.out.println("Classement voisin : 2 poneys equals ");
        for(int i = 0 ; i < poneys.length ; i++)
        {
          System.out.println("Poney ["+i+"] rank : "+ poneys[i].getRank());
        }

        System.out.println("\n");

        int ArrayAttendu [] = {2,0,1,4,3};
        field.rankPoney();
        assertArrayEquals(field.getRanking(),ArrayAttendu);



    }

    /**
     * la fonction de classement n'echange pas de place en cas d'egalité partielle dans le tableau
     */
    @Test
    public void TestThreePoneyEquals()
    {
        PoneyModel[] poneys = field.getPoneyModels();

        for(int i = 0 ; i < poneys.length-2; i++)
        {
            poneys[i].setX(0.99999999999);
            poneys[i].setNbLap(4);
            poneys[i].setSpeed(0.5);

        }

        poneys[3].setX(0.499999999999999);
        poneys[3].setNbLap(4);
        poneys[3].setSpeed(0.5);

        poneys[4].setX(0.5);
        poneys[4].setNbLap(4);
        poneys[4].setSpeed(0.5);

        field.update(0);

        System.out.println("Classement voisin : 3 poneys equals ");
        for(int i = 0 ; i < poneys.length ; i++)
        {
            System.out.println("Poney ["+i+"] rank : "+ poneys[i].getRank());
        }
        System.out.println("\n");


        int ArrayAttendu [] = {0,1,2,4,3};
        field.rankPoney();
        assertArrayEquals(field.getRanking(),ArrayAttendu);



    }



    /**
     * la fonction de classement n'echange pas de place en cas d'egalité
     */
    @Test
    public void FivePoneyEquals()
    {
        PoneyModel[] poneys = field.getPoneyModels();
        int ArrayAttendu [] = new int [poneys.length];

        for(int i = 0 ; i < poneys.length; i++)
        {
            poneys[i].setX(0.99999999999);
            poneys[i].setNbLap(4);
            poneys[i].setSpeed(0.5);
            ArrayAttendu[i] = i;

        }
        field.update(0);

        System.out.println("Classement voisin : 5 poneys equals ");
        for(int i = 0 ; i < poneys.length ; i++)
        {
            System.out.println("Poney ["+i+"] rank : "+ poneys[i].getRank());
        }
        System.out.println("\n");


        field.rankPoney();
        assertArrayEquals(field.getRanking(),ArrayAttendu);

        System.out.println("Classement Tri : Verif ravail sur Copies ");
        for(int i = 0 ; i < poneys.length ; i++)
        {
            System.out.println("Poney ["+i+"] Progression : "+ poneys[i].getX());
            System.out.println("Poney ["+i+"] Tours : "+ poneys[i].getNbLap());
        }
        System.out.println("\n");

    }






}
