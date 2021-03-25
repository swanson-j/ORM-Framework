package orm.utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Eventually used to create tables
 */

public class XSDParser {

    public Map<String, String>elementMap;

    public Map<String, String> getElementMap(String path, String namespace){
        elementMap = new LinkedHashMap<>();

        // @author: RoshanDsouza & Maksim Gladkov
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            NodeList nodeList = doc.getElementsByTagName(namespace+ ":element");

            for(int i = 0; i < nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                if(element.hasAttributes()){
                    elementMap.put(element.getAttribute("name"), element.getAttribute("type"));
                }
            }

            Iterator iterator = elementMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry mapElements = (Map.Entry)iterator.next();
                System.out.println(mapElements.getKey());
                String newValue = mapElements.getValue().toString().replace(namespace+":", "");
                System.out.println(newValue);
                elementMap.replace((String) mapElements.getKey(), newValue);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return elementMap;
    }
}
