package projekt.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {

	/** Saab informatsiooni kindlalt lehelt, kasutades aktsia lühendit. Tagastab List<String> järgnevad väärtustega(Name, LastTradePriceOnly, DaysLow, DaysHigh)*/
	public static List<String> getStockByQuote(String symbol){
		List<String> stockInfo = new ArrayList<>();
		try { // TODO Fix horrible mess...
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
			String link = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22" + symbol + "%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
			URL url = new URL(link);
			InputStream stream = url.openStream();
			Document doc = builder.parse(new InputSource(stream));
		
			Element root = doc.getDocumentElement();
			stockInfo.add(root.getElementsByTagName("Name").item(0).getTextContent());
			stockInfo.add(symbol);
			stockInfo.add(root.getElementsByTagName("LastTradePriceOnly").item(0).getTextContent());
			stockInfo.add(root.getElementsByTagName("DaysLow").item(0).getTextContent());
			stockInfo.add(root.getElementsByTagName("DaysHigh").item(0).getTextContent());
			
			stream.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return stockInfo;
	}
	
}
