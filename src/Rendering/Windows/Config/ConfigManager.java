package Rendering.Windows.Config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private File file;
    private Document doc;

    public double getHeight() {
        return Double.parseDouble(this.getCertainValue("height"));
    }

    public double getWidth() {
        return Double.parseDouble(this.getCertainValue("width"));
    }

    public boolean getMuted(){
        return Boolean.parseBoolean(this.getCertainValue("muted"));
    }

    public boolean getWindowMode() {
        return Boolean.parseBoolean(this.getCertainValue("windowMode"));
    }

    public boolean getFullscreen() {
        return Boolean.parseBoolean(this.getCertainValue("fullScreen"));
    }

    private NodeList getOptionNodes(){
        String filePath = "src/Rendering/Windows/Config/config";
        file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        NodeList nodeList = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("options").item(0).getChildNodes();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    private String getCertainValue(String key){
        NodeList nodeList = this.getOptionNodes();
        String value = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node.getNodeName().equals(key)) {
                value = node.getTextContent();
            }
        }
        return value;
    }

    public void writeValue(String key, String value) {
        NodeList nodeList = this.getOptionNodes();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeName().equals(key)){
                node.setTextContent(value);
                System.out.println(node.getNodeName() + " " + node.getTextContent());
            }
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            try {
                StreamResult result = new StreamResult(file);
                transformer = transformerFactory.newTransformer();
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

}

