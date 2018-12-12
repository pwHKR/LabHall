package RemoteCom;

import Arduino.ComTalker;
import DataStorage.DataStorage;
import RemoteCom.Connection.Client.Client;
import RemoteCom.Model.Request;

public class HandleRequest {
    private boolean arduinoHandleWorked;
    public HandleRequest() {
    }

    public void handle(Request request) {


        String type = request.getType();

        switch (type) {

            case "toggleDevice":
                handleToggle(request);


            case "getDeviceStatus":
                handleDeviceStatus(request);
                break;



            default:

                System.out.println("invalid request");


        }


    }

    public void writeToLogFile(Request request) {

        // Add code here
    }


    private void handleLamp(Request request) {

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with lamp with id" + request.getDeviceId());

        //  ComTalker comTalker = new ComTalker();
        //comTalker.turnLightOff();

    }


    private void handleFan(Request request) {

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with fan with id" + request.getDeviceId());

    }

    private void handleTest(Request request) {

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with request from web with id" + request.getDeviceId());
    }

    private void handleToggle(Request request) {
        /**
         private byte insideLight = 107;
         private byte targetTempRoom = 108;
         private byte fan = 109;
         private byte targetTempAttic = 110;
         private byte activateBurglarAlarm = 111;
         * */

        if (request.getDeviceId() == 107) {
            arduinoHandleWorked = DataStorage.getInstance().getCm().setDeviceStatus(ComTalker.TOGGLEABLE_DEVICE.INSIDE_LAMP, Integer.valueOf(request.getValue()));
            if (arduinoHandleWorked) {
                request.setValidated(true);
                Client client = new Client();
                client.send(request);
            }
        }
        if (request.getDeviceId() == 108) {
            DataStorage.getInstance().getCm().setDeviceStatus(ComTalker.TOGGLEABLE_DEVICE.TARGET_TEMP_ROOM, Integer.valueOf(request.getValue()));
            request.setValidated(true);
            Client client = new Client();
            client.send(request);
        }

        if (request.getDeviceId() == 109) {
            DataStorage.getInstance().getCm().setDeviceStatus(ComTalker.TOGGLEABLE_DEVICE.FAN, Integer.valueOf(request.getValue()));
            request.setValidated(true);
            Client client = new Client();
            client.send(request);
        }
        if (request.getDeviceId() == 110) {
            DataStorage.getInstance().getCm().setDeviceStatus(ComTalker.TOGGLEABLE_DEVICE.TARGET_TEMP_ATTIC, Integer.valueOf(request.getValue()));
            request.setValidated(true);
            Client client = new Client();
            client.send(request);
        }
        if (request.getDeviceId() == 111) {
            DataStorage.getInstance().getCm().setDeviceStatus(ComTalker.TOGGLEABLE_DEVICE.ACTIVATE_BURGLAR_ALARM, Integer.valueOf(request.getValue()));
            request.setValidated(true);
            Client client = new Client();
            client.send(request);
        }
    }
    private void handleDeviceStatus(Request request){


        if(request.getDeviceId() == 97){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.FIRE_ALARM)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 98){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.BURGLAR_ALARM)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 99){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.WATER_ALARM)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 100){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.INSIDE_TEMP)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 101){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.ATTIC_TEMP)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 102){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.OUTSIDE_TEMP)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 104){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.STOVE)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 105){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.WINDOW)));
            Client client  = new Client();
            client.send(request);
        }
        if(request.getDeviceId() == 106){
            request.setValue(String.valueOf(DataStorage.getInstance().getCm().getDeviceStatus(ComTalker.DEVICE.POWER)));
            Client client  = new Client();
            client.send(request);
        }
    }
    /**
     private byte fireAlarm = 97;
     private byte burglarAlarm = 98;
     private byte waterAlarm = 99;
     private byte tempRoom = 100;
     private byte tempAttic = 101;
     private byte tempOutside = 102;
     private byte stove = 104;
     private byte window = 105;
     private byte power = 106; */

}
