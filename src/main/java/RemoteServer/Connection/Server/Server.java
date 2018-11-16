package RemoteServer.Connection.Server;

import RemoteServer.Model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Server extends Thread {
    Socket clientSocket = null;

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        ObjectInputStream in = null;
        try {
           // out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        try {

                Request remote = null;

                try {
                    remote = (Request) in.readObject();
                    System.out.println(remote.toString());

                } catch(IOException ex){ ex.printStackTrace();
                }



        } /*catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }*/ catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
            //out.close();
            in.close();
        } catch (IOException ioe) {
            System.out.println("Failed in closing down");
            System.exit(-1);
        }
    }
}