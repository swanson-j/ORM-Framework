import orm.utilities.XSDParser;

import java.util.Iterator;

public class XMLDriver {
    public static void main(String[] args) {
        XSDParser xsdParser = new XSDParser();
        xsdParser.getElementMap("C:\\Users\\Joshua\\Desktop\\gitrepos\\Projects\\ORM-Framework\\ORM-Framework\\src\\main\\resources\\Alien.xsd", "xs");
    }

}
