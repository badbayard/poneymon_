package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.Process;
import javafx.util.Pair;

import java.util.ArrayList;

public class ProcessManager {
    private static ProcessManager instance = null;
    private ArrayList<Pair<Process, Thread>> threads;

    private ProcessManager() {
        instance = this;
        threads = new ArrayList<>();
    }

    public static ProcessManager getProcessManager() {
        if (instance == null) {
            instance = new ProcessManager();
        }
        return instance;
    }

    /**
     * Retourne l'index (s'il existe) du processus déjà utiliser par le
     * client du processus en paramêtre.
     *
     * @param process Le processus contenant le client utilisé pour la recherche
     * @return -1 s'il n'y a pas de processus pour ce client sinon l'inde du
     * processus de ce client.
     */
    public synchronized int searchProcessByClient(Process process) {
        for (int i = 0; i < threads.size(); ++i) {
            if (threads.get(i).getKey().getIdClient() ==
                    process.getIdClient()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ajoute le processus au Manager et lance le thread correspondant.
     * @param process Le nouveau processus à lancé
     */
    public synchronized void createAndRunThread(Process process) {
        int oldProcess = searchProcessByClient(process);
        if (oldProcess != -1) {
            deleteProcess(oldProcess);
        }
        Thread t = new Thread(process);
        threads.add(new Pair<>(process, t));
        t.start();
    }

    /**
     * Supprime le processus dans la case indiqué par le parametre.
     * @param index l'index du processus à détruire
     */
    public synchronized void deleteProcess(int index) {
        threads.get(index).getKey().close();
        threads.remove(index);
    }
}