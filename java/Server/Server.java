package Server;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) {
        int PORT = 5321;
        int numUser=0;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                numUser++;
                System.out.println("User" + numUser + " connected");
               OrganiseServer organiseServer = new OrganiseServer(socket, numUser);
                Thread t = new Thread(organiseServer);
                t.start();

            }
        } catch (IOException IOE) {
            System.out.println("Connection with Client failed!");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
