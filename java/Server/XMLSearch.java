package Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLSearch {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    Document document;

    public List<User> check() throws ParserConfigurationException, IOException, SAXException {
        dBuilder = dbFactory.newDocumentBuilder();
        document=dBuilder.parse("data.xml");
        List<User> userArrayList=new ArrayList<User>();

        User user =null;
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("User");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                user = new User();
                user.setLogin(eElement.getElementsByTagName("login").item(0).getTextContent());
                user.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                user.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
                userArrayList.add(user);
            }
        }

        return userArrayList;
    }
}




