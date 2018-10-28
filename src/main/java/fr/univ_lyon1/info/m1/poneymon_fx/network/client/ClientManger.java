package fr.univ_lyon1.info.m1.poneymon_fx.network.client;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communicationSystem.MessagingSystem;

/**
 * Class permettant de représenter un joueur humain aux yeux du serveur.
 */
public class ClientManger {
    private boolean isChief = false;
    private String username = "client";
    private MovingEntityModel playerCharacter;
    private MessagingSystem messagingSystem;

    ClientManger(String username) {
        this.username = username;
    }

    public void setPlayerCharacter(MovingEntityModel playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public boolean getChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }
}
