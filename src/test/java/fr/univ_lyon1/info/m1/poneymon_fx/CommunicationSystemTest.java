package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communicationSystem.CommunicationSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CommunicationSystemTest {

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private CommunicationSystem clientMessagingSystem;
    private CommunicationSystem serverMessagingSystem;
    private String answer;

    @Before
    public void setUp() {
        System.out.println("HI");
        port = 4242;
        answer = "";
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        System.out.println("YO");
        try {
            serverSocket.close();
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        //clientMessagingSystem.close();
        //serverMessagingSystem.close();
    }

    @Test
    public void sendMessage() {
        System.out.println("Start of send message test");
        runServer1();
        try {
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        clientMessagingSystem = new CommunicationSystem(clientSocket);
        clientMessagingSystem.sendCommand(new StringCommand("Hello"));
        while (answer.isEmpty()) {
            System.out.print("");
        }
        assertEquals("Hello", answer);
        System.out.println("End of send message test");
    }

    @Test
    public void receiveMessage() {
        System.out.println("Start of receive message test");
        runServer2();
        try {
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        clientMessagingSystem = new CommunicationSystem(clientSocket);
        answer = ((StringCommand) clientMessagingSystem.receiveCommand())
                .getMot();
        assertEquals("Hi", answer);
        System.out.println("End of receive message test");
    }

    /*@Test(expected = NullPointerException.class)
    public void communicationSystemClose() {
        System.out.println("Start of closing communication system test");
        runServer1();
        try {
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            fail("Unwanted Exception thrown");
            e.printStackTrace();
        }
        clientMessagingSystem = new CommunicationSystem(clientSocket);
        clientMessagingSystem.close();
        clientMessagingSystem.sendCommand(new Command());
        System.out.println("End of closing communication system test");
    }*/

    private void runServer1() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Socket client = serverSocket.accept();
                    serverMessagingSystem = new CommunicationSystem(client);
                    StringCommand message =
                            (StringCommand) serverMessagingSystem
                                    .receiveCommand();
                    answer = message.getMot();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void runServer2() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Socket client = serverSocket.accept();
                    serverMessagingSystem = new CommunicationSystem(client);
                    serverMessagingSystem.sendCommand(new StringCommand("Hi"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
