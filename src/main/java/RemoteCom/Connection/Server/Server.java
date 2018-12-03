package RemoteCom.Connection.Server;

import RemoteCom.HandleRequest;
import RemoteCom.Model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Server extends Thread {
    Socket clientSocket = null;

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        System.out.println("client connected");

        ObjectInputStream in = null;
        try {

            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        try {

            HandleRequest handle = new HandleRequest();

            Request remote = null;

            try {
                remote = (Request) in.readObject();
                System.out.println(remote.toString());

                handle.handle(remote);
                handle.writeToLogFile(remote);

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        } /*catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }*/ catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();

            in.close();
        } catch (IOException ioe) {
            System.out.println("Failed in closing down");
            System.exit(-1);
        }
    }
}