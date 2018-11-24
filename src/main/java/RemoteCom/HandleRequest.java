package RemoteCom;

import RemoteCom.Model.Request;

public class HandleRequest {
    public HandleRequest() {
    }

    public void handle(Request request){


      String type = request.getType();

        switch (type){

            case "lamp":
                handleLamp(request);
                break;

            case "fan":

                break;

            default:

                System.out.println("invalid request");


        }


    }

    public void writeToLogFile(Request request){

        // Add code here
    }


    private void handleLamp(Request request){

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with lamp with id" + request.getDeviceId());

    }


    private void handleFan(Request request){

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with fan with id" + request.getDeviceId());

    }
}