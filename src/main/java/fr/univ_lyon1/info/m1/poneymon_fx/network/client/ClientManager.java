package fr.univ_lyon1.info.m1.poneymon_fx.network.client;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

import java.net.Socket;

/**
 * Class permettant de représenter un joueur humain aux yeux du serveur.
 */
public class ClientManager {
    private boolean isChief = false;
    private MovingEntityModel playerCharacter;
    private CommunicationSystem communicationSystemSystem;
    private Socket client;

    public ClientManager (Socket c) {
        client = c;
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
