package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;

/**
 * Class permettant de représenter un joueur humain aux yeux du serveur.
 */
public class Player {
    private boolean isChief = false;
    String username = "player";
    MovingEntityModel playerCharacter;

    Player(String username) {
        this.username = username;
    }

    public void setPlayerCharacter(MovingEntityModel playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public boolean getChief() {
        return isChief;
    }
}
