package RemoteServer;

import RemoteServer.Connection.Client.Client;
import RemoteServer.RequestList.Lamp;

public class Main {

    public static void main(String[] args) {

        //MultiClientServer multiClientServer = new MultiClientServer();
        //multiClientServer.start();

        Client connection = new Client();

        connection.send(Lamp.lamp0_OFF);



    }
}
