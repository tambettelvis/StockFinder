package projekt.src;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StockInfoPanel extends VBox {

	private Label stockName;
	private Label currentPrice;
	private Label daysLow;
	private Label daysMax;
	private Label change;
	private Label marketCapitalization;
	private Label daysRange;
	
	private PopupGraph graphActive;
	
	static int spacing = 10;
	
	Button viewGraphButton = new Button("Vaata graafi");
	
	public StockInfoPanel(){
		super(spacing);
		createLabels("", "", "", "", "", "", "");
	}
	
	public StockInfoPanel(String stockName, String currentPrice, String daysLow, String daysMax, String change, String marketCapitalization, String daysRange) {
		super(spacing);
		createLabels(stockName, currentPrice, daysLow, daysMax, change, marketCapitalization, daysRange);
	}

	private void createLabels(String stockString, String currentPriceString, String daysLowString, String daysMaxString,
			String changeString, String marketCapitalizationString, String daysRangeString){
		Label lbl1 = new Label("Aktsia nimetus: ");
		Label lbl2 = new Label("Hetke hind: ");
		Label lbl3 = new Label("P‰eva madalaim hind: ");
		Label lbl4 = new Label("P‰eva kırgeim hind: ");
		Label lbl5 = new Label("Muutus: ");
		Label lbl6 = new Label("Firma v‰‰rtus: ");
		Label lbl7 = new Label("P‰eva muutus: ");
		
		stockName = new Label(stockString);
		currentPrice = new Label(currentPriceString);
		daysLow = new Label(daysLowString);
		daysMax = new Label(daysMaxString);
		change = new Label(changeString);
		marketCapitalization = new Label(marketCapitalizationString);
		daysRange = new Label(daysRangeString);

		HBox row1 = new HBox(10);
		HBox row2 = new HBox(10);
		HBox row3 = new HBox(10);
		HBox row4 = new HBox(10);
		HBox row5 = new HBox(10);
		HBox row6 = new HBox(10);
		HBox row7 = new HBox(10);
		
		row1.getChildren().addAll(lbl1, stockName);
		row2.getChildren().addAll(lbl2, currentPrice);
		row3.getChildren().addAll(lbl3, daysLow);
		row4.getChildren().addAll(lbl4, daysMax);
		row5.getChildren().addAll(lbl5, change);
		row6.getChildren().addAll(lbl6, marketCapitalization);
		row7.getChildren().addAll(lbl7, daysRange);
		
		viewGraphButton.setPrefSize(300, 50);
		viewGraphButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(stockName == null || stockName.getText().isEmpty()){
					return;
				}
				PopupGraph graph = new PopupGraph(stockName.getText());
				graphActive = graph;
				
				String symbol = null;
				for(Stock s : Stock.stocks){
					if(s.getName().equals(getStockName())){
						symbol = s.getTag();
						break;
					}
				}
				Map<Long, Double> prices = FileManager.getStockPrices(symbol);
				for (Map.Entry<Long, Double> entry : prices.entrySet())
				{
					Calendar c = new GregorianCalendar();
					c.setTimeInMillis(entry.getKey());
					System.out.println(c.getTimeInMillis() + " -- " + entry.getValue());
				    graphActive.addData(c, entry.getValue());
				}
				
			}
		});
		
		this.getChildren().addAll(row1, row2, row3, row4, row5, row6, row7, viewGraphButton);
	}
	
	public void updateActiveGraph(Calendar calendar, double value) {
		graphActive.addData(calendar, value);
		
		System.out.println("active graph update: " + calendar.getTimeInMillis() + "; " + value);
	}
	
	public String getStockName(){
		return stockName.getText();
	}

	public void setStockName(String stockName) {
		this.stockName.setText(stockName);
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice.setText(String.valueOf(currentPrice));
	}

	public void setDaysLow(double daysLow) {
		this.daysLow.setText(String.valueOf(daysLow));
	}

	public void setDaysMax(double daysMax) {
		this.daysMax.setText(String.valueOf(daysMax));
	}
	
	public void setChange(double change){
		this.change.setText(String.valueOf(change));
		if(change >= 0){
			this.change.setTextFill(Color.GREEN);
		} else {
			this.change.setTextFill(Color.RED);
		}
	}
	
	public void setMarketCapitalization(String marketCapitalization) {
		this.marketCapitalization.setText(marketCapitalization);
	}
	
	public void setDaysRange(String daysRange) {
		this.daysRange.setText(daysRange);
	}
	
}
