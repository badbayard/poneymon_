package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.NotifyPlayerChangeCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.UpdateGameCmd;

public class ControllerListener implements Runnable {
    ClientMultiController cmc;
    private boolean isRunning = true;
    private boolean gameOn = false;

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

                if (!gameOn) {
                    cmc.turnGameOn(ugc);
                }

//                cmc.setFieldModel(ugc.getFieldModel());
                cmc.assignFieldModel(ugc.getFieldModel());
                System.out.println(ugc.getFieldModel().getParticipantModels()[0].getX());
                System.out.println(cmc.getFieldModel().getParticipantModels()[0].getX());
                System.out.println(ugc);
            }
        }
    }

    void setRunning(boolean running) {
        isRunning = running;
    }

    void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }
}
