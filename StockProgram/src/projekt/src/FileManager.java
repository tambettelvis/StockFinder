package projekt.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

	public static final String STOCKS_FILE = "stocks.txt";
	public static final String STOCK_PRICES_FILE = "prices.txt";

	/** Salvestab aktsia symboli stocks.txt. Eraldamiseks kasutame ';'.*/
	public static void saveStockToFile(Stock stock){
		writeToFile(STOCKS_FILE, stock.getTag() + ";");
	}
	
	public static void writeToFile(String fileName, String data){
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
			writer.append(data);
			writer.close();
		} catch(IOException e){
			//TODO do something else...
		}
	}
	
	public static void clearFile(String fileName){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static Map<Long, Double> getStockPrices(String symbol){
		Map<Long, Double> map = new HashMap<Long, Double>();
		File file = new File(STOCK_PRICES_FILE);
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			String line = lines.get(getStockLine(symbol));
			String[] data = line.split("\\|");
			for(int i = 1; i < data.length; i++){
				String[] priceAndTime = data[i].split(";");
				map.put(Long.parseLong(priceAndTime[1]), Double.parseDouble(priceAndTime[0]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static boolean isStockInFile(String symbol){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(STOCKS_FILE));
			String line = reader.readLine();
			reader.close();
			if(line.contains(symbol)){
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//YHOO|hind;aeg|hind;aeg|
	public static void saveStockPrice(String symbol, double price){
		File file = new File(STOCK_PRICES_FILE);
		try {
			int lineNumber = getStockLine(symbol);
			if(lineNumber != -1){
				List<String> lines = Files.readAllLines(file.toPath());
				lines.set(lineNumber, lines.get(lineNumber).trim() + price + ";" + System.currentTimeMillis() +  "|");
				BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_PRICES_FILE));
				for(String line : lines){
					writer.append(line + System.lineSeparator());
				}
				writer.close();
			} else {
				System.out.println("STOCK DOESN't EXIST : " + symbol);
				BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_PRICES_FILE, true));
				writer.append(symbol + "|" + price + ";" + System.currentTimeMillis() + "|" + System.lineSeparator());
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
