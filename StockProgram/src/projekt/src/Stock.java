package projekt.src;

import java.util.ArrayList;
import java.util.List;

public class Stock {

	private String name;
	private double currentPrice;
	private double daysLow;
	private double daysMax;
	
	public static List<Stock> stocks = new ArrayList<Stock>();
	
	public Stock(String name, double currentPrice, double daysLow, double daysMax) {
		super();
		this.name = name;
		this.currentPrice = currentPrice;
		this.daysLow = daysLow;
		this.daysMax = daysMax;
		stocks.add(this);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
