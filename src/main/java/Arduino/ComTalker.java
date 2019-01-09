package Arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;


/*
   Simple class for communication between Java and Arduino
   This is not a finalized version, please read the TODO's.
   This class assumes that it will only be instantiated once or the Serial communication might not work.
 */
public class ComTalker {


    public enum DEVICE {
        INSIDE_TEMP,
        OUTSIDE_TEMP,
        ATTIC_TEMP,
        STOVE,
        WINDOW,
        POWER,
        BURGLAR_ALARM,
        FIRE_ALARM,
        WATER_ALARM,
    }

    public enum TOGGLEABLE_DEVICE {
        INSIDE_LAMP,
        TARGET_TEMP_ROOM,
        TARGET_TEMP_ATTIC,
        FAN,
        ACTIVATE_BURGLAR_ALARM
    }

    private byte fireAlarm = 97;
    private byte burglarAlarm = 98;
    private byte waterAlarm = 99;
    private byte tempRoom = 100;
    private byte tempAttic = 101;
    private byte tempOutside = 102;
    private byte stove = 104;
    private byte window = 105;
    private byte power = 106;
    private byte insideLight = 107;
    private byte targetTempRoom = 108;
    private byte fan = 109;
    private byte targetTempAttic = 110;
    private byte activateBurglarAlarm = 111;


    SerialPort comPort;


    public ComTalker() {
        this.comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
    }

    public ComTalker(SerialPort serialPort, boolean isOpen) {
        this.comPort = serialPort;
        if (!isOpen) {
            comPort.openPort();
        }
    }


    public int getDeviceStatus(DEVICE device) {

        switch (device) {

            case ATTIC_TEMP:
                return getAtticTemp();

            case INSIDE_TEMP:
                return getInsideTemp();

            case OUTSIDE_TEMP:
                return getOutsideTemp();

            case POWER:
                return getPower();

            case STOVE:
                return getStove();

            case WINDOW:
                return getWindow();

            case BURGLAR_ALARM:
                return getBurglarAlarm();

            case FIRE_ALARM:
                return getFireAlarm();

            case WATER_ALARM:
                return getWaterAlarm();


        }

        return Integer.MIN_VALUE;
    }


    public boolean setDeviceStatus(TOGGLEABLE_DEVICE device, int status) {
        switch (device) {

            case INSIDE_LAMP:
                return handleInsideLight(status);

            case FAN:
                return handleFan(status);

            case TARGET_TEMP_ROOM:
                return setTargetTempRoom(status);

            case TARGET_TEMP_ATTIC:
                return setTargetTempAttic(status);

            case ACTIVATE_BURGLAR_ALARM:
                return setBurglarAlarmStatus(status);

        }
        return false;
    }

    private boolean setTargetTempAttic(int status) {
        byte[] output = {targetTempAttic, (byte) (status + 100)};
        if (sendMessage(output)) {
            return true;
        } else {
            return false;
        }
    }


    private boolean setTargetTempRoom(int status) {
        byte[] output = {targetTempRoom, (byte) (status + 100)};
        if (sendMessage(output)) {
            return true;
        } else {
            return false;
        }
    }

    private byte getWindow() {
        byte[] output = {window, '0'};
        if (sendMessage(output)) {
            return getValue();
        } else {
            return Byte.MIN_VALUE;
        }
    }

    private byte getStove() {
        byte[] output = {stove, '0'};
        if (sendMessage(output)) {
            return getValue();
        } else {
            return Byte.MIN_VALUE;
        }
    }

    private byte getPower() {
        byte[] output = {power, '0'};
        if (sendMessage(output)) {
            return getValue();
        } else {
            return Byte.MIN_VALUE;
        }

    }


    private byte getOutsideTemp() {
        byte[] output = {tempOutside, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue() - 100);
        } else {
            return Byte.MIN_VALUE;
        }
    }

    private byte getInsideTemp() {
        byte[] output = {tempRoom, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue() - 100);
        } else {
            return Byte.MIN_VALUE;
        }
    }

    private byte getAtticTemp() {
        byte[] output = {tempAttic, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue() - 100);
        } else {
            return Byte.MIN_VALUE;
        }

    }

    private int getBurglarAlarm() {
        byte[] output = {burglarAlarm, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue());

        } else {
            return Byte.MIN_VALUE;
        }

    }

    private int getFireAlarm() {
        byte[] output = {fireAlarm, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue());

        } else {
            return Byte.MIN_VALUE;
        }
    }

    private int getWaterAlarm() {
        byte[] output = {waterAlarm, '0'};
        if (sendMessage(output)) {
            return (byte) (getValue());

        } else {
            return Byte.MIN_VALUE;
        }
    }



    private boolean handleFan(int status) {
        byte[] output = new byte[2];
        if (status == 0) {
            output = new byte[]{fan, '0'};
        } else if (status == 1) {
            output = new byte[]{fan, '1'};
        }
        if (sendMessage(output)) {
            return true;
        } else {
            return false;
        }


    }


    private boolean setBurglarAlarmStatus(int status) {
        byte[] output = new byte[2];
        if (status == 0) {
            output = new byte[]{activateBurglarAlarm, '0'};
        } else if (status == 1) {
            output = new byte[]{activateBurglarAlarm, '1'};
        }
        if (sendMessage(output)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean handleInsideLight(int status) {
        byte[] output = null;
        if (status == 0) {
            output = new byte[]{insideLight, '0'};
        } else if (status == 1) {
            output = new byte[]{insideLight, '1'};
        }
        if (sendMessage(output)) {
            return true;
        } else {
            return false;
        }
    }


    private boolean sendMessage(byte[] output) {
        try {

            //send first msg
            comPort.getOutputStream().write(output[0]);
            comPort.getOutputStream().write(output[1]);
            comPort.getOutputStream().flush();

            //TODO: should be rewritten for wait time and handle errors..
            while (comPort.getInputStream().available() < 2) {

            }
            //handle response if the returning array is at least 2. What else?

            byte[] input = new byte[comPort.getInputStream().available()];
            comPort.getInputStream().read(input);


            //if it all checks out, handle
            if (input[0] == output[0] && input[1] == output[1]) {
                comPort.getOutputStream().write(output[0]);
                comPort.getOutputStream().write(output[1]);
                return true;

            } else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] pollAlarms() {
        try {


            if (comPort.getInputStream().available() > 0) {
                byte[] input = new byte[comPort.getInputStream().available()];
                comPort.getInputStream().read(input);

                return input;

            } else {
                System.out.println("No alarm activated");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    //To be used to receive a message after three-way sending handshake @ sendMessage().
    private byte getValue() {
        try {
            //TODO: should be rewritten for wait time and handle errors..
            while (comPort.getInputStream().available() < 1) {

            }
            if (comPort.getInputStream().available() > 0) {
                byte[] input = new byte[comPort.getInputStream().available()];
                comPort.getInputStream().read(input);
                //System.out.println(input[0]);
                return input[0];
            } else {
                return Byte.MIN_VALUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Byte.MIN_VALUE;

    }
}




