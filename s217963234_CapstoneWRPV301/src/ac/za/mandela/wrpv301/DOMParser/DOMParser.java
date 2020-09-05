package ac.za.mandela.wrpv301.DOMParser;

import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DOMParser {
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    public DOMParser() {
        this.dbFactory = DocumentBuilderFactory.newInstance();
    }

    /**
     * <p> Creates a new XML file from and observable list of albums
     * The newly created file will consist of all an album including tracks in it's full structure.
     */
    public Document Write()
    {
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement =doc.createElement("AlbumList");
            doc.appendChild(rootElement);

           //Apppend the stuff;

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("album.xml"));
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");
            return doc;

        } catch (Exception e) {
            //TODO POP UP FOR fail
            return null;
        }
    }


    public void SaveGame()
    {

    }
}
