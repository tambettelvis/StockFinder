package projekt.src;

import java.util.ArrayList;
import java.util.List;

public class Stock {
 
	private String name; //TODO: final?
	private String tag; //TODO: final?
	private double currentPrice;
	private double daysLow;
	private double daysMax;
	private double change;
	private String marketCapitalization;
	private String daysRange;
	
	public static List<Stock> stocks = new ArrayList<Stock>();
	
	public Stock(String name, String tag, double currentPrice, double daysLow, double daysMax, double change, String marketCapitalization, String daysRange) {
		super();
		this.name = name;
		this.tag = tag;
		this.currentPrice = currentPrice;
		this.daysLow = daysLow;
		this.daysMax = daysMax;
		this.change = change;
		this.marketCapitalization = marketCapitalization;
		this.daysRange = daysRange;
		stocks.add(this);
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public double getDaysLow() {
		return daysLow;
	}
	public void setDaysLow(double daysLow) {
		this.daysLow = daysLow;
	}


	public double getDaysMax() {
		return daysMax;
	}


	public void setDaysMax(double daysMax) {
		this.daysMax = daysMax;
	}


	public double getChange() {
		return change;
	}


	public void setChange(double change) {
		this.change = change;
	}


	public String getMarketCapitalization() {
		return marketCapitalization;
	}


	public void setMarketCapitalization(String marketCapitalization) {
		this.marketCapitalization = marketCapitalization;
	}


	public String getDaysRange() {
		return daysRange;
	}


	public void setDaysRange(String daysRange) {
		this.daysRange = daysRange;
	}
	
	
	
}
