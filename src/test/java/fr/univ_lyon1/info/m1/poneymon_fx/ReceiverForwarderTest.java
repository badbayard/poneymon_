package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.Forwarder;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.Receiver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ReceiverForwarderTest {

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Forwarder serverForwarder;
    private Forwarder clientForwarder;
    private Receiver clientReceiver;
    private Receiver serverReceiver;

    @Before
    public void setUp() {
        port = 4242;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
    }

    @Test
    public void receiveTest() {
        System.out.println("Start receive Test");
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Socket client = serverSocket.accept();
                    serverForwarder = new Forwarder(client);
                    serverForwarder.sendCommand(new StringCommand("1"));
                    serverForwarder.sendCommand(new StringCommand("Hi"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        clientReceiver = new Receiver(clientSocket);
        assertEquals("1",
                ((StringCommand) clientReceiver.receiveCommand()).getMot());
        assertEquals("Hi",
                ((StringCommand) clientReceiver.receiveCommand()).getMot());
        System.out.println("End receive Test");
    }

    @Test
    public void sendTest() {
        System.out.println("Start send Test");
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Socket client = serverSocket.accept();
                    serverReceiver = new Receiver(client);
                    serverForwarder = new Forwarder(client);
                    serverForwarder
                            .sendCommand(serverReceiver.receiveCommand());
                    serverForwarder
                            .sendCommand(serverReceiver.receiveCommand());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        clientForwarder = new Forwarder(clientSocket);
        clientReceiver = new Receiver(clientSocket);
        clientForwarder.sendCommand(new StringCommand("A"));
        assertEquals("A",
                ((StringCommand) clientReceiver.receiveCommand()).getMot());
        clientForwarder.sendCommand(new StringCommand("123"));
        assertEquals("123",
                ((StringCommand) clientReceiver.receiveCommand()).getMot());
        System.out.println("End send Test");
    }
}
