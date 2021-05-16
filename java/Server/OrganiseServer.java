package Server;

import dl4j.WordToVec;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class OrganiseServer implements Runnable{

    DataInputStream in;
    DataOutputStream out;
    private final int clientID;
    XMLSearch xmlSearcher;

    OrganiseServer(Socket socket, int id) throws IOException, ParserConfigurationException, SAXException {
        clientID = id;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }
    private synchronized boolean checklogg(String login, String password) throws IOException, ParserConfigurationException, SAXException {
        xmlSearcher=new XMLSearch();

        List<User> userList=xmlSearcher.check();
        int flag=0;
        for (User element : userList) {
            if(element.getLogin().equals(login)&&element.getPassword().equals(password)){
                flag=1;
            }
        }
        if(flag==0){
            out.writeUTF("Wrong login or password!");
            return false;
        }
        return true;
    }

    private void logging() throws Exception {

        out.writeUTF("Type login: ");
        String newLogin=in.readUTF();

        out.writeUTF("Password: ");
        String newPassword=in.readUTF();

        if(checklogg(newLogin,newPassword)){
            out.writeUTF("Type a phrase you want to search: ");
            String word=in.readUTF();

            WordToVec wordToVec=new WordToVec(word);
            List<String> similarWordsInVocabTo=wordToVec.search();
            printList(similarWordsInVocabTo);
        }

    }
    private synchronized void printList(List<String> list) throws IOException {
        for(String word : list) {
            out.writeUTF(word);
        }
    }
    private synchronized void forgotPassword () throws IOException, ParserConfigurationException, SAXException {
        out.writeUTF("Type login: ");
        String newLogin=in.readUTF();
        out.writeUTF("Type email: ");
        String newEmail=in.readUTF();

        xmlSearcher=new XMLSearch();
        List<User>userList=xmlSearcher.check();
        int flag=0;
        for (User element : userList) {
            if(element.getLogin().equals(newLogin)&&element.getEmail().equals(newEmail)){
                out.writeUTF("Your password : "+ element.getPassword());
                flag=1;
            }
        }
        if(flag==0){
            out.writeUTF("There is no mail in our database!");
        }
    }
    private synchronized void registration() throws IOException, ParserConfigurationException, SAXException {
        out.writeUTF("Type login: ");
        String login=in.readUTF();
        out.writeUTF("password: ");
        String password=in.readUTF();
        out.writeUTF("email: ");
        String email=in.readUTF();
        if(checkRegister(login,password,email)){
            XMLWriter xmlWriter=new XMLWriter();
            xmlWriter.writeToFile(login,password,email);
        }
    }

    public synchronized boolean checkRegister(String login, String password, String email) throws IOException, ParserConfigurationException, SAXException {
        xmlSearcher=new XMLSearch();
        List<User>userList=xmlSearcher.check();
        for (User element : userList) {
            if(element.getLogin().equals(login)){
                out.writeUTF("This login is used");
                return false;
            }
            if(element.getEmail().equals(email)){
                out.writeUTF("This email is used");
                return false;
            }
        }
        return true;
    }

    public String ask() throws IOException {
        out.writeUTF("What do you want to do? 1-login, 2-register, 3-forgot password, 4- exit");
        String choice=in.readUTF();
        return choice;
    }

    public void service(String choice) throws IOException, ParserConfigurationException, SAXException {

        if (choice.equals("1")) {
            try {
                logging();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice.equals("2")) {
            registration();
        }
        else if(choice.equals("3")){
           forgotPassword();
        }
    }

    @Override
    public void run() {
    try{
            while(true){
                out.writeUTF("What do you want to do? 1-login, 2-register, 3-forgot password, 4- exit");
                String choice = in.readUTF();
                if (choice.equals("4")) return;
                service(choice);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("User" + clientID + " disconnected.");
        }
    }

}
