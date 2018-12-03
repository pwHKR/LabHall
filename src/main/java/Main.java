import RemoteCom.Connection.Client.Client;
import RemoteCom.Connection.Server.MultiClientServer;
import RemoteCom.Model.Request;

public class Main {

    public static void main(String[] args) {

        MultiClientServer multiClientServer = new MultiClientServer();
        multiClientServer.start();

        Client connection = new Client(); // Can be created anywhere in the application for sending remote rewuest

        // Test for remote com



        Request test = new Request("dbCall",6,"25");
        Request test2 = new Request("tempUpdate",0,"29");



        connection.send(test);

        connection.send(test2);


        // Main server application needs to be executed before the labHall application starts
        // E.G Dont start this application until the Main Server application is launched





    }
}
