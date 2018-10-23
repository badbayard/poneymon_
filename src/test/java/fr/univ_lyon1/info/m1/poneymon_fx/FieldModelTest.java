package fr.univ_lyon1.info.m1.poneymon_fx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;

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

        // When

        // Then
        assertEquals(f1.countParticipants(), 5);
        assertEquals(f2.countParticipants(), 2);
        assertEquals(f3.countParticipants(), 4);
        assertEquals(f4.countParticipants(), 5);
    }

    /**
     * test de update() sur tous les poneys du terrain
     */
    @Test
    public void StepWorksOnEveryPonys() {
        MovingEntityModel[] poneys = field.getParticipantModels();
        double progression;
        for (int i = 0; i < poneys.length; i++) {
            progression = poneys[i].getTotalProgress();
            poneys[i].update(500);
            assert (poneys[i].getTotalProgress() >= progression);
        }
    }

    /**
     * test de partieTerminee() si un poney atteind 5 tours
     */
    @Test
    public void ReturnTrueIfGameIsOver() {
        MovingEntityModel[] poneys = field.getParticipantModels();

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

        for (int i = 0; i < poneys.length; i++) {
            assertFalse(poneys[i].getRaceFinished());
        }

        field.update(500);

        assertTrue(poneys[0].getRaceFinished());
        assertTrue(poneys[1].getRaceFinished());
        assertFalse(poneys[2].getRaceFinished());
        assertFalse(poneys[3].getRaceFinished());
        assertFalse(poneys[4].getRaceFinished());

    }

    /**
     * la fonction de classement trie bien par progression totale
     */
    @Test
    public void FieldOrderByTotalProgressionForRankingView() {
        MovingEntityModel[] poneys = field.getParticipantModels();

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
        for (int i = 0; i < poneys.length; i++) {
            System.out.println("Poney [" + i + "] rank : " + poneys[i].getRank());
        }

        System.out.println("\n");

        ArrayList<MovingEntityModel> attendu = new ArrayList<>();
        attendu.add(poneys[2]);
        attendu.add(poneys[0]);
        attendu.add(poneys[1]);
        attendu.add(poneys[4]);
        attendu.add(poneys[3]);

        field.rankParticipants();
        assertEquals(field.getRankings(), attendu);
    }

    /**
     * la fonction de classement n'echange pas de place en cas d'egalité partielle dans le tableau
     */
    @Test
    public void TestThreePoneyEquals() {
        MovingEntityModel[] poneys = field.getParticipantModels();

        for (int i = 0; i < poneys.length - 2; i++) {
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
        for (int i = 0; i < poneys.length; i++) {
            System.out.println("Poney [" + i + "] rank : " + poneys[i].getRank());
        }
        System.out.println("\n");

        ArrayList<MovingEntityModel> attendu = new ArrayList<>();
        attendu.add(poneys[0]);
        attendu.add(poneys[1]);
        attendu.add(poneys[2]);
        attendu.add(poneys[4]);
        attendu.add(poneys[3]);

        field.rankParticipants();
        assertEquals(field.getRankings(), attendu);
    }


    /**
     * la fonction de classement n'echange pas de place en cas d'egalité
     */
    @Test
    public void FivePoneyEquals() {
        MovingEntityModel[] poneys = field.getParticipantModels();
        ArrayList<MovingEntityModel> attendu = new ArrayList<>();

        for (int i = 0; i < poneys.length; i++) {
            poneys[i].setX(0.99999999999);
            poneys[i].setNbLap(4);
            poneys[i].setSpeed(0.5);
            attendu.add(i, poneys[i]);
        }
        field.update(0);

        System.out.println("Classement voisin : 5 poneys equals ");
        for (int i = 0; i < poneys.length; i++) {
            System.out.println("Poney [" + i + "] rank : " + poneys[i].getRank());
        }
        System.out.println("\n");

        field.rankParticipants();
        assertEquals(field.getRankings(), attendu);

        System.out.println("Classement Tri : Verif ravail sur Copies ");
        for (int i = 0; i < poneys.length; i++) {
            System.out.println("Poney [" + i + "] Progression : " + poneys[i].getX());
            System.out.println("Poney [" + i + "] Tours : " + poneys[i].getNbLap());
        }
        System.out.println("\n");

    }


}
