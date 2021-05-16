package Client;


import java.io.DataInputStream;

public class Message extends Thread {
    private final DataInputStream input;

    public Message(DataInputStream input) {

        this.input = input;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(input.readUTF());
            } catch (java.io.EOFException e) {
                System.out.println("Connection failed.");
                System.exit(8);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}