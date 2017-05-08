package projekt.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public static final String STOCKS_FILE = "stocks.txt";
	public static final String STOCK_PRICES_FILE = "prices.txt";

	/** Salvestab aktsia symboli stocks.txt. Eraldamiseks kasutame ';'.*/
	public static void saveStockToFile(Stock stock){
		writeToFile(STOCKS_FILE, stock.getTag() + ";");
	}
	
	public static void writeToFile(String fileName, String data){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(data);
			writer.close();
		} catch(IOException e){
			//TODO do something else...
		}
	}
	
	public static List<String> getStockSymbolsFromFile(){
		List<String> stocksSymbols = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(STOCKS_FILE));
			String line = reader.readLine();
			if(line != null){
				String[] dataFromFile = line.split(";");
				for(int i = 0; i < dataFromFile.length; i++)
					stocksSymbols.add(dataFromFile[i]);
			}
			reader.close();
		} catch(IOException e){
			//TODO Do also something...
		}
		return stocksSymbols;
	}
	
	public static void saveStockPrice(String symbol, double price){
		File file = new File(STOCK_PRICES_FILE);
		try {
			int lineNumber = getStockLine(symbol);
			if(lineNumber != -1){
				List<String> lines = Files.readAllLines(file.toPath());
				lines.set(lineNumber, lines.get(lineNumber) + price + ";" + System.currentTimeMillis() +  "|");
			} else {
				BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_PRICES_FILE));
				writer.write(symbol + "|" + price + ";" + System.currentTimeMillis() + "|");
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getStockLine(String symbol){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(STOCK_PRICES_FILE));
			int lineCount = 0;
			String line;
			while((line = reader.readLine()) != null){
				if(line.startsWith(symbol))
					return lineCount;
				lineCount++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
}
