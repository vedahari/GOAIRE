package OnAireServlets;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

public class OSMWrapperAPI{
	public void parse(String city) throws SAXException,IOException {
		
		XMLReader osmReader = XMLReaderFactory.createXMLReader();
		osmReader.setContentHandler(new OSMSAXHandler(city));
		//osmReader.parse("D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\chennai_india.osm");		
		//osmReader.parse("D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\los-angeles_california.osm");
		//osmReader.parse("D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\blacksburg.osm");
		
		osmReader.parse(Constants.CityFileMap.get(city));
	}
}