package RemoteCom.Connection.Client;

import RemoteCom.Model.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {


    private Socket socket = null;
    private ObjectOutputStream out = null;

    public Client() {
    }

    private void establishContact() {
        try {
           socket = new Socket("83.248.250.216", 12345);
            // socket = new Socket("192.168.0.3", 12345);
            //socket = new Socket(InetAddress.getLocalHost(), 12345);
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            System.exit(1);
        }
    }

    private void closeContact() {
        try {
            out.flush();
            out.close();

            socket.close();
        } catch (IOException ioe) {
            System.out.println("Failed");
            System.exit(-1);
        }
    }


    public void send(Request message) {

        establishContact();


        try {
            out.writeObject(message);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        closeContact();

    }


}