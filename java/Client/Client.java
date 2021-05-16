package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static String getLogin(){
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();

        return login;
    }
    public static String getPassword(){
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();

        return password;
    }
    public static String getEmail(){
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();

        return email;
    }
    public static String getWord(){
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();

        return word;
    }
    public static void main(String[] args) {
        String SERVER_IP = "127.0.0.1";
        int PORT = 5321;

        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected");

            Scanner scanner = new Scanner(System.in);
            Message message = new Message(in);
            message.start();

            while (true) {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":{
                        out.writeUTF(choice);
                        out.writeUTF(getLogin());
                        out.writeUTF(getPassword());
                        out.writeUTF(getWord());
                        break;
                    }
                    case "2": {
                        out.writeUTF(choice);
                        out.writeUTF(getLogin());
                        out.writeUTF(getPassword());
                        out.writeUTF(getEmail());
                        break;
                    }
                    case "3":{
                        out.writeUTF(choice);
                        out.writeUTF(getLogin());
                        out.writeUTF(getEmail());
                        break;
                    }
                    default:
                        System.out.println("Your choice is wrong! Try Again.");
                        break;
                }
                if (choice.equals("4")) break;
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException IOE) {
            System.out.println("Failed to connect to Server.");
        } finally {
            System.out.println("Disconnected.");

        }
    }
}