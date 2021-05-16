package Server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class XMLWriter {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    Document document;
    public void writeToFile(String newLogin, String newPassword, String newEmail){
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.newDocument();
            document=dBuilder.parse("data.xml");
            Element documentE= document.getDocumentElement();
            Element user = document.createElement("User");
            Element login = document.createElement("login");
            Element password = document.createElement("password");
            Element email = document.createElement("email");
            login.setTextContent(newLogin);
            password.setTextContent(newPassword);
            email.setTextContent(newEmail);

            user.appendChild(login);
            user.appendChild(password);
            user.appendChild(email);
            documentE.appendChild(user);
            document.replaceChild(documentE,documentE);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);

            StreamResult console = new StreamResult(System.out);

            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(source, new StreamResult("data.xml"));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    
}
