package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.NotifyPlayerChangeCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.UpdateGameCmd;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ControllerListener implements Runnable {
    ClientMultiController cmc;
    private boolean isRunning = true;

    ControllerListener(ClientMultiController cmc) {
        this.cmc = cmc;
    }

    @Override
    public void run() {
        while (isRunning) {
            Command command = cmc.receiveCommandCnt();

            if (command == null) {
                isRunning = false;
                return;
            }

            if (command instanceof NotifyPlayerChangeCmd) {
                NotifyPlayerChangeCmd npcc = (NotifyPlayerChangeCmd) command;
                cmc.changeNbPlayers(npcc.getNbPlayers());
            } else if (command instanceof UpdateGameCmd) {
                UpdateGameCmd ugc = (UpdateGameCmd) command;
                cmc.setFieldModel(ugc.getFieldModel());
            }
        }
    }

    void setRunning(boolean running) {
        isRunning = running;
    }
}
