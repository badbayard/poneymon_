package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class ProccessInServer implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public ProccessInServer(Socket sock) {
        this.sock = sock;
    }

    public void run() {
        System.err.println("Lancement du traitement de la connexion cliente");

        boolean closeConnexion = false;
        while (!sock.isClosed()) {
            try {
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                String response = read(); // Attente reponse
                InetSocketAddress remote =
                        (InetSocketAddress) sock.getRemoteSocketAddress();

                String command = "Commande reçue : " + response;
                System.err.println(command);

                // Traiement de la réponse
                String toSend = "";
                switch (response.toUpperCase()) {
                    case "FULL":
                        toSend = DateFormat.getDateTimeInstance(DateFormat.FULL,
                                DateFormat.MEDIUM).format(new Date());
                        break;
                    case "DATE":
                        toSend = DateFormat.getDateInstance(DateFormat.FULL)
                                .format(new Date());
                        break;
                    case "HOUR":
                        toSend = DateFormat.getTimeInstance(DateFormat.MEDIUM)
                                .format(new Date());
                        break;
                    case "CLOSE":
                        toSend = "Communication terminée";
                        closeConnexion = true;
                        break;
                    default:
                        toSend = "Commande inconnu !";
                        break;
                }

                //On envoie la réponse au client
                writer.write(toSend);
                writer.flush(); // Transmet vraiment les données au client

                if (closeConnexion) {
                    System.err.println("Commande de fermeture ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("Connexion interrompue ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode de lecture de réponse temporaire.
     * @return la réponse du serveur.
     * @throws IOException
     */
    private String read() throws IOException {
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

}
